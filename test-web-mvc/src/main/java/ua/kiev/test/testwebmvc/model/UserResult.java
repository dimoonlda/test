package ua.kiev.test.testwebmvc.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.joda.time.DateTime;

import javax.persistence.Column;
import javax.persistence.Transient;
import java.util.Date;

/**
 * Created by lutay.d on 05.08.2015.
 */
public class UserResult {
    private Long id;
    private String login;
    private String role;
    private boolean enabled;
    private DateTime expirationDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public DateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(DateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public static UserResult createFromUser(User user){
        UserResult result = new UserResult();
        result.setRole(user.getRole());
        result.setExpirationDate(user.getExpirationDate());
        result.setEnabled(user.isEnabled());
        result.setId(user.getId());
        result.setLogin(user.getLogin());
        return result;
    }
}
