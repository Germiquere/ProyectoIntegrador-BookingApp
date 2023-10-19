package com.dh.bmn;

import com.dh.bmn.service.UsuarioService;
import jakarta.persistence.EntityNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LogManager.getLogger(GlobalExceptionHandler.class);


   /* @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleAllExceptions(Exception ex, WebRequest req){
        LOGGER.error(ex.getMessage());
        return new ResponseEntity<>("Error " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }*/

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest req) {
        LOGGER.error(ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

}
