package org.dmly.shmiter.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;

import java.util.Map;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

public abstract class AbstractController {

    protected void addRequiredAttributes(Map<String, Object> model, HttpServletRequest request) {
        model.put("securityContext", request.getSession().getAttribute(SPRING_SECURITY_CONTEXT_KEY));
    }

    protected void addRequiredAttributes(Model model, HttpServletRequest request) {
        model.addAttribute("securityContext", request.getSession().getAttribute(SPRING_SECURITY_CONTEXT_KEY));
    }

}
