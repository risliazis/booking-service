package com.edts.booking.exception;

import com.edts.booking.model.dto.response.DefaultResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public DefaultResponse handleNotFound(NotFoundException ex){
        return new DefaultResponse(ex.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public DefaultResponse handleBadRequest(BadRequestException ex){
        return new DefaultResponse(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public DefaultResponse handleValidationException(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .toList();
        return new DefaultResponse(errors.get(0));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public DefaultResponse handleBookingException(BookingException ex){
        return new DefaultResponse(ex.getMessage());
    }



}
