package ua.kiev.test.testwebmvc.utils;

import com.google.common.base.Optional;
import org.joda.time.DateTime;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Service;
import ua.kiev.test.testwebmvc.model.SecurityUser;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Service
public class TokenUtilsImpl implements TokenUtils {

    private static final long EXPIRATION_TIME = 1000L * 60 * 60; //one hour

    private static Map<String, SecurityUser> map = new HashMap<>();

    @Override
    public boolean validate(SecurityUser user, String token) {
        if(user ==null || !user.isCredentialsNonExpired()) {
            map.remove(token); // remove expired token
            return false;
        } else {
            user.setExpirationDate(this.updateDate());
            return user.isAccountNonExpired() && user.isAccountNonLocked() && user.isEnabled()
                    && isNotExpired(user);
        }
    }

    @Override
    public boolean isNotExpired(SecurityUser user) {
        return user!=null && (user.getExpirationDate()==null
                || new Date().getTime()<user.getExpirationDate().getMillis())
                /*&& CollectionUtils.isNotEmpty(user.getAuthorities())*/;//todo
    }

    @Override
    public String generate(SecurityUser user) {
        user.setExpirationDate(this.updateDate());
        String token = this.generateToken(user);
        map.put(token, user);
        return token;
    }

    @Override
    public SecurityUser getUserByToken(String token) throws UsernameNotFoundException {
        SecurityUser user = map.get(token);
        Optional<SecurityUser> opt =  Optional.fromNullable(user);
        if(!opt.isPresent()) {
            throw new UsernameNotFoundException("User not found.");
        }
        return opt.get();
    }

    private String generateToken(SecurityUser user) {
        String random = UUID.randomUUID().toString();
        String keySource = user.getUsername() + user.getExpirationDate() + random;
        byte [] tokenByte = Base64.encode(keySource.getBytes());
        return new String(tokenByte);
    }

    private DateTime updateDate() {
        return new DateTime(System.currentTimeMillis() + EXPIRATION_TIME);
    }

    @Override
    public void removeToken(String token) {
        map.remove(token);
    }

}
