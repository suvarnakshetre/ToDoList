package com.todo.service;


import com.todo.model.ToDo;
import com.todo.model.User;
import com.todo.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepo repo;


    public boolean registerUser(User user) {
        if (repo.findByUsername(user.getUsername()) != null) {
            return false;
        }
        repo.save(user);
        return true;
    }

    public User loginUser(String username, String password) {
        User user = repo.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }


}