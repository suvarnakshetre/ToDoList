package com.todo.controller;

import com.todo.model.User;
import com.todo.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private UserService service;

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/loginUser")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        Model model,
                        HttpSession session) {

        User user = service.loginUser(username, password);

        if (user != null) {
            session.setAttribute("loggedInUser", user); // ✅ IMPORTANT
            return "redirect:/viewToDoList";
        }

        model.addAttribute("error", "Invalid credentials");
        return "login";
    }

    @GetMapping("/signup")
    public String signupPage(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user, Model model) {
        if (service.registerUser(user)) {
            return "redirect:/login";
        }
        model.addAttribute("error", "Username exists");
        return "signup";
    }

}
