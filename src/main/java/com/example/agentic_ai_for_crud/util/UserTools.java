package com.example.agentic_ai_for_crud.util;

import com.example.agentic_ai_for_crud.entity.User;
import com.example.agentic_ai_for_crud.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class UserTools {
    // To log the actions taken by the AI agent
    private static final Logger log = LoggerFactory.getLogger(UserTools.class);
    private final UserService userService;

    public UserTools(UserService userService) {
        this.userService = userService;
    }

    @Tool(description = "Fetch all the users")
    public List<User> getAllUsers() {
        List<User> users = userService.getAllUsers();
        log.info("FETCH: All users. Count = {}", users.size());
        return users;
    }

    @Tool(description = "Fetch the user with given id if exists, else throw an exception.")
    public User getUserById(int id) {
        try {
            User user = userService.getUserById(id);
            log.info("FETCH: User with id {} exists. {}", id, user.toString());
            return user;
        } catch (Exception e) {
            log.warn("FETCH: Failed! User with id {} doesn't exist!", id);
            throw e;
        }
    }

    @Tool(description = """
            Create a new user with all necessary fields.
            Id is auto-generated, and default role is GENERAL.
            Prompt for value of other fields if not found.
        """)
    public User createUser(User user) {
        User newUser = userService.createUser(user);
        log.info("CREATE: User with id {} created! {}", newUser.getId(), newUser.toString());
        return newUser;
    }

    @Tool(description = "Delete the user with given id if exists, else throw an exception")
    public void deleteUserById(int id) {
        try {
            userService.deleteUserById(id);
            log.info("DELETE: User with id {} deleted!", id);
        } catch (Exception e) {
            log.warn("DELETE: Failed! User with id {} doesn't exist!", id);
            throw e;
        }
    }

    @Tool(description = """
            Update the user with given id if exists, else throw an exception.
            Only the fields name, location & balance are allowed to be updated.
            Also throws an exception for violating data integrity.
        """)
    public User updateUserById(int id, Map<String, Object> updates) {
        try {
            User updatedUser = userService.updateUserById(id, updates);
            log.info("UPDATE: User with id {} updated! {}", id, updatedUser.toString());
            return updatedUser;
        } catch (Exception e) {
            log.warn("UPDATE: Failed! User with id {} doesn't exist!", id);
            throw e;
        }
    }

}
