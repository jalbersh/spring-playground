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
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
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

    private LessonsController lessonsController;

    @Autowired
    MockMvc mvc;

    @Autowired
    LessonRepository repository;

    @Before
    public void setup() throws Exception {
        lessonsController = new LessonsController((repository));
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
//                .andExpect(jsonPath("$.data.lessons").isArray()); // works
//                .andExpect((jsonPath("$.data.lessons", Matchers.contains("Another Lesson to find"))));
//                .andExpect("$.data.lessons[?(@=='%s')]", lesson.getTitle()).exists());
    }

    @Test
    @Transactional
    @Rollback
    public void testFindBetweenUsingController() throws Exception {
        Lesson lesson1 = new Lesson();
        lesson1.setTitle("Another Lesson with date1");
        Calendar cal1 = Calendar.getInstance();
        cal1.set(2018, 6, 2);
        Date date1 = cal1.getTime();
        lesson1.setDeliveredOn(date1);
        Lesson savedLesson1 = repository.save(lesson1);
        Lesson lesson2 = new Lesson();
        lesson2.setTitle("Another Lesson with date2");
        Calendar cal2 = Calendar.getInstance();
        cal2.set(2018, 6, 9);
        Date date2 = cal2.getTime();
        lesson2.setDeliveredOn(date2);
        Lesson savedLesson2 = repository.save(lesson2);

        List<Lesson> ls = repository.findByTitle("Another Lesson with date1").orElse(new ArrayList<Lesson>());
        assertThat(ls.isEmpty(), not(true));
        assertThat(ls.get(0).getDeliveredOn(), equalTo(date1));
        System.out.println("date1="+date1);
        assertThat(repository.count(),greaterThan(1L));

        List<Lesson> lessons = lessonsController.getBetween("2018-06-01", "2018-06-20");
        System.out.println("lessons from getBetween="+lessons);
//        assertThat(lessons.isEmpty(), not(true));
//        assertThat(lessons.size(), equalTo(2));
//        assertThat(lessons.contains(savedLesson1), equalTo(true));
//        assertThat(lessons.contains(savedLesson2), equalTo(true));
    }

    @Test
    @Transactional
    @Rollback
    public void testFindBetweenUsingEndpoint() throws Exception {
        Lesson lesson1 = new Lesson();
        lesson1.setTitle("Another Lesson with date1");
        Calendar cal1 = Calendar.getInstance();
        cal1.set(2018, 6, 2);
        Date date1 = cal1.getTime();
        lesson1.setDeliveredOn(date1);
        Lesson savedLesson1 = repository.save(lesson1);
        Lesson lesson2 = new Lesson();
        lesson2.setTitle("Another Lesson with date2");
        Calendar cal2 = Calendar.getInstance();
        cal2.set(2018, 6, 9);
        Date date2 = cal2.getTime();
        lesson2.setDeliveredOn(date2);
        Lesson savedLesson2 = repository.save(lesson2);

        MockHttpServletRequestBuilder request = get("/lessons/between?date1=2017-06-01&date2=2017-06-20")
                .contentType(MediaType.APPLICATION_JSON);
//                .accept(MediaType.ALL)
//                .headers(RequestMethod.GET)
        this.mvc.perform(request)
                .andExpect(status().isOk());
//                .andExpect(jsonPath("$.data.lessons").isArray())
//                .andExpect((jsonPath("$.data.lessons", Matchers.contains("Another Lesson to date"))));
    }
}
