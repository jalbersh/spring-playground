package com.galvanize.jalbersh.springplayground.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/math")
public class EndpointsController {

    @GetMapping("/tasks")
    public String getTasks() {
        return "These are tasks\n";
    }

    @PostMapping("/tasks")
    public String createTask() {
        return "You just POSTed to /tasks\n";
    }

    @GetMapping("/pi")
    public String getPi() {
        return Math.PI+"\n";
    }
}