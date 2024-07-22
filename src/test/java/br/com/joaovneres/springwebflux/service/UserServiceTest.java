package br.com.joaovneres.springwebflux.service;

import br.com.joaovneres.springwebflux.entity.User;
import br.com.joaovneres.springwebflux.mapper.UserMapper;
import br.com.joaovneres.springwebflux.model.request.UserRequest;
import br.com.joaovneres.springwebflux.repository.impl.UserRepositoryImpl;
import br.com.joaovneres.springwebflux.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepositoryImpl userRepository;

    @Mock
    private UserMapper mapper;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void save() {
        UserRequest request = new UserRequest("João V.", "joao@gmail.com", "123456");
        User entity = User.builder().build();

        when(mapper.toEntity(any(UserRequest.class))).thenReturn(entity);
        when(userRepository.save(any(User.class))).thenReturn(Mono.just(User.builder().build()));

        Mono<User> result = userService.save(request);

        StepVerifier.create(result)
                .expectNextMatches(user -> user.getClass() == User.class)
                .expectComplete()
                .verify();

        Mockito.verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testFindById() {
        when(userRepository.findById(anyString())).thenReturn(Mono.just(User.builder()
                .id("123")
                .build()));

        Mono<User> result = userService.findById("123");

        StepVerifier.create(result)
                .expectNextMatches(user -> user.getClass() == User.class && Objects.equals(user.getId(), "123"))
                .expectComplete()
                .verify();

        Mockito.verify(userRepository, times(1)).findById(anyString());
    }

    @Test
    public void testFindAll(){
        when(userRepository.findAll()).thenReturn(Flux.just(User.builder().build()));

        Flux<User> result = userService.findAll();

        StepVerifier.create(result)
                .expectNextMatches(user -> user.getClass() == User.class)
                .expectComplete()
                .verify();

        Mockito.verify(userRepository, times(1)).findAll();
    }
}