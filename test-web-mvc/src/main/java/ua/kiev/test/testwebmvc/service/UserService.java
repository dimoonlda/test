package ua.kiev.test.testwebmvc.service;

import ua.kiev.test.testwebmvc.model.User;

/**
 * Created by lezha on 22.02.2015.
 */
public interface UserService {

    //User findByLoginAndPassword(LoginDto login);

    User findUserByLogin(String login);
    User findUserById(Long id);

    User create(User user);

    void save(User user);
}
