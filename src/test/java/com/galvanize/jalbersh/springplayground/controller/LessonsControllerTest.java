package com.galvanize.jalbersh.springplayground.controller;

import com.galvanize.jalbersh.springplayground.model.Lesson;
import com.galvanize.jalbersh.springplayground.repository.LessonRepository;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(locations={"classpath:app-context.xml"})
public class LessonsControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    LessonRepository repository;

    @Before
    public void setup() throws Exception {
        Lesson lesson = new Lesson();
        lesson.setId(5L);
        lesson.setTitle("JPL");
        lesson.setDeliveredOn(Calendar.getInstance().getTime());
        repository.save(lesson);
        lesson = new Lesson();
        lesson.setId(6L);
        lesson.setTitle("Another Lesson");
        lesson.setDeliveredOn(Calendar.getInstance().getTime());
        repository.save(lesson);
    }

    @Test
    @Transactional
    @Rollback
    public void testDelete() throws Exception {
        Lesson lesson = new Lesson();
        lesson.setTitle("JPL");
        lesson.setDeliveredOn(Calendar.getInstance().getTime());
        lesson = repository.save(lesson);
        long count = repository.count();
        System.out.println("old count was "+count);
        MockHttpServletRequestBuilder request = delete("/lessons/"+lesson.getId());
        this.mvc.perform(request)
                .andExpect(status().isOk());
        long newCount = repository.count();
        System.out.println("new count was "+newCount);
        assertThat((count-1),equalTo(newCount));
    }

    @Test
    @Transactional
    @Rollback
    public void testCreate() throws Exception {
        MockHttpServletRequestBuilder request = post("/lessons")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\": \"Another Lesson\"}");

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", instanceOf(Number.class) ));
    }

    @Test
    @Transactional
    @Rollback
    public void testList() throws Exception {
        Lesson lesson = new Lesson();
        lesson.setId(6L);
        lesson.setTitle("Another Lesson");
        lesson.setDeliveredOn(Calendar.getInstance().getTime());
        lesson = repository.save(lesson);

        MockHttpServletRequestBuilder request = get("/lessons");

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", instanceOf(Number.class) ));
    }

    @Test
    @Transactional
    @Rollback
    public void testUpdate() throws Exception {
        Lesson lesson = new Lesson();
        lesson.setTitle("Another Lesson");
        lesson.setDeliveredOn(Calendar.getInstance().getTime());
        Lesson savedLesson = repository.save(lesson);

        MockHttpServletRequestBuilder request = patch("/lessons/"+savedLesson.getId());

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", not(savedLesson.getId())));
    }

    @Test
    @Transactional
    @Rollback
    public void testFindByTitle() throws Exception {
        Lesson lesson = new Lesson();
        lesson.setTitle("Another Lesson to find");
        lesson.setDeliveredOn(Calendar.getInstance().getTime());
        Lesson savedLesson = repository.save(lesson);

        MockHttpServletRequestBuilder request = get("/lessons/find/"+savedLesson.getTitle());

        this.mvc.perform(request)
                .andExpect(status().isOk());
//                .andExpect(jsonPath("$.data.lessons").isArray()) // works
//                .andExpect((jsonPath("$.data.lessons", Matchers.contains("Another Lesson to find"))));
//                .andExpect("$.data.lessons[?(@=='%s')]", lesson.getTitle()).exists());
    }
}
