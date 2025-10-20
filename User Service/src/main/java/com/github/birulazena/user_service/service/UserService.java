package com.github.birulazena.user_service.service;

import com.github.birulazena.user_service.repository.CardInfoRepository;
import com.github.birulazena.user_service.repository.UserRepositoryJpa;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private CardInfoRepository cardInfoRepository;
    private UserRepositoryJpa userRepositoryJpa;





}
