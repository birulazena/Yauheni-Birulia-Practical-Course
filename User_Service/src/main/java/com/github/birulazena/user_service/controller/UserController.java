package com.github.birulazena.user_service.controller;

import com.github.birulazena.user_service.dto.CardInfoRequestDto;
import com.github.birulazena.user_service.dto.CardInfoResponseDto;
import com.github.birulazena.user_service.dto.UserRequestDto;
import com.github.birulazena.user_service.dto.UserResponseDto;
import com.github.birulazena.user_service.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserRequestDto userDto) {
        UserResponseDto userResponseDto = userService.createUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDto);
    }

    @GetMapping
    public ResponseEntity<Page<UserResponseDto>> getAllUsers(@RequestParam int page, @RequestParam int count) {
        Pageable pageable = PageRequest.of(page, count);
        Page<UserResponseDto> responseDtoPage = userService.findAllUsers(pageable);
        return ResponseEntity.ok(responseDtoPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
        UserResponseDto userResponseDto = userService.getUserById(id);
        return ResponseEntity.ok(userResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser (@PathVariable Long id, @Valid @RequestBody UserRequestDto userDto) {
        UserResponseDto userResponseDto = userService.updateUser(id, userDto);
        return ResponseEntity.ok(userResponseDto);
    }

    @GetMapping("/by-email")
    public ResponseEntity<UserResponseDto> getUserByEmail(@RequestParam String email) {
        UserResponseDto userResponseDto = userService.getUserByEmail(email);
        return ResponseEntity.ok(userResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserResponseDto> deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/card-info")
    public ResponseEntity<CardInfoResponseDto> createCardForUser(@RequestBody CardInfoRequestDto cardInfoRequestDto){
        CardInfoResponseDto cardInfoResponseDto = userService.addCardInfoToUser(cardInfoRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(cardInfoResponseDto);
    }

    @PutMapping("/card-info/{id}")
    public ResponseEntity<CardInfoResponseDto> updateCardForUser(@PathVariable Long id, @RequestBody CardInfoRequestDto cardInfoRequestDto) {
        CardInfoResponseDto cardInfoResponseDto = userService.updateCardInfoToUser(id, cardInfoRequestDto);
        return ResponseEntity.ok(cardInfoResponseDto);
    }

    @DeleteMapping("/card-info/{id}")
    public ResponseEntity<CardInfoResponseDto> deleteCardById(@PathVariable Long id) {
        userService.deleteCardInfoFromUserById(id);
        return ResponseEntity.noContent().build();
    }

}
