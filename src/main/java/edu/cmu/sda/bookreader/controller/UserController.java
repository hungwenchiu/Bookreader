package edu.cmu.sda.bookreader.controller;

import edu.cmu.sda.bookreader.entity.User;
import edu.cmu.sda.bookreader.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Scope(value = "session")
@Component(value = "userController")
@Slf4j
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
        Long userId = service.authenticateUser(user);
        map.put("userId", String.valueOf(userId));
        map.put("loginStatus", null == userId ? "false" : "true");
        return map;
    }

    @GetMapping("/user/{name}")
    public ResponseEntity<User> findUserByName(@PathVariable String name) {
        User user = service.getUserByName(name);
        if (null == user) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(user);
    }


}
