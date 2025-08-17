package com.harshit.vibelink.controller;

import com.harshit.vibelink.entity.User;
import com.harshit.vibelink.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User req) {
        String username = req.getUsername();
        if (username == null || username.isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        return userRepository.findByUsername(username)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    User u = new User();
                    u.setUsername(username);
                    u.setCreatedAt(LocalDateTime.now());
                    return ResponseEntity.ok(userRepository.save(u));
                });
    }

    @GetMapping
    public List<User> all() {
        return userRepository.findAll();
    }
}
