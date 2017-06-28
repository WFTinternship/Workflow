package com.workfront.internship.workflow.service;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by nane on 6/29/17
 */
public class ServiceUtils {

    public static String hashPassword(String password) {
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
