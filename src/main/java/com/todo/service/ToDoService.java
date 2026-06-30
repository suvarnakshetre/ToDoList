package com.todo.service;

import com.todo.model.Category;
import com.todo.model.ToDo;
import com.todo.model.User;
import com.todo.repository.CategoryRepo;
import com.todo.repository.ToDoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.List;

@Service
public class ToDoService {

    @Autowired
    private ToDoRepo repo;

    @Autowired
    private CategoryRepo categoryRepo;


    public List<ToDo> getAllToDoItems() {
        List<ToDo> list = repo.findAll();
        LocalDate today = LocalDate.now();

        for (ToDo todo : list) {
            if (todo.getDate() != null) {
                LocalDate taskDate = todo.getDate().toLocalDate();

                if (taskDate.equals(today.plusDays(1))
                        && "Incomplete".equalsIgnoreCase(todo.getStatus())) {
                    todo.setDueTomorrow(true);
                } else {
                    todo.setDueTomorrow(false);
                }
            }
        }
        return list;
    }

    public List<ToDo> getToDosByUser(User user) {
        return repo.findByUser(user);
    }

    public List<ToDo> searchTasks(String keyword, User user) {
        return repo.findByUserAndTitleContainingIgnoreCase(user, keyword);
    }

  public String saveOrUpdate(ToDo todo) {

        if (todo.getCategoryName() != null && !todo.getCategoryName().trim().isEmpty()) {
            Category category = categoryRepo.findAll()
                                            .stream()
                                            .filter(c -> c.getName().equalsIgnoreCase(todo.getCategoryName()))
                                            .findFirst()
                                            .orElse(null);

            if (category == null) {
                category = new Category();
                category.setName(todo.getCategoryName());
                category = categoryRepo.save(category);
            }

            todo.setCategory(category);
        }
        boolean isNew = (todo.getId() == null);
        repo.save(todo);
        return isNew ? "Save Success" : "Update Success";
    }


    public String delete(Long id) {
        repo.deleteById(id);
        return "Delete Success";
    }

    public String complete(Long id) {
        ToDo todo = repo.findById(id).orElse(null);
        if (todo != null) {
            todo.setStatus("Completed");
            repo.save(todo);
            return "Update Success";
        }
        return "Update Failure";
    }

    public ToDo getById(Long id) {
        return repo.findById(id).orElse(null);
    }

    public List<ToDo> getByCategory(User user, Long categoryId) {
        return repo.findByUserAndCategoryId(user, categoryId);
    }
}