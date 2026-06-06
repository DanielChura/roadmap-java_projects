package com.example.ToDoApp.service;

import java.util.List;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.ToDoApp.dto.CreateTask;
import com.example.ToDoApp.dto.TaskResponse;
import com.example.ToDoApp.entity.Task;
import com.example.ToDoApp.entity.User;
import com.example.ToDoApp.repository.TaskRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public List<TaskResponse> findAllTasks() {
        return taskRepository.findAll().stream().map(t -> toTaskResponse(t)).toList();
    };

    public TaskResponse findTaskById(Long id) {
        return toTaskResponse(taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id)));
    }

    public @Nullable TaskResponse createTask(CreateTask request) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setUser(user);

        return toTaskResponse(taskRepository.save(task));
    }

    public @Nullable TaskResponse updateTask(Long id, CreateTask request) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setUser(user);
        return toTaskResponse(taskRepository.save(task));
    }

    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
        taskRepository.delete(task);
    }

    private TaskResponse toTaskResponse(Task task) {
        TaskResponse taskResponse = new TaskResponse();
        taskResponse.setId(task.getId());
        taskResponse.setTitle(task.getTitle());
        taskResponse.setDescription(task.getDescription());
        taskResponse.setUserId(task.getUser().getId());
        return taskResponse;
    }
}
