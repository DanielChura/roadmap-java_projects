package com.example.ToDoApp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.ToDoApp.dto.CreateTodoRequest;
import com.example.ToDoApp.dto.PaginatedResponse;
import com.example.ToDoApp.dto.TodoResponse;
import com.example.ToDoApp.entity.Task;
import com.example.ToDoApp.entity.User;
import com.example.ToDoApp.repository.TaskRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public PaginatedResponse<TodoResponse> findAllTasks(String title, String description, Pageable pageable) {
        Page<Task> page = taskRepository.findByFilters(title, description, pageable);
        return new PaginatedResponse<>(
                page.getContent().stream().map(this::toTodoResponse).toList(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements());
    }

    public TodoResponse findTaskById(Long id) {
        return toTodoResponse(taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id)));
    }

    public TodoResponse createTask(CreateTodoRequest request) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setUser(user);

        return toTodoResponse(taskRepository.save(task));
    }

    public TodoResponse updateTask(Long id, CreateTodoRequest request) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));

        if (!task.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("No tienes permiso para modificar esta tarea");
        }

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());

        return toTodoResponse(taskRepository.save(task));
    }

    public void deleteTask(Long id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));

        if (!task.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("No tienes permiso para eliminar esta tarea");
        }

        taskRepository.delete(task);
    }

    private TodoResponse toTodoResponse(Task task) {
        TodoResponse response = new TodoResponse();
        response.setId(task.getId());
        response.setTitle(task.getTitle());
        response.setDescription(task.getDescription());
        response.setUserId(task.getUser().getId());
        return response;
    }
}
