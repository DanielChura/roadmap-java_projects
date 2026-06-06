package com.example.ToDoApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ToDoApp.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUser_Id(Long id);
}