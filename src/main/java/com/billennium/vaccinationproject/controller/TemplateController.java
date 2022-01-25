package com.billennium.vaccinationproject.controller;


import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/")
public class TemplateController {

    @GetMapping("login")
    public String getLoginPage() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        boolean userNotLoggedIn = auth instanceof AnonymousAuthenticationToken;

        if (!userNotLoggedIn) {
            return "index";
        }

        return "login";
    }

    @GetMapping("index")
    public String getIndexPage() {
        return "index";
    }

    @GetMapping("/swagger")
    public RedirectView redirectViewToSwagger(RedirectAttributes attributes) {
        attributes.addFlashAttribute("flashAttribute","redirectViewToSwagger");
        attributes.addAttribute("attribute", "redirectViewToSwagger");
        return new RedirectView("swagger-ui.html#/");
    }
}
