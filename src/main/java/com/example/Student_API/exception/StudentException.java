package com.example.Student_API.exception;

public class StudentException extends Throwable{
    public StudentException(String message) {
        super(message);
    }

    public StudentException(String message, Throwable cause) {
        super(message, cause);
    }
}
