package com.workfront.internship.workflow.service.impl;

import com.workfront.internship.workflow.dao.UserDAO;
import com.workfront.internship.workflow.entity.AppArea;
import com.workfront.internship.workflow.entity.Post;
import com.workfront.internship.workflow.entity.User;
import com.workfront.internship.workflow.exceptions.dao.DAOException;
import com.workfront.internship.workflow.exceptions.service.NoSuchUserException;
import com.workfront.internship.workflow.exceptions.service.DuplicateEntryException;
import com.workfront.internship.workflow.exceptions.service.InvalidObjectException;
import com.workfront.internship.workflow.exceptions.service.NotExistingEmailException;
import com.workfront.internship.workflow.exceptions.service.ServiceLayerException;
import com.workfront.internship.workflow.service.UserService;
import com.workfront.internship.workflow.service.util.ServiceUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;

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
        user.setPassword(ServiceUtils.hashString(user.getPassword()));
        long id = userDAO.add(user);
        for (AppArea appArea : AppArea.values()) {
            userDAO.subscribeToArea(user.getId(), appArea.getId());
        }

        return id;
    }

    /**
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
        } catch (DAOException e) {
            LOGGER.error("Failed to find such users");
            throw new ServiceLayerException("Failed to find such users", e);
        }

        return users;
    }

    /**
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
        } catch (DAOException e) {
            LOGGER.error("Failed to find such a user");
            throw new ServiceLayerException("Failed to find such a user", e);
        }

        return user;
    }

    /**
     * @see UserService#getByEmail(String)
     */
    @Override
    public User getByEmail(String email) {
        if (isEmpty(email)) {
            LOGGER.error("Email is not valid");
            throw new InvalidObjectException("Not valid email");
        }

        User user;
        try {
            user = userDAO.getByEmail(email);
        } catch (DAOException e) {
            LOGGER.error("Couldn't get the user");
            throw new ServiceLayerException("Failed to find such a user", e);
        }

        return user;
    }

    /**
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
        } catch (DAOException e) {
            LOGGER.error("Failed to find app areas");
            throw new ServiceLayerException("Failed to find app areas", e);
        }

        return appAreas;
    }

    /**
     * @see UserService#getLikedPosts(long)
     */
    @Override
    public List<Post> getLikedPosts(long id) {
        if (id < 1) {
            LOGGER.error("Id is not valid");
            throw new InvalidObjectException("Invalid user id");
        }

        List<Post> posts;
        try {
            posts = userDAO.getLikedPosts(id);
        } catch (DAOException e) {
            LOGGER.error("Failed to find liked posts");
            throw new ServiceLayerException("Failed to find liked posts", e);
        }
        return posts;
    }

    /**
     * @see UserService#getDislikedPosts(long)
     */
    @Override
    public List<Post> getDislikedPosts(long id) {
        if (id < 1) {
            LOGGER.error("Id is not valid");
            throw new InvalidObjectException("Invalid user id");
        }

        List<Post> posts;
        try {
            posts = userDAO.getDislikedPosts(id);
        } catch (DAOException e) {
            LOGGER.error("Failed to find disliked posts");
            throw new ServiceLayerException("Failed to find disliked posts", e);
        }
        return posts;
    }

    /**
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
        } catch (DAOException e) {
            LOGGER.error("Failed to subscribe to the app area");
            throw new ServiceLayerException("Failed to subscribe to the app area", e);
        }
    }

    /**
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
        } catch (DAOException e) {
            LOGGER.error("Failed to unsubscribe from the app area");
            throw new ServiceLayerException("Failed to unsubscribe from the app area", e);
        }
    }

    /**
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
        } catch (DAOException e) {
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
        } catch (DAOException e) {
            LOGGER.error("Failed to delete all the user");
            throw new ServiceLayerException("Failed to delete all the user", e);
        }
    }

    /**
     * @see UserService#authenticate(String, String)
     */
    @Override
    public User authenticate(String email, String password) {
        if (isEmpty(password)) {
            LOGGER.error("Password is not valid");
            throw new InvalidObjectException("Not valid password");
        }

        User user = getByEmail(email);

        if (user != null && user.getPassword().equals(ServiceUtils.hashString(password))) {
            return user;
        } else {
            LOGGER.error("Invalid email-password combination!");
            throw new NoSuchUserException("Invalid email-password combination!");
        }
    }

    /**
     * @see UserService#sendEmail(User)
     */
    @Override
    public String sendEmail(User user) {
        if (user == null || !user.isValid()) {
            LOGGER.error("Not valid user. Failed to add.");
            throw new InvalidObjectException();
        }

        String EMAIL = "workfront.internship@gmail.com";
        String PASSWORD = "project2017";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(EMAIL, PASSWORD);
                    }
                });
        String verificationCode = ServiceUtils.hashString(ServiceUtils.hashString(user.getPassword())).substring(0, 6);
        try {
            //Creating MimeMessage object
            MimeMessage mm = new MimeMessage(session);
            //Setting sender address
            mm.setFrom(new InternetAddress(EMAIL));
            //Adding receiver
            mm.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(user.getEmail()));
            //Adding subject
            mm.setSubject("Welcome");
            //Adding message
            mm.setText("Dear " + user.getFirstName() + ", \n Welcome to Workflow! " +
                    "Here is your verification code: " + verificationCode + ". \n Please use it to finish your sign up.");
            //sending Email
            Transport.send(mm);

        } catch (SendFailedException e) {
            LOGGER.error("The recipient address is not a valid");
            throw new NotExistingEmailException("The recipient address is not a valid", e);
        } catch (MessagingException e) {
            LOGGER.error("Failed to send an email");
            throw new ServiceLayerException("Failed to send an email", e);
        }
        return verificationCode;
    }

    @Override
    public void updateProfile(User user) {
        if (user == null || !user.isValid()) {
            LOGGER.error("Not valid user. Failed to add.");
            throw new InvalidObjectException();
        }

        try {
            userDAO.updateProfile(user);
        } catch (DAOException e) {
            LOGGER.error("Failed to update user's profile");
            throw new ServiceLayerException("Failed to update user's profile", e);
        }
    }

    @Override
    public void updateAvatar(User user) {
        if (user == null || !user.isValid()) {
            LOGGER.error("Not valid user. Failed to add.");
            throw new InvalidObjectException();
        }

        try {
            userDAO.updateAvatar(user);
        } catch (DAOException e) {
            LOGGER.error("Failed to update user's profile");
            throw new ServiceLayerException("Failed to update user's profile", e);
        }
    }

    @Override
    public String verifyNewPassword(User user, String oldPassword,
                                  String newPassword, String confirmPassword) {

        if (ServiceUtils.hashString(oldPassword).equals(user.getPassword())
                && newPassword.equals(confirmPassword)) {
            user.setPassword(newPassword);
            return ServiceUtils.hashString(newPassword);
        }else {
            throw new ServiceLayerException("The password is incorrect");
        }
    }
}
