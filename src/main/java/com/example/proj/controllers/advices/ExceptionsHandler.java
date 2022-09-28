package com.example.proj.controllers.advices;

import com.example.proj.exceptions.CategoryNotFoundException;
import com.example.proj.exceptions.ImageNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionsHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({IllegalStateException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    public String illegalStateExceptionHandler(IllegalStateException e) {
        return e.getMessage();
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String categoryNotFoundExceptionHandler(CategoryNotFoundException e) {
        return e.getMessage();
    }

    @ExceptionHandler(ImageNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String imageNotFoundExceptionHandler(ImageNotFoundException e) {
        return e.getMessage();
    }
}
