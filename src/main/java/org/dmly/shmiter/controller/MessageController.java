package org.dmly.shmiter.controller;

import org.dmly.shmiter.model.Message;
import org.dmly.shmiter.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class MessageController {

    private final MessageRepository repository;

    @Autowired
    public MessageController(MessageRepository repository) {
        this.repository = repository;
    }

    @GetMapping(path = "/")
    public String displayIndex(@RequestParam(required = false, name = "filterTag") String filterTag,
                               Map<String, Object> model) {

        if (filterTag != null && !filterTag.isEmpty()) {
            model.put("messages", repository.findByTag(filterTag));
        } else {
            model.put("messages", repository.findAll());
        }
        return "index";
    }

    @PostMapping(path = "/add-message")
    public String addMessage(@RequestParam(required = true, name = "message") String message,
                             @RequestParam(required = false, defaultValue = "", name = "tag") String tag) {

        repository.save(new Message(message, tag));
        return "redirect:/";
    }

}
