package com.todo.repository;


import com.todo.model.ToDo;
import com.todo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;
import java.util.Optional;

public interface ToDoRepo extends JpaRepository<ToDo, Long> {
    List<ToDo> findByUser(User user);
    List<ToDo> findByUserAndTitleContainingIgnoreCase(User user, String keyword);
    List<ToDo> findByUserAndCategoryId(User user, Long categoryId);


}