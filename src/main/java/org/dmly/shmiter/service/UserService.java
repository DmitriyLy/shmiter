package org.dmly.shmiter.service;

import org.dmly.shmiter.dto.UserActionResult;
import org.dmly.shmiter.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();

    Optional<User> findById(Long id);

    void save(User user);

    UserActionResult saveProfile(User user, String newPassword, String newEmail);
}
