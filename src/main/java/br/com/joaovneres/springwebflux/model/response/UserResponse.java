package br.com.joaovneres.springwebflux.model.response;

public record UserResponse(
        String id,
        String name,
        String email
) {
}
