package com.workfront.internship.workflow.service.impl;

import com.workfront.internship.workflow.controller.utils.EmailType;
import com.workfront.internship.workflow.dao.PostDAO;
import com.workfront.internship.workflow.dao.UserDAO;
import com.workfront.internship.workflow.dao.impl.PostDAOImpl;
import com.workfront.internship.workflow.entity.Post;
import com.workfront.internship.workflow.entity.User;
import com.workfront.internship.workflow.exceptions.dao.DAOException;
import com.workfront.internship.workflow.exceptions.service.InvalidObjectException;
import com.workfront.internship.workflow.exceptions.service.NotificationEmailSendFailedException;
import com.workfront.internship.workflow.exceptions.service.ServiceLayerException;
import com.workfront.internship.workflow.service.PostService;
import com.workfront.internship.workflow.service.util.ServiceUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by nane on 6/4/17
 */
@Service
@Transactional
public class PostServiceImpl implements PostService {

    private static final Logger logger = Logger.getLogger(PostDAO.class);

    private PostDAO postDAO;
    private UserDAO userDAO;

    @Autowired
    public PostServiceImpl(@Qualifier("postDAOSpringImpl") PostDAO postDAO,
                           @Qualifier("userDAOSpringImpl") UserDAO userDAO) {
        this.postDAO = postDAO;
        this.userDAO = userDAO;
    }

    public PostServiceImpl() {

    }

    public static boolean isEmpty(String string) {
        return string == null || string.isEmpty();
    }

    /**
     * @see PostService#add(Post)
     */
    @Override
    public long add(Post post) {
        if (post == null || !post.isValid()) {
            logger.error("Post is invalid. Failed to add to the database");
            throw new InvalidObjectException("Invalid Post");
        }
        long id;
        try {
            id = postDAO.add(post);
        } catch (DAOException e) {
            logger.error("Failed to add the post to database");
            throw new ServiceLayerException("Failed to add the post to database", e);
        }
        return id;
    }

    /**
     * @see PostService#setBestAnswer(long, long)
     */
    @Override
    public void setBestAnswer(long postId, long answerId) {
        if (postId < 1) {
            logger.error("Post id is not valid");
            throw new InvalidObjectException("Invalid post id");
        }
        if (answerId < 1) {
            logger.error("Answer id is not valid");
            throw new InvalidObjectException("Invalid answer id");
        }
        try {
            postDAO.setBestAnswer(postId, answerId);
            User postOwner = postDAO.getById(postId).getUser();
            ServiceUtils.increaseRating(postOwner, 10);
            userDAO.updateRating(postOwner);

        } catch (DAOException e) {
            logger.error("Failed during setting the best answer");
            throw new ServiceLayerException("Failed during setting the best answer", e);
        }
    }

    /**
     * @see PostService#getAll()
     */
    @Override
    public List<Post> getAll() {
        List<Post> posts;
        try {
            posts = postDAO.getAll();
            return posts;
        } catch (DAOException e) {
            logger.error(e.getStackTrace());
            throw new ServiceLayerException("Failed to get all posts");
        }
    }

    /**
     * @see PostService#getPostsByPage(long)
     */
    @Override
    public List<Post> getPostsByPage(long pageNumber) {
        if (pageNumber < 1) {
            logger.error("Id is not valid");
            throw new InvalidObjectException("Invalid page number");
        }

        List<Post> posts;
        long rowNumber = ServiceUtils.getRowNumberByPage(pageNumber);

        try {
            posts = postDAO.getPostsByPage(rowNumber);
            return posts;
        } catch (DAOException e) {
            logger.error(e.getStackTrace());
            throw new ServiceLayerException("Failed to get posts");
        }
    }

    /**
     * @see PostService#getById(long)
     */
    @Override
    public Post getById(long id) {
        if (id < 1) {
            logger.error("Id is not valid");
            throw new InvalidObjectException("Invalid post id");
        }
        Post post;
        try {
            post = postDAO.getById(id);
        } catch (DAOException e) {
            logger.error(e.getStackTrace());
            throw new ServiceLayerException("Failed to get post with specified id");
        }
        return post;
    }

    /**
     * @see PostService#getByTitle(String)
     */
    @Override
    public List<Post> getByTitle(String title) {
        if (isEmpty(title)) {
            logger.error("Title is not valid");
            throw new InvalidObjectException("Not valid answerTitle");
        }
        List<Post> posts;
        try {
            posts = postDAO.getByTitle(title);
            return posts;
        } catch (DAOException e) {
            throw new ServiceLayerException("Failed to get posts by specified answerTitle");
        }

    }

    /**
     * @see PostService#getByUserId(long)
     */
    @Override
    public List<Post> getByUserId(long id) {
        if (id < 1) {
            logger.error("Id is not valid");
            throw new InvalidObjectException("Not valid id");
        }
        List<Post> posts;
        try {
            posts = postDAO.getByUserId(id);
            return posts;
        } catch (DAOException e) {
            throw new ServiceLayerException("Failed to get posts by specified user id");
        }

    }

    /**
     * @see PostService#getByAppAreaId(long)
     */
    @Override
    public List<Post> getByAppAreaId(long id) {
        if (id < 1) {
            logger.error("Id is not valid");
            throw new InvalidObjectException("Not valid id");
        }
        List<Post> posts;
        try {
            posts = postDAO.getByAppAreaId(id);
            return posts;
        } catch (DAOException e) {
            throw new ServiceLayerException("Failed to get posts by specified app area id");
        }

    }

    /**
     * @see PostService#getAnswersByPostId(long)
     */
    @Override
    public List<Post> getAnswersByPostId(long id) {
        if (id < 1) {
            logger.error("Id is not valid");
            throw new InvalidObjectException("Not valid id");
        }
        List<Post> posts;
        try {
            posts = postDAO.getAnswersByPostId(id);
            return posts;
        } catch (DAOException e) {
            e.printStackTrace();
            throw new ServiceLayerException("Failed to get answers of the specified post");
        }

    }

    /**
     * @see PostService#getAnswersByUserId(long)
     */
    @Override
    public List<Post> getAnswersByUserId(long id) {
        if (id < 1) {
            logger.error("Id is not valid");
            throw new InvalidObjectException("Not valid id");
        }
        List<Post> answers;
        try {
            answers = postDAO.getAnswersByUserId(id);
            return answers;
        } catch (DAOException e) {
            e.printStackTrace();
            throw new ServiceLayerException("Failed to get answers of the specified user");
        }
    }

    /**
     * @see PostService#getBestAnswer(long)
     */
    @Override
    public Post getBestAnswer(long id) {
        if (id < 1) {
            logger.error("Id is not valid");
            throw new InvalidObjectException("Not valid id");
        }

        Post post;
        try {
            post = postDAO.getBestAnswer(id);
            return post;
        } catch (DAOException e) {
            throw new ServiceLayerException("Failed to get the best answer of the specified post");
        }

    }

    /**
     * @see PostService#getLikesNumber(long)
     */
    @Override
    public long getLikesNumber(long postId) {
        if (postId < 1) {
            logger.error("Id is not valid");
            throw new InvalidObjectException("Not valid id");
        }

        try {
            return postDAO.getLikesNumber(postId);
        } catch (DAOException e) {
            throw new ServiceLayerException("Failed to like the post");
        }
    }

    /**
     * @see PostService#getDislikesNumber(long)
     */
    @Override
    public long getDislikesNumber(long postId) {
        if (postId < 1) {
            logger.error("Id is not valid");
            throw new InvalidObjectException("Not valid id");
        }

        try {
            return postDAO.getDislikesNumber(postId);
        } catch (DAOException e) {
            throw new ServiceLayerException("Failed to like the post");
        }
    }

    /**
     * @see PostService#like(long, long)
     */
    @Override
    public void like(long userId, long postId) {
        if (userId < 1 || postId < 1) {
            logger.error("Id is not valid");
            throw new InvalidObjectException("Not valid id");
        }

        try {
            postDAO.like(userId, postId);
            User postOwner = postDAO.getById(postId).getUser();
            ServiceUtils.increaseRating(postOwner, 1);
            userDAO.updateRating(postOwner);
        } catch (DAOException e) {
            throw new ServiceLayerException("Failed to like the post");
        }
    }

    /**
     * @see PostService#like(long, long)
     */
    @Override
    public void dislike(long userId, long postId) {
        if (userId < 1 || postId < 1) {
            logger.error("Id is not valid");
            throw new InvalidObjectException("Not valid id");
        }

        try {
            postDAO.dislike(userId, postId);
            User postOwner = postDAO.getById(postId).getUser();
            ServiceUtils.decreaseRating(postOwner, 1);
            userDAO.updateRating(postOwner);
        } catch (DAOException e) {
            throw new ServiceLayerException("Failed to dislike the post");
        }
    }

    /**
     * @see PostService#removeLike(long, long)
     */
    @Override
    public void removeLike(long userId, long postId) {
        if (userId < 1 || postId < 1) {
            logger.error("Id is not valid");
            throw new InvalidObjectException("Not valid id");
        }

        try {
            postDAO.removeLike(userId, postId);
            User postOwner = postDAO.getById(postId).getUser();
            ServiceUtils.decreaseRating(postOwner, 1);
            userDAO.updateRating(postOwner);
        } catch (DAOException e) {
            throw new ServiceLayerException("Failed to remove the like to the post");
        }
    }

    /**
     * @see PostService#removeDislike(long, long)
     */
    @Override
    public void removeDislike(long userId, long postId) {
        if (userId < 1 || postId < 1) {
            logger.error("Id is not valid");
            throw new InvalidObjectException("Not valid id");
        }

        try {
            postDAO.removeDislike(userId, postId);
            User postOwner = postDAO.getById(postId).getUser();
            ServiceUtils.increaseRating(postOwner, 1);
            userDAO.updateRating(postOwner);
        } catch (DAOException e) {
            throw new ServiceLayerException("Failed to remove the dislike to the post");
        }
    }

    /**
     * @see PostService#update(Post)
     */
    @Override
    public void update(Post post) {
        if (post == null || !post.isValid()) {
            logger.error("Post is invalid. Failed to add to the database");
            throw new InvalidObjectException("Invalid Post");
        }
        try {
            postDAO.update(post);
        } catch (DAOException e) {
            logger.error("Failed to update the post");
            throw new ServiceLayerException("Failed to update the post", e);
        }
    }


    /**
     * @see PostService#delete(long)
     */
    @Override
    public void delete(long id) {
        if (id < 1) {
            logger.error("Id is not valid");
            throw new InvalidObjectException("Not valid id");
        }
        try {
            postDAO.delete(id);
        } catch (DAOException e) {
            logger.error("Failed to delete specified posts");
            throw new ServiceLayerException("Failed to delete specified posts", e);
        }
    }

    /**
     * @see PostService#getNumberOfAnswers(long)
     */
    @Override
    public Integer getNumberOfAnswers(long postId) {
        if (postId < 1) {
            logger.error("Id is not valid");
            throw new InvalidObjectException("Not valid id");
        }
        Integer numbOfAnswers;
        try {
            numbOfAnswers = postDAO.getNumberOfAnswers(postId);
        } catch (DAOException e) {
            logger.error("Failed to delete specified posts");
            throw new ServiceLayerException("Failed to delete specified posts", e);
        }
        return numbOfAnswers;
    }

    /**
     * @see PostService#getNotified(long, long)
     */
    @Override
    public void getNotified(long postId, long userId) {
        if (postId < 1 || userId < 1) {
            logger.error("Id is not valid");
            throw new InvalidObjectException("Not valid id");
        }

        try {
            postDAO.getNotified(postId, userId);
        } catch (DAOException e) {
            logger.error("Failed to turn notification on for specified post and user");
            throw new ServiceLayerException("Failed to delete specified posts", e);
        }
    }

    /**
     * @see PostService#removeBestAnswer(long)
     */
    @Override
    public void removeBestAnswer(long answerId) {
        if (answerId < 1) {
            logger.error("Id is not valid");
            throw new InvalidObjectException("Not valid id");
        }

        try {
            postDAO.removeBestAnswer(answerId);
            User postOwner = postDAO.getById(answerId).getUser();
            ServiceUtils.decreaseRating(postOwner, 10);
            userDAO.updateRating(postOwner);
        } catch (DAOException e) {
            logger.error("Failed to remove the best answer");
            throw new ServiceLayerException("Failed to remove the best answer", e);
        }
    }

    /**
     * @see PostService#getNotificationRecipients(long)
     */
    @Override
    public List<User> getNotificationRecipients(long postId) {
        if (postId < 1) {
            logger.error("Id is not valid");
            throw new InvalidObjectException("Not valid id");
        }

        List<User> users;
        try {
            users = postDAO.getNotificationRecipients(postId);
        } catch (DAOException e) {
            logger.error("Failed to get users with specified id");
            throw new ServiceLayerException("Failed to get users with specified id", e);
        }
        return users;
    }

    /**
     * @see PostService#notifyUsers(List, Post, EmailType)
     */
    @Override
    public void notifyUsers(List<User> users, Post post, EmailType type) {
        if (users == null) {
            logger.error("Not valid userList. Failed to send emails.");
            throw new InvalidObjectException();
        }

        for (User user : users) {
            if (user == null || !user.isValid()) {
                logger.error("Not valid user. Failed to send email.");
                throw new InvalidObjectException();
            }
        }

        try {
            for (User user : users) {
                Map<String, String> subjectContent = getEmailSubjectContent(post, user, type);
                sendEmail(user, subjectContent.get("subject"), subjectContent.get("content"));
            }
        } catch (DAOException e) {
            logger.error("Failed to send emails to all users");
            throw new NotificationEmailSendFailedException("Failed to send emails to all users", e);
        }

    }

    private Map<String, String> getEmailSubjectContent(Post post, User user, EmailType type) {
        Map<String, String> subjectContent = new HashMap<>();
        String subject = null;
        String content = null;
        if (type.equals(EmailType.NEW_RESPONSE)) {
            subject = "Response on '" + post.getTitle() + "' topic.";
            content = "Dear " + user.getFirstName() + ", \n \nThere has been a new response " +
                    " on '" + post.getTitle() + "' topic. " +
                    " You can follow the link below: \n http://localhost:8080/post/" + post.getId() +
                    " \n \n Best, \n Workflow Team";
        } else if (type.equals(EmailType.NEW_POST)) {
            subject = "Check out the new post on '" + post.getAppArea().getName() + "' topic";
            content = "Dear " + user.getFirstName() + ", \n \n There is a new post on " +
                    post.getAppArea().getName() + " application area. " +
                    " You can follow the link below to view and share your response:" +
                    " \n http://localhost:8080/post/" + post.getId() +
                    " \n \n Best, \n Workflow Team";
        }
        subjectContent.put("content", content);
        subjectContent.put("subject", subject);

        return subjectContent;
    }

    private void sendEmail(User user, String subject, String text) {
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
        try {
            //Creating MimeMessage object
            MimeMessage mm = new MimeMessage(session);
            //Setting sender address
            mm.setFrom(new InternetAddress(EMAIL));
            //Adding receiver
            mm.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(user.getEmail()));
            //Adding subject
            mm.setSubject(subject);
            //Adding message
            mm.setText(text);
            //sending Email
            Transport.send(mm);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}

