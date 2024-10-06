package com.imperion.learn.LMS.ExceptionAdvices;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiError {
    private HttpStatus status;
    private String message;
    private List<String> subErrors;
    public ApiError(String message, HttpStatus status) {
        this();
        this.message = message;
        this.status = status;

    }
}
