package ua.kiev.test.testwebmvc.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ua.kiev.test.testwebmvc.config.TestContext;
import ua.kiev.test.testwebmvc.config.WebMvcConfig;
import ua.kiev.test.testwebmvc.model.User;
import ua.kiev.test.testwebmvc.model.UserResult;
import ua.kiev.test.testwebmvc.service.UserService;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Тест работы ТОЛЬКО контроллера
 * Created by lutay.d on 05.08.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContext.class, WebMvcConfig.class})
@WebAppConfiguration
public class UserControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private UserService userServiceMock;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        //We have to reset our mock between tests because the mock objects
        //are managed by the Spring container. If we would not reset them,
        //stubbing and verified behavior would "leak" from one test to another.
        Mockito.reset(userServiceMock);

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testGetUserInfo_ShoulReturnUser() throws Exception {
        User result = new User();
        result.setId(100L);
        result.setLogin("username");
        result.setEnabled(Boolean.TRUE);
        result.setRole("ADMIN");
        result.setExpirationDate(null);

        when(userServiceMock.findUserByLogin(anyString())).thenReturn(result);

        mockMvc.perform(get("/user/{name}", "username"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(100))
                .andExpect(jsonPath("$.login").value("username"));

        verify(userServiceMock, times(1)).findUserByLogin("username");
        verifyNoMoreInteractions(userServiceMock);
    }
}
