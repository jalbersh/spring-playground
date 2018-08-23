package com.galvanize.jalbersh.springplayground.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

import static com.sun.javafx.fxml.expression.Expression.notEqualTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

@RunWith(SpringRunner.class)
@WebMvcTest(WordCounter.class)
public class WordCounterTest {
    @MockBean
    WordCounter ws;

    @Before
    public void setup() throws Exception {
        ws = new WordCounter();
    }

    @Test
    public void testWordCount() throws Exception {
        String sentence = "A brown cow jumps over a brown fox";
        Map<String,Integer> words = ws.count(sentence);
        assertThat(words.size(), equalTo(7));
        assertThat(words.get("brown"), equalTo(2));
    }
}