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
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@Tag(name = "Пользователи", description = "Операции для управления пользователями")
public class UserController {
    private final UserService userService;

    @Operation(summary = "Получение данных пользователя",
            description = "Извлекает информацию о пользователе по его уникальному идентификатору")
    @GetMapping("/{uuid}")
    public UserResponse getUser(@PathVariable UUID uuid) {
        return userService.getInfo(uuid);
    }

    @Operation(summary = "Создание нового пользователя",
            description = "Регистрирует нового пользователя в системе и возвращает его данные")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse createUser(@Valid @RequestBody @Parameter(description = "Данные пользователя")
                                                       UserRequest userRequest) {
        return userService.create(userRequest);
    }

    @Operation(summary = "Обновление данных пользователя",
            description = "Обновляет информацию о пользователе с заданным идентификатором")
    @PutMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse updateUser(
            @PathVariable @Parameter(description = "Идентификатор пользователя") UUID uuid,
            @Valid @RequestBody @Parameter(description = "Данные пользователя") UserRequest userRequest
    ) {
        return userService.update(uuid, userRequest);
    }

    @Operation(summary = "Удаление пользователя",
            description = "Удаляет пользователя из системы по его уникальному идентификатору")
    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(
            @PathVariable @Parameter(description = "Идентификатор пользователя") UUID uuid
    ) {
        userService.delete(uuid);
    }
}
