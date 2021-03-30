package edu.cmu.sda.bookreader.controller;

import edu.cmu.sda.bookreader.entity.Book;
import edu.cmu.sda.bookreader.entity.User;
import edu.cmu.sda.bookreader.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
@Scope(value = "session")
@Component(value = "userController")
public class UserController {
    @Qualifier("userService")
    @Autowired
    private UserService service;

    @PostMapping("/user")
    public User addUser(@RequestBody User user) {
        return service.saveUser(user);
    }

    @GetMapping("/user/{name}")
    public User findUserByName(@PathVariable String name) {
        return service.getUserByName(name);
    }


}
