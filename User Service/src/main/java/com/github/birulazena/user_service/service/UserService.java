package com.github.birulazena.user_service.service;

import com.github.birulazena.user_service.dto.CardInfoRequestDto;
import com.github.birulazena.user_service.dto.CardInfoResponseDto;
import com.github.birulazena.user_service.dto.UserRequestDto;
import com.github.birulazena.user_service.dto.UserResponseDto;
import com.github.birulazena.user_service.entities.CardInfo;
import com.github.birulazena.user_service.entities.User;
import com.github.birulazena.user_service.exception.CardInfoNotFoundException;
import com.github.birulazena.user_service.exception.DuplicateEmailException;
import com.github.birulazena.user_service.exception.UserNotFoundException;
import com.github.birulazena.user_service.mapper.CardInfoMapper;
import com.github.birulazena.user_service.mapper.UserMapper;
import com.github.birulazena.user_service.repository.CardInfoRepository;
import com.github.birulazena.user_service.repository.UserRepositoryJpa;
import lombok.AllArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;


@Service
@AllArgsConstructor
public class UserService {

    private CardInfoRepository cardInfoRepository;
    private UserRepositoryJpa userRepositoryJpa;
    private UserMapper userMapper;
    private CardInfoMapper cardInfoMapper;
    private CacheManager cacheManager;

    @Caching(
            put = {
                    @CachePut(value = "user_cache", key = "#result.id()"),
                    @CachePut(value = "user_email_cache", key = "#result.email()")
            }
    )
    public UserResponseDto createUser(UserRequestDto userDto) {

        if(userRepositoryJpa.findByEmail(userDto.email()).isPresent()) {
            throw new DuplicateEmailException("A user with this email already exists. Email: " + userDto.email());
        }

        User user = userMapper.toEntity(userDto);
        User saveUser = userRepositoryJpa.save(user);
        return userMapper.toDto(saveUser);
    }

    @Cacheable(value = "user_cache", key = "#id")
    public UserResponseDto getUserById(Long id) {
        User user = userRepositoryJpa.findById(id).orElseThrow(() -> new UserNotFoundException("User not found ID: " + id));
        return userMapper.toDto(user);
    }

    public Page<UserResponseDto> findAllUsers(Pageable pageable) {

        return userRepositoryJpa.findAll(pageable).map(u -> userMapper.toDto(u));
    }

    @Cacheable(value = "user_email_cache", key = "#email")
    public UserResponseDto getUserByEmail(String email) {
        User user = userRepositoryJpa.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found email: " + email));
        return userMapper.toDto(user);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Caching(
            put = {
                    @CachePut(value = "user_cache", key = "#id"),
                    @CachePut(value = "user_email_cache", key = "#result.email()")
            }
    )
    public UserResponseDto updateUser(Long id, UserRequestDto userDto) {
        userRepositoryJpa.findById(id).orElseThrow(() -> new UserNotFoundException("User not found ID: " + id));
        User user = userMapper.toEntity(userDto);
        user.setId(id);
        User saveUser = userRepositoryJpa.save(user);
        return userMapper.toDto(saveUser);
    }


    @CacheEvict(value = "user_cache", key = "#id")
    public void deleteUserById(Long id) {
        User user = userRepositoryJpa.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not fount ID: " + id));

        cacheManager.getCache("user_email_cache").evict(user.getEmail());

        userRepositoryJpa.deleteById(id);
    }

    public CardInfoResponseDto addCardInfoToUser(CardInfoRequestDto cardInfoDto){
        CardInfo cardInfo = cardInfoMapper.toEntity(cardInfoDto);
        cardInfo.setUser(userRepositoryJpa.findById(cardInfoDto.userId())
                .orElseThrow(() -> new UserNotFoundException("User not found ID: " + cardInfoDto.userId())));
        CardInfo cardInfoSave = cardInfoRepository.save(cardInfo);
        return cardInfoMapper.toDto(cardInfoSave);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public CardInfoResponseDto updateCardInfoToUser(Long id, CardInfoRequestDto cardInfoDto) {
        CardInfo cardInfo = cardInfoMapper.toEntity(cardInfoDto);
        cardInfo.setUser(userRepositoryJpa.findById(cardInfoDto.userId())
                .orElseThrow(() -> new UserNotFoundException("User not found ID: " + cardInfoDto.userId())));
        cardInfo.setId(id);
        CardInfo cardInfoSave = cardInfoRepository.save(cardInfo);
        return cardInfoMapper.toDto(cardInfoSave);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void deleteCardInfoFromUserById(Long id) {
        if(cardInfoRepository.findById(id).isEmpty()) {
            throw new CardInfoNotFoundException("Card not found ID: " + id);
        }
        cardInfoRepository.deleteById(id);
    }


}
