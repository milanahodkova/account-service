package org.project.service.impl;

import org.hibernate.validator.constraints.ModCheck;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.project.dto.request.UserRequest;
import org.project.dto.response.UserResponse;
import org.project.exception.AlreadyExistsException;
import org.project.exception.NotFoundException;
import org.project.model.UserEntity;
import org.project.model.enums.DocumentType;
import org.project.repository.UserRepository;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private UUID userId;
    private UserEntity userEntity;
    private UserRequest userRequest;
    private UserResponse userResponse;

    @BeforeEach
    void setUp() {
        this.userRequest = UserRequest.builder()
                .name("Test User")
                .docNumber("AB112233")
                .docType(DocumentType.PASSPORT)
                .build();

        this.userId = UUID.randomUUID();

        this.userEntity = UserEntity.builder()
                .id(userId)
                .name(userRequest.getName())
                .docType(userRequest.getDocType())
                .docNumber(userRequest.getDocNumber())
                .build();

        this.userResponse = UserResponse.builder()
                .id(userId)
                .name(userRequest.getName())
                .docType(userRequest.getDocType())
                .docNumber(userRequest.getDocNumber())
                .build();
    }

    @Test
    void getInfo_ShouldReturnUserInfo_WhenUserExists() {
        when(modelMapper.map(userEntity, UserResponse.class)).thenReturn(userResponse);
        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));

        UserResponse result = userService.getInfo(userId);

        assertNotNull(result);
        assertEquals(userResponse, result);
        verify(userRepository, times(1)).findById(userId);

    }

    @Test
    void getInfo_ShouldReturnUserInfo_WhenUserDoesNotExist() {
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.getInfo(userId));

        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void createUser_ShouldCreateUser_WhenUserDoesNotExist() {
        when(userRepository.existsByDocTypeAndDocNumber(userRequest.getDocType(), userRequest.getDocNumber())).thenReturn(false);
        when(modelMapper.map(userRequest, UserEntity.class)).thenReturn(userEntity);
        when(userRepository.save(userEntity)).thenReturn(userEntity);
        when(modelMapper.map(userEntity, UserResponse.class)).thenReturn(userResponse);

        UserResponse result = userService.create(userRequest);

        assertNotNull(result);
        assertEquals(userResponse, result);

        verify(userRepository, times(1)).existsByDocTypeAndDocNumber(userRequest.getDocType(), userRequest.getDocNumber());
        verify(userRepository, times(1)).save(userEntity);
    }

    @Test
    void createUser_ShouldThrowAlreadyExistsException_WhenUserExists() {
        when(userRepository.existsByDocTypeAndDocNumber(userRequest.getDocType(),
                userRequest.getDocNumber())).thenReturn(true);

        assertThrows(AlreadyExistsException.class, () -> userService.create(userRequest));

        verify(userRepository, times(1)).existsByDocTypeAndDocNumber(userRequest.getDocType(), userRequest.getDocNumber());
        verify(userRepository, never()).save(any(UserEntity.class));
    }

    @Test
    void updateUser_ShouldUpdateUser_WhenUserExist() {
        when(modelMapper.map(userEntity, UserResponse.class)).thenReturn(userResponse);
        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));
        when(userRepository.save(any())).thenReturn(userEntity);

        UserResponse result = userService.update(userId, userRequest);

        assertNotNull(result);
        assertEquals(userResponse, result);

        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void updateUser_ShouldUpdateUser_WhenUserDoesNotExist() {
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.update(userId, userRequest));

        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, never()).save(any(UserEntity.class));
    }

    @Test
    void deleteUser_ShouldDeleteUser_WhenUserExist() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));

        userService.delete(userId);

        verify(userRepository).delete(userEntity);
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).delete(userEntity);

    }

    @Test
    void deleteUser_ShouldDeleteUser_WhenUserDoesNotExist() {
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.delete(userId));

        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, never()).delete(any(UserEntity.class));
    }

}