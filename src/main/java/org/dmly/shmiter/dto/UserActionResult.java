package org.dmly.shmiter.dto;

public record UserActionResult(boolean isSuccessful, String errorMessage, String token) {
}
