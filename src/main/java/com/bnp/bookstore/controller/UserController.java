package com.bnp.bookstore.controller;

import com.bnp.bookstore.entity.User;
import com.bnp.bookstore.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user) {
        User addedUser = userService.addUser(user);
        return ResponseEntity.ok(addedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<Void> getUserByIdAndPwd(@RequestBody User user) {
        User foundUser = userService.getUserByEmail(user.getEmail());
        if (foundUser != null && userService.checkPassword(foundUser, user.getPassword())) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user != null) {
             userService.deleteUser(user);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}