package com.galvanize.jalbersh.springplayground.service;

import com.galvanize.jalbersh.springplayground.config.WordCountConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringRunner.class)
@WebMvcTest(WordCounterService.class)
@AutoConfigureMockMvc(secure=false)
public class WordCounterServiceTest {
    WordCounterService wc;

    @MockBean
    private WordCountConfig wordCountConfig;

    @Before
    public void setup() throws Exception {
        WordCountConfig.Words wordConfig = new WordCountConfig.Words();
        List<String> skipped = new ArrayList<>();
        skipped.add("the");
        skipped.add("an");
        skipped.add("a");
        wordConfig.setSkip(skipped);
        WordCountConfig wordCountConfig = new WordCountConfig();
        wordCountConfig.setCaseSensitive(false);
        wordCountConfig.setWords(wordConfig);
        wc = new WordCounterService(wordCountConfig);
    }

    @Test
    public void testWordCount() throws Exception {
        String sentence = "A brown cow jumps over a brown fox";
        Map<String,Integer> words = wc.count(sentence);
        assertThat(words.size(), equalTo(5));
        assertThat(words.get("brown"), equalTo(2));
    }

    @Test
    public void testWordCountWithPunctuation() throws Exception {
        String sentence = "how now, how now";
        Map<String,Integer> words = wc.count(sentence);
        assertThat(words.size(), equalTo(2));
        assertThat(words.get("how"), equalTo(2));
    }

    @Test
    public void testWordCountWithCaseSensitivity() throws Exception {
        String sentence = "The BROWN cow jumps over a brown fox";
        WordCountConfig.Words wordConfig = new WordCountConfig.Words();
        List<String> skipped = new ArrayList<>();
        wordConfig.setSkip(skipped);
        WordCountConfig wordCountConfig = new WordCountConfig();
        wordCountConfig.setCaseSensitive(true);
        wordCountConfig.setWords(wordConfig);
        wc = new WordCounterService(wordCountConfig);
        Map<String,Integer> words = wc.count(sentence);
        assertThat(words.size(), equalTo(8));
        assertThat(words.get("brown"), equalTo(1));
    }

    @Test
    public void testWordCountWithoutCaseSensitivity() throws Exception {
        String sentence = "The BROWN cow jumps over a brown fox";
        WordCountConfig.Words wordConfig = new WordCountConfig.Words();
        List<String> skipped = new ArrayList<>();
        wordConfig.setSkip(skipped);
        WordCountConfig wordCountConfig = new WordCountConfig();
        wordCountConfig.setCaseSensitive(false);
        wordCountConfig.setWords(wordConfig);
        wc = new WordCounterService(wordCountConfig);
        Map<String,Integer> words = wc.count(sentence);
        assertThat(words.size(), equalTo(7));
        assertThat(words.get("brown"), equalTo(2));
    }

    @Test
    public void testWordCountWithSkippingWords() throws Exception {
        String sentence = "The BROWN cow jumps over a brown fox";
        WordCountConfig.Words wordConfig = new WordCountConfig.Words();
        List<String> skipped = new ArrayList<>();
        skipped.add("the");
        skipped.add("an");
        skipped.add("a");
        wordConfig.setSkip(skipped);
        WordCountConfig wordCountConfig = new WordCountConfig();
        wordCountConfig.setCaseSensitive(false);
        wordCountConfig.setWords(wordConfig);
        wc = new WordCounterService(wordCountConfig);
        Map<String,Integer> words = wc.count(sentence);
        assertThat(words.size(), equalTo(5));
        assertThat(words.get("brown"), equalTo(2));
    }
}