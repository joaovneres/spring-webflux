package br.com.joaovneres.springwebflux.controller.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ValidationError extends StandardError implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private List<FieldError> errors = new ArrayList<>();

    public void addError(String fieldName, String Message) {
        this.errors.add(new FieldError(fieldName, Message));
    }

    ValidationError(LocalDateTime timestamp, String path, Integer status, String error, String message) {
        super(timestamp, path, status, error, message);
    }

    @Getter
    @AllArgsConstructor
    private static final class FieldError {
        private String fieldName;
        private String message;
    }

}
