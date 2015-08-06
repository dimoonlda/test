package ua.kiev.test.testwebmvc.controllers;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ua.kiev.test.testwebmvc.exceptions.ObjectNotFoundException;
import ua.kiev.test.testwebmvc.model.User;
import ua.kiev.test.testwebmvc.model.UserResult;
import ua.kiev.test.testwebmvc.service.UserService;

/**
 * Created by lutay.d on 05.08.2015.
 */
@RestController
@RequestMapping(value = "/user")
@Api(value="/user", description="User controller", position = 2)
public class UserController {

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @ApiOperation(value = "Get user by login.", notes = "", response = UserResult.class)
    @RequestMapping(value = "/{name}", method = RequestMethod.GET, produces = "application/json")
    public UserResult getUserInfo(@PathVariable("name") String name){
        logger.info("Request getUserInfo method with param: ");
        logger.info("name = " + name);
        User user = userService.findUserByLogin(name);
        if (user==null){
            throw new ObjectNotFoundException(String.format("User with login = %s not found.", name));
        }
        return UserResult.createFromUser(user);
    }

}
