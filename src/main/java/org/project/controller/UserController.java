package org.project.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.project.dto.UserRequest;
import org.project.dto.UserResponse;
import org.project.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    @GetMapping(path = "/{uuid}")
    public ResponseEntity<UserResponse> getUser(@PathVariable UUID uuid) {
        return new ResponseEntity<>(userService.getInfo(uuid), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest userRequest) {
        return new ResponseEntity<>(userService.create(userRequest),HttpStatus.CREATED);
    }

    @PutMapping(path = "/{uuid}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable UUID uuid, @Valid @RequestBody UserRequest userRequest) {
        return new ResponseEntity<>(userService.update(uuid, userRequest),HttpStatus.OK);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID uuid) {
        userService.delete(uuid);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
