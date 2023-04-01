package org.dmly.shmiter.dto;

public record CreateUserDto(String username, String password, String confirmPassword) {
}
