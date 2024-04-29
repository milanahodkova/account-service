package org.project.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.project.dto.request.UserRequest;
import org.project.dto.response.UserResponse;
import org.project.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@Tag(name = "Пользователи", description = "Операции для управления пользователями")
public class UserController {
    private final UserService userService;

    @GetMapping("/{uuid}")
    @Operation(summary = "Получение данных пользователя",
            description = "Извлекает информацию о пользователе по его уникальному идентификатору")
    public ResponseEntity<UserResponse> getUser(@PathVariable UUID uuid) {
        return new ResponseEntity<>(userService.getInfo(uuid), HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Создание нового пользователя",
            description = "Регистрирует нового пользователя в системе и возвращает его данные")
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody @Parameter(description = "Данные пользователя")
                                                       UserRequest userRequest) {
        return new ResponseEntity<>(userService.create(userRequest),HttpStatus.CREATED);
    }

    @PutMapping("/{uuid}")
    @Operation(summary = "Обновление данных пользователя",
            description = "Обновляет информацию о пользователе с заданным идентификатором")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable @Parameter(description = "Идентификатор пользователя") UUID uuid,
            @Valid @RequestBody @Parameter(description = "Данные пользователя") UserRequest userRequest
    ) {
        return new ResponseEntity<>(userService.update(uuid, userRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{uuid}")
    @Operation(summary = "Удаление пользователя",
            description = "Удаляет пользователя из системы по его уникальному идентификатору")
    public ResponseEntity<Void> deleteUser(
            @PathVariable @Parameter(description = "Идентификатор пользователя") UUID uuid
    ) {
        userService.delete(uuid);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
