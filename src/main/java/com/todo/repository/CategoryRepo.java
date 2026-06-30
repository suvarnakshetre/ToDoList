package com.todo.repository;

import com.todo.model.Category;
import com.todo.model.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;

public interface CategoryRepo extends JpaRepository<Category, Long> {

}