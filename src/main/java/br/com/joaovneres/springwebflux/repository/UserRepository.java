package br.com.joaovneres.springwebflux.repository;

import br.com.joaovneres.springwebflux.entity.User;
import reactor.core.publisher.Mono;

public interface UserRepository {
    Mono<User> save(final User user);
}
