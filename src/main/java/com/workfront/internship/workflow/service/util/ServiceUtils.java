package com.workfront.internship.workflow.service.util;

import com.workfront.internship.workflow.entity.User;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by nane on 6/29/17
 */
public class ServiceUtils {

    public static String hashString(String text) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] buffer = text.getBytes("UTF-8");
            md.update(buffer);
            byte[] digest = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte aDigest : digest) {
                sb.append(Integer.toString((aDigest & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
            return text;
        }
    }

    public static void increaseRating(User user, int amount) {
        int userRating = user.getRating();
        userRating += amount;
        user.setRating(userRating);
    }

    public static void decreaseRating(User user, int amount) {
        int userRating = user.getRating();
        userRating -= amount;
        user.setRating(userRating);
    }

    public static long getRowNumberByPage(long pageNumber) {
        long rowNumber ;

        if (pageNumber == 1) {
            rowNumber = 0;
        } else {
            rowNumber = (pageNumber - 1) * 5;
        }

        return rowNumber;
    }
}
