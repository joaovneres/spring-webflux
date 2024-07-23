package br.com.joaovneres.springwebflux.model.request;

import br.com.joaovneres.springwebflux.validator.TrimString;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequest(

        @Size(min = 3, max = 50, message = "must be between 3 and 50 characters")
        @NotBlank(message = "must not be null or  empty")
        @TrimString
        String name,

        @Email(message = "invalid email")
        @NotBlank(message = "must not be null or empty")
        @TrimString
        String email,

        @Size(min = 6, max = 20, message = "must be between 6 and 20 characters")
        @NotBlank(message = "must not be null or  empty")
        @TrimString
        String password) {
}
