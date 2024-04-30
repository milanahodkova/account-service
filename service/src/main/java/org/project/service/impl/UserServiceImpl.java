package org.project.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.project.dto.request.UserRequest;
import org.project.dto.response.UserResponse;
import org.project.exception.AlreadyExistsException;
import org.project.exception.NotFoundException;
import org.project.model.UserEntity;
import org.project.repository.UserRepository;
import org.project.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public UserResponse getInfo(UUID id) {
        UserEntity user = findByIdOrThrow(id);
        return convertToDto(user);
    }

    @Override
    @Transactional
    public UserResponse create(UserRequest userRequest) {
        if (userRepository.existsByDocTypeAndDocNumber(userRequest.getDocType(),
                userRequest.getDocNumber())) {
            throw new AlreadyExistsException("User is already exists");
        }
        UserEntity userEntity = convertToEntity(userRequest);
        userRepository.save(userEntity);
        return convertToDto(userEntity);
    }

    @Override
    @Transactional
    public UserResponse update(UUID id, UserRequest userRequest) {
        UserEntity user = findByIdOrThrow(id);

        user.setName(userRequest.getName());
        user.setDocType(userRequest.getDocType());
        user.setDocNumber(userRequest.getDocNumber());

        userRepository.save(user);
        return convertToDto(user);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        UserEntity user = findByIdOrThrow(id);
        userRepository.delete(user);
    }

    private UserEntity findByIdOrThrow(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User  wasn't found"));
    }

    private UserEntity convertToEntity(UserRequest user) {
        return modelMapper.map(user, UserEntity.class);
    }

    private UserResponse convertToDto(UserEntity user) {
        return modelMapper.map(user, UserResponse.class);
    }
}
