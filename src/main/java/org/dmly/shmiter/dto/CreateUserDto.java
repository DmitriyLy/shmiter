package org.dmly.shmiter.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateUserDto(@NotBlank(message = "Username cannot be empty") String username,
                            @NotBlank(message = "Password cannot be empty") String password,
                            @NotBlank(message = "Password confirmation cannot be empty") String passwordConfirmation,
                            @NotBlank(message = "Email cannot be empty") @Email String email) {
}
