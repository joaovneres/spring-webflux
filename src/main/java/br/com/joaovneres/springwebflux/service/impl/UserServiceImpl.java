package br.com.joaovneres.springwebflux.service.impl;

import br.com.joaovneres.springwebflux.entity.User;
import br.com.joaovneres.springwebflux.mapper.UserMapper;
import br.com.joaovneres.springwebflux.model.request.UserRequest;
import br.com.joaovneres.springwebflux.repository.impl.UserRepositoryImpl;
import br.com.joaovneres.springwebflux.service.UserService;
import br.com.joaovneres.springwebflux.service.exception.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepositoryImpl userRepository;
    private final UserMapper mapper;

    @Override
    public Mono<User> save(UserRequest request) {
        return userRepository.save(mapper.toEntity(request));
    }

    @Override
    public Mono<User> findById(String id) {
        return handleNotFound(userRepository.findById(id), id, User.class);
    }

    @Override
    public Flux<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Mono<User> update(String id, UserRequest request) {
        return findById(id)
                .map(entity -> mapper.toEntity(request, entity))
                .flatMap(userRepository::save);
    }

    @Override
    public Mono<User> delete(String id) {
        return handleNotFound(userRepository.findAndRemove(id), id, User.class);
    }

    private <T> Mono<T> handleNotFound(Mono<T> mono, String id, Class<T> clazz) {
        return mono.switchIfEmpty(Mono.error(
                new ObjectNotFoundException(
                        format("Object not found. Id: %s, type: %s", id, clazz.getSimpleName()))));
    }
}
