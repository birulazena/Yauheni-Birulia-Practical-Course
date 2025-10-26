package com.github.birulazena.user_service.containers;

import com.github.birulazena.user_service.dto.UserRequestDto;
import com.github.birulazena.user_service.dto.UserResponseDto;
import com.github.birulazena.user_service.entities.User;
import com.github.birulazena.user_service.repository.UserRepositoryJpa;
import com.github.birulazena.user_service.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions. *;


@SpringBootTest
@EnableCaching
@Testcontainers
public class UserServiceTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepositoryJpa userRepositoryJpa;

    @Autowired
    private CacheManager cacheManager;

    @Test
    public void createUserTest() {
        UserRequestDto userRequestDto = new UserRequestDto(
                "Zenya",
                "Birulia",
                LocalDate.now(),
                "birulya.zhenka@gmail.com",
                new ArrayList<>()
        );

        UserResponseDto result = userService.createUser(userRequestDto);

        Optional<User> dbUser = userRepositoryJpa.findById(result.id());
        assertTrue(dbUser.isPresent());
        assertEquals("birulya.zhenka@gmail.com", dbUser.get().getEmail());

        Cache userCache = cacheManager.getCache("user_cache");
        Cache emailCache = cacheManager.getCache("user_email_cache");

        assertNotNull(userCache.get(result.id(), UserResponseDto.class));
        assertNotNull(emailCache.get(result.email(), UserResponseDto.class));

        assertEquals(result, userCache.get(result.id(), UserResponseDto.class));
        assertEquals(result, emailCache.get(result.email(), UserResponseDto.class));
    }

    @Test
    public void getUserByIdTest() {
        User user = new User(null,
                "Zenya",
                "Birulia",
                LocalDate.now(),
                "birulya.zhenka@gmail.com",
                new ArrayList<>()
        );

        user = userRepositoryJpa.save(user);

        UserResponseDto userResponseDto1 = userService.getUserById(user.getId());

        Cache userCache = cacheManager.getCache("user_cache");
        assertEquals(userResponseDto1, userCache.get(user.getId(), UserResponseDto.class));

        UserResponseDto userResponseDto2 = userService.getUserById(user.getId());
//        assertEquals(userResponseDto1, userResponseDto2);

    }


}
