package com.example.ToDoApp.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PaginatedResponse<T> {

    private List<T> data;
    private int page;
    private int limit;
    private long total;
}
