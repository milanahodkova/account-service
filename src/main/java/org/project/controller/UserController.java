package org.project.controller;

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
    public ResponseEntity<?> createUser(@RequestBody UserRequest userRequest) {
        userService.create(userRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(path = "/{uuid}")
    public ResponseEntity<?> updateUser(@PathVariable UUID uuid, @RequestBody UserRequest userRequest) {
        userService.update(uuid, userRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> deleteUser(@PathVariable(name = "uuid") UUID uuid) {
        userService.delete(uuid);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
