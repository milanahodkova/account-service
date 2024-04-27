package org.project.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.project.dto.UserRequest;
import org.project.dto.UserResponse;
import org.project.exception.EntityAlreadyExistsException;
import org.project.exception.EntityNotFoundException;
import org.project.model.UserEntity;
import org.project.repository.UserRepository;
import org.project.service.UserService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    @Override
    public UserResponse getInfo(UUID uuid) {
      UserEntity user = userRepository.findByUuid(uuid)
              .orElseThrow(() -> new EntityNotFoundException("User not found"));
      return convertToDto(user);
    }

    @Override
    public void create(UserRequest userRequest) {
        UserEntity userEntity = convertToEntity(userRequest);
        if (userRepository.existsByDocument(userRequest.getDocument())) {
            throw new EntityAlreadyExistsException("User already exists");
        }
        userRepository.save(userEntity);
    }

    @Override
    public void update(UUID uuid, UserRequest userRequest) {
        UserEntity user = userRepository.findByUuid(uuid)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        user.setName(userRequest.getName());
        user.setDocument(userRequest.getDocument());

        userRepository.save(user);
    }

    @Override
    public void delete(UUID uuid) {
        UserEntity user = userRepository.findByUuid(uuid)
                .orElseThrow(() -> new EntityNotFoundException());

        userRepository.delete(user);
    }

    private UserEntity convertToEntity(UserRequest user) {
        return modelMapper.map(user, UserEntity.class);
    }

    private UserResponse convertToDto(UserEntity user) {
        return modelMapper.map(user, UserResponse.class);
    }
}
