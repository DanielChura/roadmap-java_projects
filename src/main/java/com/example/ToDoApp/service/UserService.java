package com.example.ToDoApp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.ToDoApp.dto.TaskResponse;
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
        return userRepository.findAll().stream().map(t -> toUserResponse(t)).toList();
    }

    public UserResponse findUserById(Long id) {
        return toUserResponse(
                userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found with id: " + id)));
    }

    public UserResponse toUserResponse(User user) {
        List<TaskResponse> tasks = user.getTasks().stream().map(t -> toTaskResponse(t)).toList();
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setName(user.getName());
        userResponse.setEmail(user.getEmail());
        userResponse.setPassword(user.getPassword());
        userResponse.setTasks(tasks);
        return userResponse;
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
