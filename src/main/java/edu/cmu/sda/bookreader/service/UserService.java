package edu.cmu.sda.bookreader.service;

import edu.cmu.sda.bookreader.entity.User;
import edu.cmu.sda.bookreader.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Scope(value = "session")
@Component(value = "userService")
public class UserService {

    @Autowired
    private UserRepository repository;
    public User saveUser(User user) {
        return repository.save(user);
    }
    public User getUserByName(String name) {
        return repository.findById(name).orElse(null);
    }

}
