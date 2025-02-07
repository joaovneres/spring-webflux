package br.com.joaovneres.springwebflux.controller;

import br.com.joaovneres.springwebflux.entity.User;
import br.com.joaovneres.springwebflux.mapper.UserMapper;
import br.com.joaovneres.springwebflux.model.request.UserRequest;
import br.com.joaovneres.springwebflux.model.response.UserResponse;
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
import reactor.core.publisher.Flux;

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

    public static final String NAME = "João V.";
    public static final String EMAIL = "joao@gmail.com";
    public static final String PASSWORD = "123456";
    public static final String ID = "123";
    public static final String BASE_URI = "users";

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
        final var userRequest = new UserRequest(NAME, EMAIL, PASSWORD);

        when(userService.save(any(UserRequest.class))).thenReturn(just(User.builder().build()));

        webTestClient.post()
                .uri("/" + BASE_URI)
                .contentType(APPLICATION_JSON)
                .body(fromValue(userRequest))
                .exchange()
                .expectStatus().isCreated();

        verify(userService).save(any(UserRequest.class));
    }

    @Test
    @DisplayName("Test endpoint save with bad request")
    void testSaveWithBadRequest() {
        final var userRequest = new UserRequest(NAME.concat(" "), EMAIL, PASSWORD);

        webTestClient.post()
                .uri("/" + BASE_URI)
                .contentType(APPLICATION_JSON)
                .body(fromValue(userRequest))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.path").isEqualTo("/" + BASE_URI)
                .jsonPath("$.status").isEqualTo(BAD_REQUEST.value())
                .jsonPath("$.error").isEqualTo("Validation error")
                .jsonPath("$.message").isEqualTo("Error on validation attributes")
                .jsonPath("$.errors[0].fieldName").isEqualTo("name")
                .jsonPath("$.errors[0].message").isEqualTo("Field cannot have blank spaces at the beginning or end");
    }

    @Test
    @DisplayName("Test find by id endpoint with success")
    void testFindByIdWithSuccess() {

        final var userResponse = new UserResponse(ID, NAME, EMAIL);

        when(userService.findById(anyString())).thenReturn(just(User.builder().build()));
        when(userMapper.toResponse(any(User.class))).thenReturn(userResponse);

        webTestClient.get()
                .uri("/" + BASE_URI + "/" + ID)
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(ID)
                .jsonPath("$.name").isEqualTo(NAME)
                .jsonPath("$.email").isEqualTo(EMAIL);

        verify(userService).findById(anyString());
        verify(userMapper).toResponse(any(User.class));
    }

    @Test
    @DisplayName("Test find all with success")
    void testFindAllWithSuccess() {
        final var userResponse = new UserResponse(ID, NAME, EMAIL);

        when(userService.findAll()).thenReturn(Flux.just(User.builder().build()));
        when(userMapper.toResponse(any(User.class))).thenReturn(userResponse);

        webTestClient.get()
                .uri("/" + BASE_URI)
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.[0].id").isEqualTo(ID)
                .jsonPath("$.[0].name").isEqualTo(NAME)
                .jsonPath("$.[0].email").isEqualTo(EMAIL);


        verify(userService).findAll();
        verify(userMapper).toResponse(any(User.class));
    }

    @Test
    @DisplayName("Test update endpoint with success")
    void testUpdateWithSuccess() {
        final var userRequest = new UserRequest(NAME, EMAIL, PASSWORD);
        final var userResponse = new UserResponse(ID, NAME, EMAIL);

        when(userService.update(anyString(), any(UserRequest.class))).thenReturn(just(User.builder().build()));
        when(userMapper.toResponse(any(User.class))).thenReturn(userResponse);

        webTestClient.patch()
                .uri("/" + BASE_URI + "/" + ID)
                .contentType(APPLICATION_JSON)
                .body(fromValue(userRequest))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(ID)
                .jsonPath("$.name").isEqualTo(NAME)
                .jsonPath("$.email").isEqualTo(EMAIL);

        verify(userService).update(anyString(), any(UserRequest.class));
        verify(userMapper).toResponse(any(User.class));
    }

    @Test
    @DisplayName("Test delete endpoint with success")
    void testDeleteWithSuccess() {

        when(userService.delete(ID)).thenReturn(just(User.builder().build()));

        webTestClient.delete()
                .uri("/" + BASE_URI + "/" + ID)
                .exchange()
                .expectStatus().isOk();

        verify(userService).delete(ID);
    }
}