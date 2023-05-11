package org.dmly.shmiter.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.dmly.shmiter.dto.CreateUserDto;
import org.dmly.shmiter.dto.UserActionResult;
import org.dmly.shmiter.service.RegistrationService;
import org.dmly.shmiter.utils.ValidationUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    public String displayRegistrationPage(Model model, HttpServletRequest request) {
        addRequiredAttributes(model, request);
        return "registration";
    }

    @PostMapping
    public String addNewUser(@Valid CreateUserDto createUserDto,
                             BindingResult bindingResult,
                             Model model,
                             HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            Map<String, String> fieldsErrorsMap = ValidationUtils.getFieldsErrorsMap(bindingResult);
            model.addAttribute("usernameValidationError", fieldsErrorsMap.get("username"));
            model.addAttribute("passwordValidationError", fieldsErrorsMap.get("password"));
            model.addAttribute("passwordConfirmationValidationError", fieldsErrorsMap.get("passwordConfirmation"));
            model.addAttribute("emailValidationError", fieldsErrorsMap.get("email"));
        } else {
            UserActionResult result = registrationService.register(createUserDto);

            if (!result.isSuccessful()) {
                model.addAttribute("errorMessage", result.errorMessage());
            } else {
                model.addAttribute("activationUrl", "http://localhost:8080/registration/activation?token=" + result.token());
            }
        }

        addRequiredAttributes(model, request);
        return "registration";
    }

    @GetMapping(path = "/activation")
    public String performActivation(@RequestParam(name = "token", required = true) String token,
                                    Model model,
                                    HttpServletRequest request) {

        UserActionResult result = registrationService.activate(token);

        if (!result.isSuccessful()) {
            model.addAttribute("errorMessage", result.errorMessage());
            return "registration";
        }

        addRequiredAttributes(model, request);
        return "redirect:/login";
    }
}
