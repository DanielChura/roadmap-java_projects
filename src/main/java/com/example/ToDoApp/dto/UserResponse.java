package com.example.ToDoApp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    Long id;
    String name;
    String email;
    String password;

    List<TaskResponse> tasks;
}
