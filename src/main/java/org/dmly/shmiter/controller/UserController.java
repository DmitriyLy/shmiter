package org.dmly.shmiter.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.dmly.shmiter.dto.EditUserDto;
import org.dmly.shmiter.model.Role;
import org.dmly.shmiter.model.User;
import org.dmly.shmiter.repository.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpRequest;
import java.util.Arrays;
import java.util.Map;

@Controller
@RequestMapping(path = "/users")
@PreAuthorize("hasAnyAuthority('ADMIN')")
public class UserController extends AbstractController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public String displayUsersPage(Model model, HttpServletRequest request) {
        model.addAttribute("users", userRepository.findAll());
        addRequiredAttributes(model, request);
        return "users";
    }

    @GetMapping("{id}")
    public String displayUserEditForm(@PathVariable(name = "id") Long id,
                                      Model model,
                                      HttpServletRequest request) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        EditUserDto userDto = new EditUserDto(user.getId(), user.getUsername(), user.getRoles());
        model.addAttribute("user", userDto);
        model.addAttribute("roles", Role.values());
        addRequiredAttributes(model, request);
        return "userEditForm";
    }

    @PostMapping
    public String performUserEdit(@RequestParam(name = "id") Long id,
                                  @RequestParam(name = "username") String username,
                                  HttpServletRequest request) {

        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        Map<String, String[]> parameterMap = request.getParameterMap();
        user.getRoles().clear();

        Arrays.stream(Role.values()).forEach(role -> {
            if (parameterMap.containsKey(role.name())) {
                user.getRoles().add(role);
            }
        });

        userRepository.save(user);

        return "redirect:/users";
    }

}
