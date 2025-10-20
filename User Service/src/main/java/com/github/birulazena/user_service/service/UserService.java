package com.github.birulazena.user_service.service;

import com.github.birulazena.user_service.entities.User;
import com.github.birulazena.user_service.exception.UserNotFoundException;
import com.github.birulazena.user_service.repository.CardInfoRepository;
import com.github.birulazena.user_service.repository.UserRepositoryJpa;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class UserService {

    private CardInfoRepository cardInfoRepository;
    private UserRepositoryJpa userRepositoryJpa;

    public User createUser(User user) {
        return userRepositoryJpa.save(user);
    }

    public User getUserById(Long id) {
        return userRepositoryJpa.findById(id).orElseThrow(() -> new UserNotFoundException("User not found ID: " + id));
    }

    public Page<User> findAll(Pageable pageable) {
        return userRepositoryJpa.findAll(pageable);
    }

    public User getUserByEmail(String email) {
        userRepositoryJpa.
    }


}
