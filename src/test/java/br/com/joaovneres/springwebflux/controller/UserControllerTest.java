package br.com.joaovneres.springwebflux.controller;

import br.com.joaovneres.springwebflux.entity.User;
import br.com.joaovneres.springwebflux.mapper.UserMapper;
import br.com.joaovneres.springwebflux.model.request.UserRequest;
import br.com.joaovneres.springwebflux.service.impl.UserServiceImpl;
import com.mongodb.reactivestreams.client.MongoClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.web.reactive.function.BodyInserters.fromValue;
import static reactor.core.publisher.Mono.just;
import static org.springframework.http.MediaType.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureWebTestClient
class UserControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private UserServiceImpl userService;

    @MockBean
    private UserMapper userMapper;

    @MockBean
    private MongoClient mongoClient;

    @Test
    @DisplayName("Test endpoint save with success")
    void testSaveWithSuccess() {
        final var userRequest = new UserRequest("João V.", "joao@gmail.com", "123456");

        when(userService.save(any(UserRequest.class))).thenReturn(just(User.builder().build()));

        webTestClient.post()
                .uri("/users")
                .contentType(APPLICATION_JSON)
                .body(fromValue(userRequest))
                .exchange()
                .expectStatus().isCreated();

        verify(userService).save(any(UserRequest.class));
    }

    @Test
    @DisplayName("Test endpoint save with bad request")
    void testSaveWithBadRequest() {
        final var userRequest = new UserRequest(" João V.", "joao@gmail.com", "123456");

        webTestClient.post()
                .uri("/users")
                .contentType(APPLICATION_JSON)
                .body(fromValue(userRequest))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.path").isEqualTo("/users")
                .jsonPath("$.status").isEqualTo(BAD_REQUEST.value())
                .jsonPath("$.error").isEqualTo("Validation error")
                .jsonPath("$.message").isEqualTo("Error on validation attributes")
                .jsonPath("$.errors[0].fieldName").isEqualTo("name")
                .jsonPath("$.errors[0].message").isEqualTo("Field cannot have blank spaces at the beginning or end");
    }

    @Test
    void findById() {
    }

    @Test
    void findAll() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}