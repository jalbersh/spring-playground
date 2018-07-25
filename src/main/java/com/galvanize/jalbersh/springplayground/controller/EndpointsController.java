package com.galvanize.jalbersh.springplayground.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EndpointsController {

    @GetMapping("/tasks")
    public String getTasks() {
        return "These are tasks\n";
    }

    @PostMapping
    public String createTask() {
        return "You just POSTed to /tasks\n";
    }

    @GetMapping
    public String getPi() {
        return Math.PI+"\n";
    }
}