package com.example.pixel_deposit_api.repository;

import com.example.pixel_deposit_api.data.entity.User;
import org.springframework.data.repository.RepositoryDefinition;

import java.util.Optional;

@RepositoryDefinition(domainClass = User.class, idClass = Long.class)
public interface UserRepository {

    Optional<User> findById(Long id);
}
