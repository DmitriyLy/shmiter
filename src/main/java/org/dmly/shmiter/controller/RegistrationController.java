package org.dmly.shmiter.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.dmly.shmiter.dto.CreateUserDto;
import org.dmly.shmiter.dto.UserActionResult;
import org.dmly.shmiter.service.RegistrationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequestMapping(path = "/registration")
public class RegistrationController extends AbstractController {

    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping
    public String displayRegistrationPage(Map<String, Object> model, HttpServletRequest request) {
        addRequiredAttributes(model, request);
        return "registration";
    }

    @PostMapping
    public String addNewUser(CreateUserDto createUserDto, Map<String, Object> model) {
        UserActionResult result = registrationService.register(createUserDto);

        if (!result.isSuccessful()) {
            model.put("errorMessage", result.errorMessage());
            return "registration";
        }

        return "redirect:/login";
    }

    @GetMapping(path = "/activation")
    public String performActivation(@RequestParam(name = "token", required = true) String token,
                                    Map<String, Object> model,
                                    HttpServletRequest request) {

        UserActionResult result = registrationService.activate(token);

        if (!result.isSuccessful()) {
            model.put("errorMessage", result.errorMessage());
            return "registration";
        }

        addRequiredAttributes(model, request);
        return "redirect:/login";
    }
}
