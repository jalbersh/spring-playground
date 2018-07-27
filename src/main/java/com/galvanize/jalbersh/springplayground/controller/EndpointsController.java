package com.galvanize.jalbersh.springplayground.controller;

import com.galvanize.jalbersh.springplayground.model.OperationData;
import com.galvanize.jalbersh.springplayground.model.OperationDataBuilder;
import com.galvanize.jalbersh.springplayground.service.MathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Collectors;

import static com.galvanize.jalbersh.springplayground.model.OperationDataBuilder.operationDataBuilder;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/math")
public class EndpointsController {
    @Autowired
    private MathService ms;

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

    @RequestMapping(value = "/calculate", method = GET)
    public String doCalc(@RequestParam(required = false) String operation,
                         @RequestParam(required = true) String x,
                         @RequestParam(required = true) String y) {
        OperationData data = operationDataBuilder().x(x).y(y).operation(operation).build();
        return ms.calculate(data);
    }

    @RequestMapping(value = "/sum", method = POST)
    public String sum(WebRequest webRequest) {
        Map<String,String[]> params = webRequest.getParameterMap();
        String[] ns = params.get("n");
        return ms.sum(ns);
    }

    @RequestMapping(value = "/volume/{width}/{length}/{height}", method = POST)
    public String volume(@PathVariable int width, @PathVariable int length, @PathVariable int height) {
        System.out.println("in volume");
        return ms.volume(width,length,height);
    }
}