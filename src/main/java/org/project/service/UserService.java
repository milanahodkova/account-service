package org.project.service;

import org.project.dto.UserRequest;
import org.project.dto.UserResponse;

import java.util.UUID;

public interface UserService {
    UserResponse getInfo(UUID uuid);

    UserResponse create(UserRequest userRequest);

    UserResponse update(UUID uuid, UserRequest userRequest);

    void delete(UUID uuid);
}
