package com.galvanize.jalbersh.springplayground.controller;

import com.galvanize.jalbersh.springplayground.service.OMDBService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class OMDBController {

    private final OMDBService service;

    public OMDBController(OMDBService service) {
        this.service = service;
    }

    @RequestMapping(value = "/movies", method = POST, consumes = "text/plain", produces = "application/json")
    public String getMovies(@RequestParam String query) {
        System.out.println("in OMDBController.getMovies with "+query);
        String data = service.doQuery(query);
        System.out.println("got info from OBDM service="+data);
        return data;
//        ObjectMapper mapper = new ObjectMapper();
//        try {
//            return mapper.writeValueAsString(data);
//        } catch (JsonProcessingException jpe) {
//            System.out.println("Exception caught: "+jpe.getMessage());
//            return null;
//        }
    }

}