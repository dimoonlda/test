package ua.kiev.test.testwebmvc.controllers;

import com.wordnik.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ua.kiev.test.testwebmvc.model.LoginDto;
import ua.kiev.test.testwebmvc.model.SecurityUser;
import ua.kiev.test.testwebmvc.model.User;
import ua.kiev.test.testwebmvc.service.UserService;
import ua.kiev.test.testwebmvc.utils.TokenUtils;


@RestController
@RequestMapping("/auth")
public class AuthController {

    private final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationProvider authManager;

    @Autowired
    private TokenUtils tokenUtils;

    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public String login(@RequestBody LoginDto login) {
        User user = userService.findUserByLogin(login.getLogin());

        if (user == null){
            logger.error(String.format("Authentication failed, user(%s) not found.", login.getLogin()));
            throw new AuthenticationServiceException("Authentication failed, user not found.");
        }

//        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(login.getLogin(),
//                login.getPassword());
        SecurityUser secUser = new SecurityUser(user);
//        authentication.setDetails(secUser);
//        SecurityContextHolder.getContext().setAuthentication(this.authManager.authenticate(authentication));
        return tokenUtils.generate(secUser);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/logout")
    public void logout(@RequestHeader(value = TokenUtils.USER_TOKEN) String token) {
        tokenUtils.removeToken(token);
    }

    @ApiOperation(value = "Fires on authorization failure")
    @RequestMapping(value = "/failed", method = RequestMethod.GET)
    public void getFailed() {
        throw new AuthenticationServiceException("Authentication failed.");
    }

    @ApiOperation(value = "Fires on authorization failure if user expired")
    @RequestMapping(value = "/expired", method = RequestMethod.GET)
    public void getExpired() {
        throw new AuthenticationServiceException("User expired");
    }

}
