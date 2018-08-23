package com.galvanize.jalbersh.springplayground.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.galvanize.jalbersh.springplayground.service.WordCounter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/words")
public class WordCounterController {

    @Autowired
    private WordCounter wc;

    public WordCounterController(WordCounter wc) {
        this.wc = wc;
    }

    @RequestMapping(value = "/count", method = POST, consumes = "text/plain", produces = "application/json")
    public String count(@RequestBody String sentence) {
        System.out.println("in count with "+sentence);
        Map<String,Integer> data = wc.count(sentence);
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(data);
        } catch (JsonProcessingException jpe) {
            System.out.println("Exception caught: "+jpe.getMessage());
            return null;
        }
    }

}