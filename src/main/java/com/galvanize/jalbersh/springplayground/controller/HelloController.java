package com.galvanize.jalbersh.springplayground.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/awesome")
public class HelloController {

    @GetMapping("/hello")
    public String helloWorld() {
        return "Hello from Spring!\n";
    }

    @GetMapping("/")
    public String myMethod2() {
        return "Nailed it!\n";
    }

    @GetMapping("/about/team")
    public String myMethod1() {
        return "Nailed it!";
    }
}