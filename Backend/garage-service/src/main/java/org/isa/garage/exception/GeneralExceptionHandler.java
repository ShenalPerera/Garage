package org.isa.garage.exception;

import io.jsonwebtoken.JwtException;
import org.isa.garage.dto.UserErrorResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.management.JMException;
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
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<?> handleEmailNotFoundException(AuthenticationException exception){
        UserErrorResponseDTO errorResponseDTO  = new UserErrorResponseDTO(
                HttpStatus.UNAUTHORIZED.value(),
                "You are not authorized",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(errorResponseDTO,HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(GarageServiceNotFoundException.class)
    public ResponseEntity<?> handleGarageServiceNotFoundException(GarageServiceNotFoundException exception){
        UserErrorResponseDTO error = new UserErrorResponseDTO(
                HttpStatus.NOT_FOUND.value(),
                exception.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidScheduleTimesException.class)
    public ResponseEntity<?> handleInvalidScheduleTimesException(InvalidScheduleTimesException exception){
        UserErrorResponseDTO error = new UserErrorResponseDTO(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                exception.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(InvalidScheduleException.class)
    public ResponseEntity<?> handleInvalidScheduleException(InvalidScheduleException exception){
        UserErrorResponseDTO error = new UserErrorResponseDTO(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                exception.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(BookingException.class)
    public ResponseEntity<?> handleBookingException(BookingException exception){
        UserErrorResponseDTO error = new UserErrorResponseDTO(
                HttpStatus.NOT_FOUND.value(),
                exception.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(JMException.class)
    public ResponseEntity<?> handleJWTException(JwtException jwtException){
        UserErrorResponseDTO error = new UserErrorResponseDTO(
                HttpStatus.UNAUTHORIZED.value(),
                jwtException.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(error,HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(GarageJWTException.class)
    public ResponseEntity<?> handleJWTException(GarageJWTException garageJwtException){
        UserErrorResponseDTO error = new UserErrorResponseDTO(
                HttpStatus.UNAUTHORIZED.value(),
                garageJwtException.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(error,HttpStatus.UNAUTHORIZED);

    }

    @ExceptionHandler(GarageServiceException.class)
    public ResponseEntity<?> handleGarageServiceException(GarageServiceException garageServiceException){
        UserErrorResponseDTO error = new UserErrorResponseDTO(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                garageServiceException.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(error,HttpStatus.UNPROCESSABLE_ENTITY);
    }

}
