package org.dmly.shmiter.service.impl;

import org.dmly.shmiter.dto.CreateUserDto;
import org.dmly.shmiter.dto.UserActionResult;
import org.dmly.shmiter.model.ActivationToken;
import org.dmly.shmiter.model.Role;
import org.dmly.shmiter.model.User;
import org.dmly.shmiter.repository.ActivationTokenRepository;
import org.dmly.shmiter.repository.UserRepository;
import org.dmly.shmiter.service.ActivationTokenProvider;
import org.dmly.shmiter.service.RegistrationService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    private final UserRepository userRepository;
    private final ActivationTokenProvider tokenProvider;
    private final ActivationTokenRepository tokenRepository;

    public RegistrationServiceImpl(UserRepository userRepository,
                                   ActivationTokenProvider tokenProvider,
                                   ActivationTokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.tokenProvider = tokenProvider;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public UserActionResult register(CreateUserDto createUserDto) {
        if (createUserDto.username() == null || createUserDto.username().isEmpty()) {
            return new UserActionResult(false, "Username is empty", "");
        }

        if (userRepository.findByUsername(createUserDto.username()) != null) {
            return new UserActionResult(false, "Provided username already presents", "");
        }

        if (createUserDto.password() == null || createUserDto.password().isEmpty()) {
            return new UserActionResult(false, "Password is empty", "");
        }

        if (createUserDto.confirmPassword() == null || createUserDto.confirmPassword().isEmpty()) {
            return new UserActionResult(false, "Password confirmation is empty", "");
        }

        if (!createUserDto.password().equals(createUserDto.confirmPassword())) {
            return new UserActionResult(false, "Password and confirmation do not match", "");
        }

        User newUser = new User(createUserDto.username(), createUserDto.password(), false, Set.of(Role.USER));
        ActivationToken token = tokenProvider.getToken();

        newUser.setActivationToken(token);
        token.setUser(newUser);
        userRepository.save(newUser);

        return new UserActionResult(true, "", token.getToken());
    }

    @Override
    public UserActionResult activate(String tokenValue) {

        if (tokenValue == null) {
            return new UserActionResult(false, "Token cannot be empty", "");
        }

        Optional<ActivationToken> activationToken = tokenRepository.findByToken(tokenValue);

        if (activationToken.isEmpty()) {
            return new UserActionResult(false, "Cannot find provided token", "");
        }

        User user = activationToken.get().getUser();
        user.setActive(true);
        userRepository.save(user);

        return new UserActionResult(true, "", "");
    }
}
