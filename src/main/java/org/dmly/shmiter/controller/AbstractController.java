package org.dmly.shmiter.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.web.WebAttributes;
import org.springframework.ui.Model;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

public abstract class AbstractController {
    protected void addRequiredAttributes(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        model.addAttribute("securityContext", session.getAttribute(SPRING_SECURITY_CONTEXT_KEY));
        model.addAttribute("authenticationException", session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION));
        session.setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, null);
    }

}
