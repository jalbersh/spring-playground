package com.galvanize.jalbersh.springplayground.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.galvanize.jalbersh.springplayground.model.WordCount;
import com.galvanize.jalbersh.springplayground.repository.LessonRepository;
import com.galvanize.jalbersh.springplayground.service.WordCounter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(WordCounterController.class)
public class WordCounterControllerTest {

    @MockBean
    WordCounter count;

    @Autowired
    private MockMvc mvc;

    private WordCounterController controller;

    @Before
    public void setup() throws Exception {
        String expectedCount = "{ \"how\": 2, \"now\": 2 }";
        ObjectMapper mapper = new ObjectMapper();
        Map<String,Integer> expectedCounts = mapper.readValue(expectedCount,new TypeReference<Map<String, Integer>>(){});
        controller = new WordCounterController(count);
        when(count.count(anyString())).thenReturn(expectedCounts);
    }

    @Test
    public void testWordCounterEndpoint() throws Exception {
        String sentence="how now, how now";
        MockHttpServletRequestBuilder request = post("/words/count")
                .accept(MediaType.TEXT_PLAIN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(sentence);
        this.mvc.perform(request)
                .andExpect(status().isOk());
//        MvcResult result = this.mvc.perform(request)
//                .andExpect(status().isOk())
//                .andDo(print())
//                .andReturn();
//        String content = result.getResponse().getContentAsString();
//        System.out.println("content="+content);
//        ObjectMapper mapper = new ObjectMapper();
//        List<WordCount> counts = mapper.readValue(content,new TypeReference<List<WordCount>>(){});
//        assertThat(counts.size(), greaterThan(0));
//        WordCount wc = counts.get(0);
//        assertThat(wc.getWord(), equalTo("how"));
//        assertThat(wc.getCount(), equalTo(2));
    }
}