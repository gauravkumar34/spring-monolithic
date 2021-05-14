package com.gaurav.demomono.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth/v1")
public class BlogController {

    @GetMapping("/userss")
    public String gauravK() {
        return "Guarv KUmar";
    }
    @GetMapping("/hello/hi/gg")
    public String bro() {
        return "hi brother";
    }
}
