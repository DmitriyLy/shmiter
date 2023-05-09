package org.dmly.shmiter.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.dmly.shmiter.dto.EditUserDto;
import org.dmly.shmiter.dto.UserActionResult;
import org.dmly.shmiter.model.Role;
import org.dmly.shmiter.model.User;
import org.dmly.shmiter.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

@Controller
@RequestMapping(path = "/users")
public class UserController extends AbstractController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String displayUsersPage(Model model, HttpServletRequest request) {
        model.addAttribute("users", userService.findAll());
        addRequiredAttributes(model, request);
        return "users";
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String displayUserEditForm(@PathVariable(name = "id") Long id,
                                      Model model,
                                      HttpServletRequest request) {
        User user = userService.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        EditUserDto userDto = new EditUserDto(user.getId(), user.getUsername(), user.getRoles());
        model.addAttribute("user", userDto);
        model.addAttribute("roles", Role.values());
        addRequiredAttributes(model, request);
        return "userEditForm";
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String performUserEdit(@RequestParam(name = "id") Long id,
                                  @RequestParam(name = "username") String username,
                                  HttpServletRequest request) {

        User user = userService.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        Map<String, String[]> parameterMap = request.getParameterMap();
        user.getRoles().clear();

        Arrays.stream(Role.values()).forEach(role -> {
            if (parameterMap.containsKey(role.name())) {
                user.getRoles().add(role);
            }
        });

        userService.save(user);

        return "redirect:/users";
    }

    @GetMapping(path = "profile")
    public String displayUserProfilePage(Model model,
                                         HttpServletRequest request) {
        addRequiredAttributes(model, request);
        return "profile";
    }

    @PostMapping(path = "profile")
    public String saveUserProfile(@AuthenticationPrincipal User user,
                                  @RequestParam(name = "password") String newPassword,
                                  @RequestParam(name = "email") String newEmail,
                                  Model model,
                                  HttpServletRequest request) {

        UserActionResult result = userService.saveProfile(user, newPassword, newEmail);

        if (result.isSuccessful()) {
            model.addAttribute("successMessage", "Updated");
        } else {
            model.addAttribute("errorMessage", result.errorMessage());
        }

        addRequiredAttributes(model, request);
        return "profile";
    }

}
