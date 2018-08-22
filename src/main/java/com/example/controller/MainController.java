package com.example.controller;

import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @RequestMapping("/")
    public String helloHtml(HashMap<String, Object> map) {
        return "main";
    }
    
}
