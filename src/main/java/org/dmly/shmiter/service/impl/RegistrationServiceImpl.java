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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    private final UserRepository userRepository;
    private final ActivationTokenProvider tokenProvider;
    private final ActivationTokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;

    public RegistrationServiceImpl(UserRepository userRepository,
                                   ActivationTokenProvider tokenProvider,
                                   ActivationTokenRepository tokenRepository,
                                   PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.tokenProvider = tokenProvider;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserActionResult register(CreateUserDto createUserDto) {
        if (userRepository.findByUsername(createUserDto.username()) != null) {
            return new UserActionResult(false, "Provided username already presents", "");
        }

        if (!createUserDto.password().equals(createUserDto.passwordConfirmation())) {
            return new UserActionResult(false, "Password and confirmation do not match", "");
        }

        User newUser = new User(createUserDto.username(),
                passwordEncoder.encode(createUserDto.password()),
                false,
                Set.of(Role.USER),
                createUserDto.email());
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
