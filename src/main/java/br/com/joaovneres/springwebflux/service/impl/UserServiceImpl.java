package br.com.joaovneres.springwebflux.service.impl;

import br.com.joaovneres.springwebflux.entity.User;
import br.com.joaovneres.springwebflux.mapper.UserMapper;
import br.com.joaovneres.springwebflux.model.request.UserRequest;
import br.com.joaovneres.springwebflux.repository.impl.UserRepositoryImpl;
import br.com.joaovneres.springwebflux.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepositoryImpl userRepository;
    private final UserMapper mapper;

    @Override
    public Mono<User> save(UserRequest request) {
        return userRepository.save(mapper.toEntity(request));
    }
}
