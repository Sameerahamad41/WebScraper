package com.WebScrapping.WebScrapping.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootController {

    @GetMapping("/")
    public String index() {
        // Forward / to the static index.html
        return "forward:/index.html";
    }
}
