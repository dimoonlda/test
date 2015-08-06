package ua.kiev.test.testwebmvc.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lezha on 22.02.2015.
 */
@Entity
@Table(name="ds_user")
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator="ds_user_seq_gen")
    @SequenceGenerator(name="ds_user_seq_gen", sequenceName="DS_USER_SEQ")
    private Long id;

    @Column(nullable = false, unique = true)
    private String login;
//    @JsonIgnore//todo uncomment
    @Column(name = "pwd", nullable = false)
    private String password;
    @Column(name = "uRole")
    @JsonIgnore
    private String role;
    private boolean enabled;
    //@Transient//todo
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (enabled != user.enabled) return false;
        if (!id.equals(user.id)) return false;
        if (!login.equals(user.login)) return false;
        if (!password.equals(user.password)) return false;
        if (role != null ? !role.equals(user.role) : user.role != null) return false;
        if (expirationDate != null ? !expirationDate.equals(user.expirationDate) : user.expirationDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + login.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + (role != null ? role.hashCode() : 0);
        result = 31 * result + (expirationDate != null ? expirationDate.hashCode() : 0);
        result = 31 * result + (enabled ? 1 : 0);
        return result;
    }

}
