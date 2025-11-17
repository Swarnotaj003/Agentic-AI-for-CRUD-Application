package com.example.agentic_ai_for_crud.service;

import com.example.agentic_ai_for_crud.entity.User;
import com.example.agentic_ai_for_crud.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User with id " + id + " doesn't exist!"));
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUserById(int id) {
        userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User with id " + id + " doesn't exist!"));
        userRepository.deleteById(id);
    }

    public User updateUserById(int id, Map<String, Object> updates) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User with id " + id + " doesn't exist!"));
        try {
            if (updates.containsKey("name")) {
                user.setName((String) updates.get("name"));
            }
            if (updates.containsKey("location")) {
                user.setLocation((String) updates.get("location"));
            }
            if (updates.containsKey("balance")) {
                user.setBalance(((Number) updates.get("balance")).doubleValue());
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
        return userRepository.save(user);
    }

}
