package org.userservice.controller;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.userservice.UserDto;
import org.userservice.entity.User;
import org.userservice.repository.UserRepository;
import org.userservice.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("")
    public List<User> getAll() {
        return userService.findAllUsers();
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Integer id) {

        User user = userService.findUserById(id);
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEnabled(user.isEnabled());
        dto.setEmail(user.getEmail());
        return dto;

    }

    @PostMapping("/save")
    public User save(@RequestBody User user) {
        return userService.createUser(user);
    }

    @GetMapping("/findById/{id}")
    public User findById(@PathVariable Integer id) {
        return userService.findUserById(id);
    }

    @GetMapping("/findByEmail/{email}")
    public User findByEmail(@PathVariable String email) {
        return userService.findUserByEmail(email);
    }

    @PostMapping("/delete/{id}")
    public void deleteUser(@PathVariable Integer id) {
        userService.deleteUserById(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable Integer id) {
        User updatedUser = userService.updateUser(user, id);
        return ResponseEntity.ok(updatedUser);
    }

}
