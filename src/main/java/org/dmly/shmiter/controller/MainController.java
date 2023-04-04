package org.dmly.shmiter.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class MainController extends AbstractController {

    @GetMapping(path = "/")
    public String displayIndex(Map<String, Object> model, HttpServletRequest request) {
        addRequiredAttributes(model, request);
        return "index";
    }

    @GetMapping(path = "/login")
    public String displayLoginPage(Map<String, Object> model, HttpServletRequest request) {
        addRequiredAttributes(model, request);
        return "login";
    }

}
