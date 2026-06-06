package com.example.ToDoApp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.ToDoApp.dto.TodoResponse;
import com.example.ToDoApp.dto.UserResponse;
import com.example.ToDoApp.entity.Task;
import com.example.ToDoApp.entity.User;
import com.example.ToDoApp.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<UserResponse> findAllUsers() {
        return userRepository.findAll().stream().map(this::toUserResponse).toList();
    }

    public UserResponse findUserById(Long id) {
        return toUserResponse(userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id)));
    }

    private UserResponse toUserResponse(User user) {
        List<TodoResponse> tasks = user.getTasks().stream().map(this::toTodoResponse).toList();

        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setTasks(tasks);
        return response;
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
