package ua.kiev.test.testwebmvc.utils;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ua.kiev.test.testwebmvc.model.SecurityUser;


public interface TokenUtils {
    public static final String USER_TOKEN = "sectoken";

    public boolean validate(SecurityUser user, String token);

    boolean isNotExpired(SecurityUser user);

    public String generate(SecurityUser user);

    public SecurityUser getUserByToken(String token) throws UsernameNotFoundException;

    void removeToken(String token);
}
