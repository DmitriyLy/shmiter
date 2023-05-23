package org.dmly.shmiter.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.dmly.shmiter.dto.MessageDto;
import org.dmly.shmiter.model.User;
import org.dmly.shmiter.service.MessageService;
import org.dmly.shmiter.service.UserService;
import org.dmly.shmiter.utils.ValidationUtils;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MessageController extends AbstractController {

    private final MessageService messageService;
    private final UserService userService;

    @GetMapping(path = "/messages")
    public String displayIndex(@RequestParam(required = false, name = "filterTag", defaultValue = "") String filterTag,
                               Model model,
                               HttpServletRequest request) {

        if (filterTag != null && !filterTag.isEmpty()) {
            model.addAttribute("messages", messageService.findByTag(filterTag));
        } else {
            model.addAttribute("messages", messageService.findAll());
        }
        model.addAttribute("filterTag", filterTag);

        addRequiredAttributes(model, request);
        return "messages";
    }

    @PostMapping(path = "/messages")
    public String addMessage(
            @AuthenticationPrincipal User user,
            @Valid MessageDto messageDto,
            BindingResult bindingResult,
            Model model,
            HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ValidationUtils.getFieldsErrorsMap(bindingResult);
            model.addAttribute("messageFiledError", errors.get("message"));
            model.addAttribute("messages", messageService.findAll());
            model.addAttribute("filterTag", "");
            addRequiredAttributes(model, request);
            return "messages";
        } else {
            messageService.save(messageDto, user);
            return "redirect:/messages";
        }
    }

    @GetMapping(path = "/user-messages/{userId}")
    public String displayUserMessages(@PathVariable Long userId,
                                      @AuthenticationPrincipal User currentUser,
                                      Model model,
                                      HttpServletRequest request) {
        model.addAttribute("messages", messageService.findByAuthorId(userId));
        model.addAttribute("messageAuthorName", getUsername(userId, currentUser));

        addRequiredAttributes(model, request);
        return "userMessages";
    }

    @GetMapping(path = "/edit-message/{messageId}")
    public String displayEditMessagePage(@PathVariable Long messageId,
                                         Model model,
                                         HttpServletRequest request) {

        model.addAttribute("message", messageService.getById(messageId));
        addRequiredAttributes(model, request);
        return "editMessage";
    }

    @PostMapping(path = "/save-message-changes")
    public String saveMessageChanges(MessageDto messageDto, @AuthenticationPrincipal User user) {
        messageService.save(messageDto, user);
        return "redirect:/edit-message/" + messageDto.getId();
    }

    private String getUsername(Long userId, User currentUser) {
        if (userId.equals(currentUser.getId())) {
            return currentUser.getUsername();
        }
        return userService.findById(userId).map(User::getUsername).orElse("<none>");
    }
}
