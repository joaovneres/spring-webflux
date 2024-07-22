package br.com.joaovneres.springwebflux.repository;

import br.com.joaovneres.springwebflux.entity.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepository {
    Mono<User> save(final User user);

    Mono<User> findById(final String id);

    Flux<User> findAll();
}
