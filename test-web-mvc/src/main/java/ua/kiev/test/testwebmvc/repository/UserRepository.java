package ua.kiev.test.testwebmvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.kiev.test.testwebmvc.model.User;

/**
 * Created by lezha on 05.08.2014.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByLoginAndPassword(String login, String password);

    User findByLogin(String login);

}
