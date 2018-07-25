package com.galvanize.jalbersh.springplayground.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/questions/bysurvey")
public class QuestionsController {
    @GetMapping("/all")
    public String myMethod() {
        return "Nailed it!";
    }
}