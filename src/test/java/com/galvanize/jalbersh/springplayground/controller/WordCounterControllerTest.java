package com.galvanize.jalbersh.springplayground.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.galvanize.jalbersh.springplayground.model.WordCount;
import com.galvanize.jalbersh.springplayground.service.WordCounterService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(WordCounterController.class)
public class WordCounterControllerTest {

    @MockBean
    WordCounterService count;

    @Autowired
    private MockMvc mvc;

    private WordCounterController controller;

    @Before
    public void setup() throws Exception {
        String expectedCount = "{ \"how\": 2, \"now\": 2 }";
        ObjectMapper mapper = new ObjectMapper();
        Map<String,Integer> expectedCounts = mapper.readValue(expectedCount,new TypeReference<Map<String, Integer>>(){});
        WordCount wc = new WordCount("how",2);
        when(count.count(anyString())).thenReturn(expectedCounts);
        controller = new WordCounterController(count);
    }

    @Test
    public void testWordCounterEndpoint() throws Exception {
        String sentence="how now, how now";
        MockHttpServletRequestBuilder request = post("/words/count")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.TEXT_PLAIN)
                .content(sentence);
//        this.mvc.perform(request)
//                .andExpect(status().isOk());
        MvcResult result = this.mvc.perform(request)
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        System.out.println("content="+content);
        ObjectMapper mapper = new ObjectMapper();
        Map<String,Integer> counts = mapper.readValue(content,new TypeReference<Map<String,Integer>>(){});
        assertThat(counts.size(), equalTo(2));
        assertThat(counts.get("how"), equalTo(2));
        assertThat(counts.get("now"), equalTo(2));
    }
}