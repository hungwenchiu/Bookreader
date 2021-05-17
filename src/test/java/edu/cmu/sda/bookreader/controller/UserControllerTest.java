package edu.cmu.sda.bookreader.controller;

import edu.cmu.sda.bookreader.BookreaderApplication;
import edu.cmu.sda.bookreader.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookreaderApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "classpath:clear_all.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class UserControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String getRootUrl() {
        return "http://localhost:" + port;
    }

    // a simple sanity check test that will fail if the application context cannot start
    @Test
    public void contextLoads() {
    }

    @Test
    public void testAddAndGetUserByName() {
        User user = new User();
        user.setName("user");
        user.setPassword("password");
        restTemplate.postForEntity(getRootUrl() + "/api/user", user, User.class);
        User responseUser = this.restTemplate.getForObject(getRootUrl() + "/api/user/name/user", User.class);
        assertEquals("user", responseUser.getName());
        User responseUserNull = this.restTemplate.getForObject(getRootUrl() + "/api/user/name/null", User.class);
        assertEquals(null, responseUserNull);
    }

    @Test
    public void testAddAndGetUserById() {
        User user = new User();
        user.setName("user");
        user.setPassword("password");
        restTemplate.postForEntity(getRootUrl() + "/api/user", user, User.class);
        User responseUser = this.restTemplate.getForObject(getRootUrl() + "/api/user/name/user", User.class);
        User responseUser2 = this.restTemplate.getForObject(getRootUrl() + "/api/user/" + responseUser.getId(), User.class);
        assertEquals("user", responseUser2.getName());
        User responseUserNull = this.restTemplate.getForObject(getRootUrl() + "/api/user/111", User.class);
        assertEquals(null, responseUserNull);
    }

    @Test
    public void testLogInUser() {
        User user = new User();
        user.setName("user");
        user.setPassword("password");
        restTemplate.postForEntity(getRootUrl() + "/api/user", user, User.class);

        Map map = this.restTemplate.postForObject(getRootUrl() + "/api/login", user, Map.class);
        assertEquals("true", map.get("loginStatus"));
    }
}
