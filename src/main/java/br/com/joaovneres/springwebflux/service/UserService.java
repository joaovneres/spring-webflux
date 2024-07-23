package br.com.joaovneres.springwebflux.service;

import br.com.joaovneres.springwebflux.entity.User;
import br.com.joaovneres.springwebflux.model.request.UserRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<User> save(UserRequest request);

    Mono<User> findById(String id);

    Flux<User> findAll();

    Mono<User> update(final String id, final UserRequest request);

    Mono<User> delete(final String id);
}
