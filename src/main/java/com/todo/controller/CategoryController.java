package com.todo.controller;

import com.todo.model.Category;
import com.todo.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @PostMapping("/add")
    public String addCategory(@RequestParam String name) {
        Category c = new Category();
        c.setName(name);
        service.save(c);
        return "redirect:/addToDoItem";
    }
}
