package com.galvanize.jalbersh.springplayground.service;

import com.galvanize.jalbersh.springplayground.config.WordCountConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class WordCounterService {
    private static String delimiter=" ";
    private static String punctuation=",.'!?;:";
    private String sentence;

    private WordCountConfig wordCountConfig;

    public WordCounterService(WordCountConfig wordCountConfig) {
        this.wordCountConfig = wordCountConfig;
    }

//    @Bean
    public WordCountConfig getWordCountConfig() {
        return this.wordCountConfig;
    }

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
        boolean sensitive = wordCountConfig.isCaseSensitive();
//        System.out.println("sensitive="+sensitive);
//        if (wordCountConfig != null) {
//            System.out.println("wordCountConfig not null");
//        }
//        if (wordCountConfig.getWords() != null) {
//            System.out.println("wordCountConfig.getWords() not null");
//        } else {
//            System.out.println("wordCountConfig.getWords() is null");
//        }
        List<String> skipWords = wordCountConfig.getWords().getSkip();
//        System.out.println("skipWords="+skipWords);
        for (String word:words) {
            String checkWord = sensitive ? word : word.toLowerCase();
//            System.out.println("checkWord="+checkWord);
            if (!skipWords.contains(checkWord)) {
//                System.out.println("adding word="+checkWord);
                if (counts.containsKey(checkWord)) {
                    counts.replace(checkWord, counts.get(checkWord), counts.get(checkWord) + 1);
                } else {
                    counts.put(checkWord, 1);
                }
            }
        }
//        System.out.println("count returning: "+counts);
        return counts;
    }
}
