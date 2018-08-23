package com.galvanize.jalbersh.springplayground.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(WordCounterController.class)
public class WordCounterControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void testWordCounterEndpoint() throws Exception {
        String sentence="how now, how now";
        MockHttpServletRequestBuilder request = patch("/words/count")
                .contentType(MediaType.TEXT_PLAIN)
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
//        NoPasswordUser contentUser = mapper.readValue(content,new TypeReference<NoPasswordUser>(){});
//        assertThat(contentUser.getEmail(), equalTo("some updated email"));
//        assertThat(contentUser.getId(), equalTo(savedUser.getId()));
    }
}