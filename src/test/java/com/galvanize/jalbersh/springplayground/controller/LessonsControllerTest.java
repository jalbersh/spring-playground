package com.galvanize.jalbersh.springplayground.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.galvanize.jalbersh.springplayground.model.Lesson;
import com.galvanize.jalbersh.springplayground.repository.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.hamcrest.core.IsNot.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@RunWith(SpringRunner.class)
//@AutoConfigureMockMvc(secure=false)
//@ContextConfiguration(locations={"classpath:app-context.xml"})
//@SpringBootTest
public class LessonsControllerTest {

    private LessonsController lessonsController;

    @Autowired
    MockMvc mvc;

    @Autowired
    LessonRepository repository;

//    @Before
    public void setup() throws Exception {
        lessonsController = new LessonsController(repository);
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

//    @Test
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

//    @Test
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

//    @Test
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

//    @Test
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

//    @Test
    @Transactional
    @Rollback
    public void testFindByTitle() throws Exception {
        Lesson lesson = new Lesson();
        lesson.setTitle("Another Lesson to find");
        lesson.setDeliveredOn(Calendar.getInstance().getTime());
        Lesson savedLesson = repository.save(lesson);

        MockHttpServletRequestBuilder request = get("/lessons/find/"+savedLesson.getTitle());

        MvcResult result = this.mvc.perform(request)
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        System.out.println("content="+content);
        ObjectMapper mapper = new ObjectMapper();
        List<Lesson> contentLessons = mapper.readValue(content,new TypeReference<List<Lesson>>(){});
        assertThat(contentLessons.isEmpty(), not(true));
        assertThat(contentLessons.size(), greaterThan(0));
        List<Lesson> onlyOurLesson = contentLessons.stream().filter(l -> l.getTitle().equals("Another Lesson to find")).collect(toList());
        assertThat(onlyOurLesson.isEmpty(), not(true));
    }

//    @Test
    @Transactional
    @Rollback
    public void testFindBetweenUsingRepository() throws Exception {
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

        List<Lesson> ls = repository.findByTitle("Another Lesson with date1");
        assertThat(ls.isEmpty(), not(true));
        assertThat(ls.get(0).getDeliveredOn(), equalTo(date1));
        System.out.println("date1="+date1);
        System.out.println("count="+repository.count());
        assertThat(repository.count(),greaterThan(1L));

        cal1.set(2018,6,1);
        date1 = cal1.getTime();
        cal2.set(2018,6,20);
        date2 = cal2.getTime();

        List<Lesson> lessons = repository.findAllDeliveredOnBetween(date1,date2);
        System.out.println("lessons from getBetween="+lessons);
        assertThat(lessons.isEmpty(), not(true));
        assertThat(lessons.size(), equalTo(2));
        assertThat(lessons.contains(savedLesson1), equalTo(true));
        assertThat(lessons.contains(savedLesson2), equalTo(true));
    }

//    @Test
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

        MockHttpServletRequestBuilder request = get("/lessons/between?date1=2018-06-01&date2=2018-06-20")
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = this.mvc.perform(request)
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.lessons").isArray())
//                .andExpect((jsonPath("$.lessons", Matchers.contains("Another Lesson to date1"))));
        String content = result.getResponse().getContentAsString();
        System.out.println("content="+content);
//        ObjectMapper mapper = new ObjectMapper();
//        List<Lesson> contentLessons = mapper.readValue(content,new TypeReference<List<Lesson>>(){});
//        assertThat(contentLessons.isEmpty(), not(true));
//        assertThat(contentLessons.size(), greaterThan(0));
//        List<Lesson> onlyOurLesson = contentLessons.stream().filter(l -> l.getTitle().equals("Another Lesson with date2")).collect(toList());
//        assertThat(onlyOurLesson.isEmpty(), not(true));
    }
}
