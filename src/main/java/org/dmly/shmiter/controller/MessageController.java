package org.dmly.shmiter.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.dmly.shmiter.model.Message;
import org.dmly.shmiter.model.User;
import org.dmly.shmiter.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
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
                               Map<String, Object> model,
                               HttpServletRequest request) {

        if (filterTag != null && !filterTag.isEmpty()) {
            model.put("messages", repository.findByTag(filterTag));
        } else {
            model.put("messages", repository.findAll());
        }
        model.put("filterTag", filterTag);

        addRequiredAttributes(model, request);
        return "messages";
    }

    @PostMapping(path = "/add-message")
    public String addMessage(
            @AuthenticationPrincipal User user,
            @RequestParam(required = true, name = "message") String message,
            @RequestParam(required = false, defaultValue = "", name = "tag") String tag,
            @RequestParam(required = false, name = "file") MultipartFile file,
            HttpServletRequest request) throws IOException {

        String fileName = getAttachmentFilename(file).orElse(null);

        repository.save(new Message(message, tag, user, fileName));
        return "redirect:/messages";
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
