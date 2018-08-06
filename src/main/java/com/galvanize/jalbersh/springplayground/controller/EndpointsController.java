package com.galvanize.jalbersh.springplayground.controller;

import com.galvanize.jalbersh.springplayground.model.Flight;
import com.galvanize.jalbersh.springplayground.model.OperationData;
import com.galvanize.jalbersh.springplayground.model.OperationDataBuilder;
import com.galvanize.jalbersh.springplayground.model.Ticket;
import com.galvanize.jalbersh.springplayground.service.MathService;
import com.galvanize.jalbersh.springplayground.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.*;
import java.util.stream.Collectors;

import static com.galvanize.jalbersh.springplayground.model.OperationDataBuilder.operationDataBuilder;
import static java.util.Arrays.asList;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class EndpointsController {
    @Autowired
    private MathService ms;

    @Autowired
    private TicketService ts;

    @GetMapping("/math/tasks")
    public String getTasks() {
        return "These are tasks\n";
    }

    @PostMapping("/math/tasks")
    public String createTask() {
        return "You just POSTed to /tasks\n";
    }

    @GetMapping("/math/pi")
    public String getPi() {
        return Math.PI+"\n";
    }

    @RequestMapping(value = "/math/calculate", method = GET)
    public String doCalc(@RequestParam(required = false) String operation,
                         @RequestParam(required = true) String x,
                         @RequestParam(required = true) String y) {
        OperationData data = operationDataBuilder().x(x).y(y).operation(operation).build();
        return ms.calculate(data);
    }

    @RequestMapping(value = "/math/sum", method = POST)
    public String sum(WebRequest webRequest) {
        Map<String,String[]> params = webRequest.getParameterMap();
        String[] ns = params.get("n");
        return ms.sum(ns);
    }

    @RequestMapping(value = "/math/volume/{width}/{length}/{height}", method = GET)
    public String volume(@PathVariable int width, @PathVariable int length, @PathVariable int height) {
        return ms.volume(width,length,height);
    }

    @PostMapping(value = "/math/area", consumes = MediaType.TEXT_PLAIN_VALUE)
    public String areaPlain(@PathVariable String type, @PathVariable int width, @PathVariable int height, @PathVariable int radius) {
        return ms.area(type,width,height,radius);
    }

    @PostMapping(value = "/math/area", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String area(@RequestParam Map<String, String> formData) {
        int width=Integer.parseInt(formData.get("width") != null ? formData.get("width") : "0");
        int height=Integer.parseInt(formData.get("height") != null ? formData.get("height") : "0");
        int radius=Integer.parseInt(formData.get("radius") != null ? formData.get("radius") : "0");
        String type=formData.get("type");
        return ms.area(type,width,height,radius);
    }

    @GetMapping(value = "/flights/flight", produces = "application/json")
    public Flight getSingleFlight() {
        return ts.getFlight();
    }

    @GetMapping(value = "/flights", produces = "application/json")
    public List<Flight> getFlights() {
        return ts.getFlights();
    }

    @PostMapping(value = "/jr/object-example", produces = "application/json")
    public String getObjectParams() {
        return "Search: q=something from=2008";
    }

    @PostMapping(value = "/flights/tickets/total", consumes = "application/json")
    public String sumTotalTickets(@RequestBody List<Ticket> tickets) {
        return ts.sumTickets(tickets);
    }

}