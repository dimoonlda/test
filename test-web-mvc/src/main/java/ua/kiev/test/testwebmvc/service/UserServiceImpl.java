package ua.kiev.test.testwebmvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.kiev.test.testwebmvc.model.User;
import ua.kiev.test.testwebmvc.repository.UserRepository;
import ua.kiev.test.testwebmvc.utils.SecurityUtils;

import javax.annotation.PostConstruct;
import java.util.UUID;

/**
 * Created by lezha on 22.02.2015.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    @Transactional
    public void populate() {

        User user = new User();
        user.setLogin("user");
        user.setPassword("pass");
        user.setEnabled(true);
        user.setExpirationDate(null);
        user.setRole("ADMIN");
        userRepository.saveAndFlush(user);
    }
/*

    @Override
    public String login(LoginDto login) {
        User user = userRepository.findByLoginAndPassword(login.getLogin(), login.getPassword());

        if (user != null) {
            return UUID.randomUUID().toString();
        } else {
            throw new BadCredentialsException("Wrong user name or password");
        }
    }

    @Override
    public User findByLoginAndPassword(LoginDto login) {
        return userRepository.findByLoginAndPassword(login.getLogin(), login.getPassword());
    }


    @Override
    public boolean changePassword(ChangePasswordDto chPasswDto) {
        User user = userRepository.findByLoginAndPassword(chPasswDto.getLogin(), chPasswDto.getPassword());

        if (user != null) {
            user.setPassword(chPasswDto.getNewPassword());
            userRepository.saveAndFlush(user);

            return true;
        } else {
            return false;
        }
    }
*/
    @Override
    public User findUserByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Override
    public User findUserById(Long id) {
        return userRepository.findOne(id);
    }

    @Override
    public User create(User user) {
        user.setPassword(SecurityUtils.hashPassword(user.getPassword()));
        return userRepository.saveAndFlush(user);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }
}
