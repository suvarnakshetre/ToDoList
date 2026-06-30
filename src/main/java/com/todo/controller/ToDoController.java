package com.todo.controller;

import com.todo.model.ToDo;
import com.todo.model.User;
import com.todo.service.CategoryService;
import com.todo.service.ToDoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ToDoController {

    @Autowired
    private ToDoService service;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/")
    public String home() {
        return "redirect:/login";
    }

    @GetMapping("/viewToDoList")
    public String view(Model model, HttpSession session) {

        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            return "redirect:/login";
        }

        model.addAttribute("list", service.getToDosByUser(user));
        return "ViewToDoList";
    }


    @GetMapping("/addToDoItem")
    public String add(Model model) {
        model.addAttribute("todo", new ToDo());
        model.addAttribute("categories", categoryService.getAll());
        return "AddToDoItem";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute ToDo todo,
                       RedirectAttributes ra,
                       HttpSession session) {

        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            return "redirect:/login";
        }

        todo.setUser(user); //

        ra.addFlashAttribute("message", service.saveOrUpdate(todo));
        return "redirect:/viewToDoList";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("todo", service.getById(id));
        return "EditToDoItem";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute ToDo todo,
                         RedirectAttributes ra,
                         HttpSession session) {

        User user = (User) session.getAttribute("loggedInUser");

        ToDo existing = service.getById(todo.getId());

        if (existing == null || !existing.getUser().getId().equals(user.getId())) {
            return "redirect:/login"; // unauthorized
        }

        todo.setUser(user);

        ra.addFlashAttribute("message", service.saveOrUpdate(todo));
        return "redirect:/viewToDoList";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id,
                         RedirectAttributes ra,
                         HttpSession session) {

        User user = (User) session.getAttribute("loggedInUser");
        ToDo todo = service.getById(id);

        if (todo == null || !todo.getUser().getId().equals(user.getId())) {
            return "redirect:/login";
        }

        ra.addFlashAttribute("message", service.delete(id));
        return "redirect:/viewToDoList";
    }

    @GetMapping("/complete/{id}")
    public String complete(@PathVariable Long id, RedirectAttributes ra) {
        ra.addFlashAttribute("message", service.complete(id));
        return "redirect:/viewToDoList";
    }

    @GetMapping("/search")
    public String searchTasks(@RequestParam("keyword") String keyword,
                              Model model,
                              HttpSession session) {

        User user = (User) session.getAttribute("loggedInUser");

        List<ToDo> searchResults = service.searchTasks(keyword, user);

        model.addAttribute("list", searchResults);
        model.addAttribute("keyword", keyword);

        return "ViewToDoList";
    }

    @GetMapping("/filterByCategory")
    public String filterByCategory(@RequestParam(required = false) Long categoryId,
                                   Model model,
                                   HttpSession session) {

        User user = (User) session.getAttribute("loggedInUser");

        List<ToDo> list;

        if (categoryId == null) {
            list = service.getToDosByUser(user);
        } else {
            list = service.getByCategory(user, categoryId);
        }

        model.addAttribute("list", list);
        model.addAttribute("categories", categoryService.getAll());

        return "ViewToDoList";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}