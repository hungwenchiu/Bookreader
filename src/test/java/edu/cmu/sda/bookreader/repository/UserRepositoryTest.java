package edu.cmu.sda.bookreader.repository;

import edu.cmu.sda.bookreader.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;

    @Test
    public void canCreateUser() {
        final String username = "neal";
        final String password = "test";
        User user = new User();
        user.setName(username);
        user.setPassword(password);
        User savedUser = userRepository.save(user);
        User dbUser = userRepository.findById(savedUser.getId()).orElse(null);
        assertNotNull(dbUser);
        assertEquals(username, dbUser.getName());
        assertEquals(password, dbUser.getPassword());
    }
}
