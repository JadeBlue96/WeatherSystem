package com.isoft.rest.controller;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@CrossOrigin(origins = "http://localhost:3000")
@Controller
public class RedirectController {
    
    @GetMapping("/private")
    public String redirectToRoot() {
        return "redirect:/";
    }
}
