package com.galvanize.jalbersh.springplayground.controller;

import com.galvanize.jalbersh.springplayground.model.OperationData;
import com.galvanize.jalbersh.springplayground.model.OperationDataBuilder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

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

    @RequestMapping(value = "/calculate", method = GET)
    public String doCalc(@RequestParam(required = false) String operation,
                         @RequestParam(required = true) String x,
                         @RequestParam(required = true) String y) {
        OperationData data = new OperationDataBuilder().x(x).y(y).operation(operation).build();
        return calculate(data);
    }

    @RequestMapping(value = "/sum", method = POST)
    public String sum(WebRequest webRequest) {
        Map<String,String[]> params = webRequest.getParameterMap();
        String[] ns = params.get("n");
        double sum = 0.0;
        for (String n : ns) {
            sum += Double.parseDouble(n);
        }
        return Double.toString(sum)+"\n";
    }

    private String calculate(OperationData data) {
        double x = 0.0;
        double y = 0.0;
        try {
            x = Double.parseDouble(data.getX());
            y = Double.parseDouble(data.getY());
        } catch (NumberFormatException e) {
            System.err.println("parsing problem encountered: "+e.getMessage());
        }
        switch(data.getOperation()) {
            case "add": return Double.toString(x+y)+"\n";
            case "subtract": return Double.toString(x-y)+"\n";
            case "multiply": return Double.toString(x*y)+"\n";
            case "divide": return Double.toString(x/y)+"\n";
            default: return Double.toString(x+y)+"\n";
        }
    }
}