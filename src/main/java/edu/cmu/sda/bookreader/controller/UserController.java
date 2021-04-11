package edu.cmu.sda.bookreader.controller;

import edu.cmu.sda.bookreader.entity.User;
import edu.cmu.sda.bookreader.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
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

    @PostMapping("/login")
    public Map<String, String> authenticateUser(@RequestBody User user) {
        Map<String, String> map = new HashMap<>();
        map.put("loginStatus", String.valueOf(service.authenticateUser(user)));
        return map;
    }

    @GetMapping("/user/{name}")
    public User findUserByName(@PathVariable String name) {
        return service.getUserByName(name);
    }


}
