package br.com.joaovneres.springwebflux.repository.impl;

import br.com.joaovneres.springwebflux.entity.User;
import br.com.joaovneres.springwebflux.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final ReactiveMongoTemplate mongoTemplate;

    @Override
    public Mono<User> save(final User user) {
        return mongoTemplate.save(user);
    }

    @Override
    public Mono<User> findById(String id) {
        return mongoTemplate.findById(id, User.class);
    }

    @Override
    public Flux<User> findAll() {
        return mongoTemplate.findAll(User.class);
    }

    @Override
    public Mono<User> findAndRemove(String id) {
        Query query = new Query();
        Criteria where = Criteria.where("id").is(id);
        return mongoTemplate.findAndRemove(query.addCriteria(where), User.class);
    }
}
