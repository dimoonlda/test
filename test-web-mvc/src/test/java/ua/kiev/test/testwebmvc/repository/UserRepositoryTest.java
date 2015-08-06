package ua.kiev.test.testwebmvc.repository;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import ua.kiev.test.testwebmvc.config.TestContext;
import ua.kiev.test.testwebmvc.model.User;

/**
 * Тест который тестирует выборку репозиторием из БД.
 * Created by lutay.d on 06.08.2015.
 */
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContext.class})
@DatabaseSetup(UserRepositoryTest.DATASET)
@DatabaseTearDown(type = DatabaseOperation.DELETE_ALL, value = { UserRepositoryTest.DATASET })
@DirtiesContext
public class UserRepositoryTest {

    protected static final String DATASET = "classpath:datasets/users-test.xml";

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindByLogin(){
        User user = userRepository.findByLogin("user1");
        assertNotNull(user);
        assertEquals("ADMIN", user.getRole());
    }
}
