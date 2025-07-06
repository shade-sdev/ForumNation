package com.shade.enterprise.user.infrastructure.repository;

import com.shade.enterprise.user.domain.model.User;
import com.shade.enterprise.user.domain.port.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class JpaUserRepositoryImpl implements UserRepository {

    private final UserJpaEntityRepository repository;

    private final UserJpaMapper mapper;

    @Inject
    public JpaUserRepositoryImpl(UserJpaEntityRepository repository, UserJpaMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Optional<User> findById(UUID id) {
        return repository.findByIdOptional(id)
                         .map(it -> User.builder()
                                        .id(it.getId())
                                        .build());
    }

    @Override
    public User createUser(User user) {
        UserJpaEntity userJpaEntity = mapper.mapToEntity(user);
        repository.persist(userJpaEntity);
        return mapper.mapToDomain(userJpaEntity);
    }
}
