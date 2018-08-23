package com.galvanize.jalbersh.springplayground.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class WordCounter {
    private static String delimiter=" ";
    private static String punctuation=",.'!?;:";
    private String sentence;

    @Bean
    public Map<String, Integer> getCount() throws Exception {
        return count(sentence);
    }

    public Map<String, Integer> count(String sentence) {
        this.sentence = sentence;
        if (sentence == null) {
            return new HashMap<>();
        }
        String noPunc = sentence.replaceAll("\\p{P}", "");
        String [] words = noPunc.split(delimiter);
        Map<String,Integer> counts = new HashMap<>();
        for (String word:words) {
            if (counts.containsKey(word)) {
                counts.replace(word,counts.get(word),counts.get(word)+1);
            } else {
                counts.put(word,1);
            }
        }
        System.out.println("count returning: "+counts);
        return counts;
    }
}
