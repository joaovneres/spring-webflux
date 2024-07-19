package br.com.joaovneres.springwebflux.service;

import br.com.joaovneres.springwebflux.entity.User;
import br.com.joaovneres.springwebflux.model.request.UserRequest;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<User> save(UserRequest request);
}
