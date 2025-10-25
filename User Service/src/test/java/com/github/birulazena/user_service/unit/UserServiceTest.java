package com.github.birulazena.user_service.unit;

import com.github.birulazena.user_service.dto.UserRequestDto;
import com.github.birulazena.user_service.dto.UserResponseDto;
import com.github.birulazena.user_service.entities.User;
import com.github.birulazena.user_service.exception.DuplicateEmailException;
import com.github.birulazena.user_service.exception.UserNotFoundException;
import com.github.birulazena.user_service.mapper.CardInfoMapper;
import com.github.birulazena.user_service.mapper.UserMapper;
import com.github.birulazena.user_service.repository.CardInfoRepository;
import com.github.birulazena.user_service.repository.UserRepositoryJpa;
import com.github.birulazena.user_service.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions. *;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepositoryJpa userRepositoryJpa;

    @Mock
    private CardInfoRepository cardInfoRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private CardInfoMapper cardInfoMapper;

    @InjectMocks
    private UserService userService;

    @Test
    public void createUserEmailExceptionTest() {
        UserRequestDto userRequestDto = new UserRequestDto("Zenya",
                "Birulia",
                LocalDate.now(),
                "birulya.zhenka@gmail.com",
                new ArrayList<>());

        when(userRepositoryJpa.findByEmail("birulya.zhenka@gmail.com")).thenReturn(Optional.of(new User()));

        assertThrows(DuplicateEmailException.class, () -> userService.createUser(userRequestDto));
    }

    @Test
    public void createUserTest() {
        UserRequestDto userRequestDto = new UserRequestDto("Zenya",
                "Birulia",
                LocalDate.now(),
                "birulya.zhenka@gmail.com",
                new ArrayList<>());

        User user = new User(null,
                "Zenya",
                "Birulia",
                LocalDate.now(),
                "birulya.zhenka@gmail.com",
                new ArrayList<>());

        User saveUser = new User(1L,
                "Zenya",
                "Birulia",
                LocalDate.now(),
                "birulya.zhenka@gmail.com",
                new ArrayList<>());
        UserResponseDto userResponseDto = new UserResponseDto(1L,
                "Zenya",
                "Birulia",
                LocalDate.now(),
                "birulya.zhenka@gmail.com",
                new ArrayList<>());

        when(userRepositoryJpa.findByEmail("birulya.zhenka@gmail.com")).thenReturn(Optional.empty());
        when(userMapper.toEntity(userRequestDto)).thenReturn(user);
        when(userRepositoryJpa.save(user)).thenReturn(saveUser);
        when(userMapper.toDto(saveUser)).thenReturn(userResponseDto);

        UserResponseDto result = userService.createUser(userRequestDto);

        assertEquals(1L, result.id());
        assertEquals("birulya.zhenka@gmail.com", result.email());

    }

    @Test
    public void getUserByIdExceptionTest() {
        when(userRepositoryJpa.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUserById(1L));
    }

    @Test
    public void getUserByIdTest() {
        User user = new User(1L,
                "Zenya",
                "Birulia",
                LocalDate.now(),
                "birulya.zhenka@gmail.com",
                new ArrayList<>());

        UserResponseDto userResponseDto = new UserResponseDto(1L,
                "Zenya",
                "Birulia",
                LocalDate.now(),
                "birulya.zhenka@gmail.com",
                new ArrayList<>());

        when(userRepositoryJpa.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(userResponseDto);

        UserResponseDto result = userService.getUserById(1L);

        assertEquals(1L, result.id());
        assertEquals("birulya.zhenka@gmail.com", result.email());
    }

    @Test
    public void findAllUsersTest() {
        Pageable pageable = PageRequest.of(0, 5);
        User user1 = new User(1L,
                "Zenya1",
                "Birulia",
                LocalDate.now(),
                "birulya.zhenka1@gmail.com",
                new ArrayList<>());
        User user2 = new User(2L,
                "Zenya3",
                "Birulia",
                LocalDate.now(),
                "birulya.zhenka2@gmail.com",
                new ArrayList<>());
        User user3 = new User(3L,
                "Zenya3",
                "Birulia",
                LocalDate.now(),
                "birulya.zhenka3@gmail.com",
                new ArrayList<>());

        UserResponseDto userResponseDto1 = new UserResponseDto(1L,
                "Zenya1",
                "Birulia",
                LocalDate.now(),
                "birulya.zhenka1@gmail.com",
                new ArrayList<>());

        UserResponseDto userResponseDto2 = new UserResponseDto(2L,
                "Zenya2",
                "Birulia",
                LocalDate.now(),
                "birulya.zhenka2@gmail.com",
                new ArrayList<>());

        UserResponseDto userResponseDto3 = new UserResponseDto(3L,
                "Zenya3",
                "Birulia",
                LocalDate.now(),
                "birulya.zhenka3@gmail.com",
                new ArrayList<>());

        List<User> listUser = new ArrayList<>(List.of(user1, user2, user3));

        List<UserResponseDto> listDto = new ArrayList<>(List.of(userResponseDto1, userResponseDto2, userResponseDto3));

        Page<User> pageUser = new PageImpl<>(listUser);

        Page<UserResponseDto> pageDto = new PageImpl<>(listDto);

        when(userRepositoryJpa.findAll(pageable)).thenReturn(pageUser);
        when(userMapper.toDto(user1)).thenReturn(userResponseDto1);
        when(userMapper.toDto(user2)).thenReturn(userResponseDto2);
        when(userMapper.toDto(user3)).thenReturn(userResponseDto3);

        assertEquals(userService.findAllUsers(pageable), pageDto);
    }

    @Test
    public void getUserByEmailExceptionTest() {
        when(userRepositoryJpa.findByEmail("birulya.zhenka@gmail.com")).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUserByEmail("birulya.zhenka@gmail.com"));

    }

    @Test
    public void getUserByEmailTest() {
        User user = new User(1L,
                "Zenya",
                "Birulia",
                LocalDate.now(),
                "birulya.zhenka@gmail.com",
                new ArrayList<>());

        UserResponseDto userResponseDto = new UserResponseDto(1L,
                "Zenya",
                "Birulia",
                LocalDate.now(),
                "birulya.zhenka@gmail.com",
                new ArrayList<>());

        when(userRepositoryJpa.findByEmail("birulya.zhenka@gmail.com")).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(userResponseDto);

        UserResponseDto result = userService.getUserByEmail("birulya.zhenka@gmail.com");

        assertEquals(1L, result.id());
        assertEquals("birulya.zhenka@gmail.com", result.email());
    }

    @Test
    public void updateUserExceptionTest() {
        UserRequestDto userRequestDto = new UserRequestDto("Zenya",
                "Birulia",
                LocalDate.now(),
                "birulya.zhenka@gmail.com",
                new ArrayList<>());


        when(userRepositoryJpa.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.updateUser(1L, userRequestDto));

    }

    @Test
    public void updateUserTest() {
        UserRequestDto userRequestDto = new UserRequestDto("Zenya",
                "Birulia",
                LocalDate.now(),
                "birulya.zhenka@gmail.com",
                new ArrayList<>());

        User user = new User(null,
                "Zenya",
                "Birulia",
                LocalDate.now(),
                "birulya.zhenka@gmail.com",
                new ArrayList<>());

        User saveUser = new User(1L,
                "Zenya",
                "Birulia",
                LocalDate.now(),
                "birulya.zhenka@gmail.com",
                new ArrayList<>());
        User user1 = new User(1L,
                "Zenya",
                "Birulia",
                LocalDate.now(),
                "birulya.zhenka@gmail.com",
                new ArrayList<>());
        UserResponseDto userResponseDto = new UserResponseDto(1L,
                "Zenya",
                "Birulia",
                LocalDate.now(),
                "birulya.zhenka@gmail.com",
                new ArrayList<>());

        when(userRepositoryJpa.findById(1L)).thenReturn(Optional.of(saveUser));
        when(userMapper.toEntity(userRequestDto)).thenReturn(saveUser);
        when(userRepositoryJpa.save(saveUser)).thenReturn(saveUser);
        when(userMapper.toDto(saveUser)).thenReturn(userResponseDto);

        assertEquals(userService.updateUser(1L, userRequestDto), userResponseDto);
    }

    @Test
    public void deleteUserByIdExceptionTest() {
        when(userRepositoryJpa.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.deleteUserById(1L));
    }

    @Test
    public void deleteUserByUdTest() {
        User user = new User(1L,
                "Zenya",
                "Birulia",
                LocalDate.now(),
                "birulya.zhenka@gmail.com",
                new ArrayList<>());

        when(userRepositoryJpa.findById(1L)).thenReturn(Optional.of(user));
        userRepositoryJpa.deleteById(1L);
    }




}
