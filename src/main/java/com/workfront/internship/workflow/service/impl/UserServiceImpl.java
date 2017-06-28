package com.workfront.internship.workflow.service.impl;

import com.sun.mail.smtp.SMTPTransport;
import com.workfront.internship.workflow.dao.UserDAO;
import com.workfront.internship.workflow.dao.impl.UserDAOImpl;
import com.workfront.internship.workflow.dao.springJDBC.UserDAOSpringImpl;
import com.workfront.internship.workflow.domain.AppArea;
import com.workfront.internship.workflow.domain.User;
import com.workfront.internship.workflow.exceptions.service.DuplicateEntryException;
import com.workfront.internship.workflow.exceptions.service.InvalidObjectException;
import com.workfront.internship.workflow.exceptions.service.ServiceLayerException;
import com.workfront.internship.workflow.service.UserService;
import com.workfront.internship.workflow.util.DBHelper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.sql.DataSource;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import static com.workfront.internship.workflow.dao.UserDAO.password;

/**
 * Created by Vahag on 6/4/2017
 */

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class);

    private final UserDAO userDAO;

    @Autowired
    public UserServiceImpl(@Qualifier("userDAOSpringImpl") UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public static boolean isEmpty(String string) {
        return string == null || string.trim().length() == 0;
    }

    /**
     * @param user
     * @return
     * @see UserService#add(User)
     */
    @Override
    public long add(User user) {
        if (user == null || !user.isValid()) {
            LOGGER.error("Not valid user. Failed to add.");
            throw new InvalidObjectException();
        }

        if (userDAO.getByEmail(user.getEmail()) != null) {
            LOGGER.error("Failed to add. User already exists");
            throw new DuplicateEntryException("User already exists");
        }
        user.setPassword(hashPassword(user.getPassword()));
        long id = userDAO.add(user);
        for (AppArea appArea : AppArea.values()) {
            userDAO.subscribeToArea(user.getId(), appArea.getId());
        }

        return id;
    }

    /**
     * @param name
     * @return
     * @see UserService#getByName(String)
     */

    @Override
    public List<User> getByName(String name) {
        if (isEmpty(name)) {
            LOGGER.error("Name is not valid");
            throw new InvalidObjectException("Not valid name");
        }

        List<User> users;
        try {
            users = userDAO.getByName(name);
        } catch (RuntimeException e) {
            LOGGER.error("Failed to find such users");
            throw new ServiceLayerException("Failed to find such users", e);
        }

        return users;
    }

    /**
     * @param id
     * @return
     * @see UserService#getById(long)
     */
    @Override
    public User getById(long id) {
        if (id < 1) {
            LOGGER.error("Id is not valid");
            throw new InvalidObjectException("Invalid user id");
        }

        User user;
        try {
            user = userDAO.getById(id);
        } catch (RuntimeException e) {
            LOGGER.error("Failed to find such a user");
            throw new ServiceLayerException("Failed to find such a user", e);
        }

        return user;
    }

    @Override
    public User getByEmail(String email) {
        if (isEmpty(email)){
            LOGGER.error("Email is not valid");
            throw new InvalidObjectException("Not valid email");
        }

        User user;
        try {
            user = userDAO.getByEmail(email);
        } catch (RuntimeException e){
            LOGGER.error("Couldn't get the user");
            throw new ServiceLayerException("Failed to find such a user", e);
        }

        return user;
    }

    /**
     * @param id
     * @return
     * @see UserService#getAppAreasById(long)
     */
    @Override
    public List<AppArea> getAppAreasById(long id) {
        if (id < 1) {
            LOGGER.error("Id is not valid");
            throw new InvalidObjectException("Invalid user id");
        }

        List<AppArea> appAreas;
        try {
            appAreas = userDAO.getAppAreasById(id);
        } catch (RuntimeException e) {
            LOGGER.error("Failed to find app areas");
            throw new ServiceLayerException("Failed to find app areas", e);
        }

        return appAreas;
    }

    /**
     * @param userId
     * @param appAreaId
     * @see UserService#subscribeToArea(long, long)
     */
    @Override
    public void subscribeToArea(long userId, long appAreaId) {
        if (userId < 1) {
            LOGGER.error("Id is not valid");
            throw new InvalidObjectException("Invalid user id");
        }

        if (appAreaId < 1) {
            LOGGER.error("Id is not valid");
            throw new InvalidObjectException("Invalid app area id");
        }

        try {
            userDAO.subscribeToArea(userId, appAreaId);
        } catch (RuntimeException e) {
            LOGGER.error("Failed to subscribe to the app area");
            throw new ServiceLayerException("Failed to subscribe to the app area", e);
        }
    }

    /**
     * @param userId
     * @param appAreaId
     * @see UserService#unsubscribeToArea(long, long)
     */
    @Override
    public void unsubscribeToArea(long userId, long appAreaId) {
        if (userId < 1) {
            LOGGER.error("Id is not valid");
            throw new InvalidObjectException("Invalid user id");
        }

        if (appAreaId < 1) {
            LOGGER.error("Id is not valid");
            throw new InvalidObjectException("Invalid app area id");
        }

        try {
            userDAO.unsubscribeToArea(userId, appAreaId);
        } catch (RuntimeException e) {
            LOGGER.error("Failed to unsubscribe from the app area");
            throw new ServiceLayerException("Failed to unsubscribe from the app area", e);
        }
    }

    /**
     * @param id
     * @see UserService#deleteById(long)
     */
    @Override
    public void deleteById(long id) {
        if (id < 1) {
            LOGGER.error("Id is not valid");
            throw new InvalidObjectException("Not valid id");
        }

        try {
            userDAO.deleteById(id);
        } catch (RuntimeException e) {
            LOGGER.error("Failed to delete the user");
            throw new ServiceLayerException("Failed to delete the user", e);
        }
    }

    /**
     * @see UserService#deleteAll()
     */
    @Override
    public void deleteAll() {
        try {
            userDAO.deleteAll();
        } catch (RuntimeException e) {
            LOGGER.error("Failed to delete all the user");
            throw new ServiceLayerException("Failed to delete all the user", e);
        }
    }

    /**
     * @see UserService#authenticate(String, String)
     * @param email is input from client
     * @param password is input from client
     */
    @Override
    public User authenticate(String email, String password) {
        if (isEmpty(password)){
            LOGGER.error("Password is not valid");
            throw new InvalidObjectException("Not valid password");
        }

        User user = getByEmail(email);

        //TODO: Password should be hashed
        if (user != null && user.getPassword().equals(hashPassword(password))){
            return user;
        }else {
            LOGGER.error("Invalid email-password combination!");
            throw new ServiceLayerException("Invalid email-password combination!");
        }
    }

    /**
     * @param user is input from client
     * @see UserService#sendEmail(User)
     */
    @Override
    public void sendEmail(User user) {
        if (!user.isValid()) {
            LOGGER.error("Not valid user. Failed to add.");
            throw new InvalidObjectException();
        }

        String EMAIL = "nanevardanyants@gmail.com";
        String PASSWORD = "3modern!012";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        Session session = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(EMAIL, PASSWORD);
                    }
                });
        try {
            //Creating MimeMessage object
            MimeMessage mm = new MimeMessage(session);
            //Setting sender address
            mm.setFrom(new InternetAddress(EMAIL));
            //Adding receiver
            mm.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(user.getEmail()));
            //Adding subject
            mm.setSubject("Hello");
            //Adding message
            mm.setText("Here is the message");
            //sending Email
            Transport.send(mm);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] buffer = password.getBytes("UTF-8");
            md.update(buffer);
            byte[] digest = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte aDigest : digest) {
                sb.append(Integer.toString((aDigest & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
            return password;
        }
    }
}
