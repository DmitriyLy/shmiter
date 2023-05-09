package org.dmly.shmiter.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.dmly.shmiter.dto.UserActionResult;
import org.dmly.shmiter.model.User;
import org.dmly.shmiter.repository.UserRepository;
import org.dmly.shmiter.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public UserActionResult saveProfile(User user, String newPassword, String newEmail) {

        boolean isChanged = false;

        if (StringUtils.isNotEmpty(newEmail) && !StringUtils.equals(newEmail, user.getEmail())) {
            user.setEmail(newEmail);
            isChanged = true;
        }

        if (StringUtils.isNotEmpty(newPassword) && !StringUtils.equals(newPassword, user.getPassword())) {
            user.setPassword(newPassword);
            isChanged = true;
        }

        if (isChanged) {
            userRepository.save(user);
            return new UserActionResult(true, "", "");
        }

        return new UserActionResult(false, "There is nothing to change", "");
    }
}
