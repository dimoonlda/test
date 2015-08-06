package ua.kiev.test.testwebmvc.controllers;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lutay.d on 03.08.2015.
 */
@RestController
@RequestMapping(value = "/test")
@Api(value="/test", description="My test controller", position = 1)
public class MyTestController {

    private static final Logger logger = LoggerFactory.getLogger(MyTestController.class);

    @ApiOperation(value = "Just say hello.", notes = "You can call this method for saying hello.", response = String.class)
    @RequestMapping(value = "/hello/{name}", method = RequestMethod.GET, produces = "application/json")
    public String hello(@PathVariable("name") String name){
        logger.info("Request hello method with param: ");
        logger.info("name = " + name);
        return "Hello!";
    }
}
