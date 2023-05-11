package org.dmly.shmiter.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.dmly.shmiter.dto.MessageDto;
import org.dmly.shmiter.model.Message;
import org.dmly.shmiter.model.User;
import org.dmly.shmiter.repository.MessageRepository;
import org.dmly.shmiter.utils.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
public class MessageController extends AbstractController {

    private final MessageRepository repository;

    @Value("${upload.dir}")
    private String uploadDir;

    @Autowired
    public MessageController(MessageRepository repository) {
        this.repository = repository;
    }

    @GetMapping(path = "/messages")
    public String displayIndex(@RequestParam(required = false, name = "filterTag", defaultValue = "") String filterTag,
                               Model model,
                               HttpServletRequest request) {

        if (filterTag != null && !filterTag.isEmpty()) {
            model.addAttribute("messages", repository.findByTag(filterTag));
        } else {
            model.addAttribute("messages", repository.findAll());
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
            HttpServletRequest request) throws IOException {

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ValidationUtils.getFieldsErrorsMap(bindingResult);
            model.addAttribute("messageFiledError", errors.get("message"));
        } else {
            String fileName = null;

            if (messageDto.getFile() != null) {
                fileName = getAttachmentFilename(messageDto.getFile()).orElse(null);
            }

            repository.save(new Message(messageDto.getMessage(), messageDto.getTag(), user, fileName));
        }

        model.addAttribute("messages", repository.findAll());
        model.addAttribute("filterTag", "");
        addRequiredAttributes(model, request);
        return "messages";
    }

    private Optional<String> getAttachmentFilename(MultipartFile file) throws IOException {

        if (file == null) {
            return Optional.empty();
        }

        Path uploadDirPath = Path.of(System.getProperty("user.dir"), uploadDir);
        if (Files.notExists(uploadDirPath)) {
            Files.createDirectory(uploadDirPath);
        }

        String fileName = UUID.randomUUID() + "." + file.getOriginalFilename();

        Files.write(Path.of(System.getProperty("user.dir"), uploadDir, fileName), file.getBytes());

        return Optional.of(fileName);
    }

}
