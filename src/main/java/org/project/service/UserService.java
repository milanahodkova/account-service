package org.project.service;

import org.project.dto.UserRequest;
import org.project.dto.UserResponse;

import java.util.UUID;

public interface UserService {
    UserResponse getInfo(UUID uuid);

    void create(UserRequest userRequest);

    void update(UUID uuid, UserRequest userRequest);

    void delete(UUID uuid);
}
