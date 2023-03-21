package org.dmly.shmiter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class GreetingsController {

    /*@GetMapping
    public String displayIndex() {
        return "index";
    }*/

    @GetMapping(path = "/greetings")
    public String displayGreetings(@RequestParam(required = false, defaultValue = "world") String name, Map<String, Object> model) {
        model.put("name", name);
        return "greetings";
    }

}
