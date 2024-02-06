package org.isa.garage.exception;

import org.isa.garage.controller.GarageRestController;
import org.isa.garage.dto.UserErrorResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GeneralExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GeneralExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex){
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);

            logger.error("Bad request {} - {}",fieldName,errorMessage);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<?> handleUserAlreadyExitsException(UserAlreadyExistException ex){
        UserErrorResponseDTO userErrorResponseDTO = new UserErrorResponseDTO(
                HttpStatus.CONFLICT.value(),
                "Email address is already registered",
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(userErrorResponseDTO,HttpStatus.CONFLICT);
    }
}
