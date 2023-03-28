package org.dmly.shmiter.controller;

import org.dmly.shmiter.dto.UserDto;
import org.dmly.shmiter.model.Role;
import org.dmly.shmiter.model.User;
import org.dmly.shmiter.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping(path = "/registration")
public class RegistrationController {

    private final UserRepository userRepository;

    public RegistrationController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public String displayRegistrationPage() {
        return "registration";
    }

    @PostMapping
    public String addNewUser(UserDto userDto, Map<String, Object> model) {
        if (userDto.username() == null || userDto.username().isEmpty()) {
            model.put("message", "Username is empty");
            return "registration";
        }

        if (userRepository.findByUsername(userDto.username()) != null) {
            model.put("message", "Provided username already presents");
            return "registration";
        }

        if (userDto.password() == null || userDto.password().isEmpty()) {
            model.put("message", "Password is empty");
            return "registration";
        }

        if (userDto.confirmPassword() == null || userDto.confirmPassword().isEmpty()) {
            model.put("message", "Password confirmation is empty");
            return "registration";
        }

        if (!userDto.password().equals(userDto.confirmPassword())) {
            model.put("message", "Password and confirmation do not match");
            return "registration";
        }

        User newUser = new User(userDto.username(), userDto.password(), true, Set.of(Role.USER));
        userRepository.save(newUser);

        return "redirect:/login";
    }

}
