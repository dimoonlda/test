package ua.kiev.test.testwebmvc.utils;

import org.springframework.util.StringUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by lezha on 22.02.2015.
 */
public class SecurityUtils {

    private static final String SALT = "[B@7adf9f5f";

    public static String hashPassword(String password) {
        if (StringUtils.isEmpty(password)) {
            throw new IllegalArgumentException("password should not be empty");
        }

        try {
            MessageDigest md = MessageDigest.getInstance("SHA");
            md.update(SALT.getBytes());
            byte[] bytes = md.digest(password.getBytes());

            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }

            //Get complete hashed password in hex format
            return sb.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }

        return null;
    }


}
