package org.project.service;

import org.project.dto.request.UserRequest;
import org.project.dto.response.UserResponse;

import java.util.UUID;

public interface UserService {
    UserResponse getInfo(UUID uuid);

    UserResponse create(UserRequest userRequest);

    UserResponse update(UUID uuid, UserRequest userRequest);

    void delete(UUID uuid);
}
