package com.mabe.todo_app.common;

import java.util.List;

public record ApiResponse<T>(
        List<T> results,
        int total,
        T result,
        int start,
        String message,
        String error
) {
}