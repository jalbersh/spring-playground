package com.galvanize.jalbersh.springplayground.controller;

import com.galvanize.jalbersh.springplayground.model.Lesson;
import com.galvanize.jalbersh.springplayground.repository.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Calendar;
import java.util.Optional;

import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping("/lessons")
public class LessonsController {

    private final LessonRepository repository;

    @Autowired
    public LessonsController(LessonRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(value = "", method = GET)
    public Iterable<Lesson> all() {
        return this.repository.findAll();
    }

    @RequestMapping(value = "/5", method = GET)
    public Optional<Lesson> get5() {
        return this.repository.findById(5L);
    }

    @RequestMapping(value = "/5", method = DELETE)
    public void delete5() { this.repository.deleteById(5L); }

    @RequestMapping(value = "", method = POST, consumes = "application/json")
    public Lesson create(@RequestBody Lesson lesson) {
        return this.repository.save(lesson);
    }

    @RequestMapping(value = "/5", method = POST)
    public Lesson create5() {
        Lesson lesson = new Lesson();
        lesson.setId(5L);
        lesson.setTitle("JPL");
        lesson.setDeliveredOn(Calendar.getInstance().getTime());
        return this.repository.save(lesson);
    }

    @RequestMapping(value = "/{id}", method = GET)
    public Lesson getId(@PathVariable long id) {
        Lesson lesson = new Lesson();
        lesson.setId(id);
        lesson.setTitle("JPL");
        lesson.setDeliveredOn(Calendar.getInstance().getTime());
        return this.repository.save(lesson);
    }

    @RequestMapping(value = "/{id}", method = POST)
    public Lesson createId(@PathVariable long id) {
        Lesson lesson = new Lesson();
        lesson.setId(id);
        lesson.setTitle("JPL");
        lesson.setDeliveredOn(Calendar.getInstance().getTime());
        return this.repository.save(lesson);
    }
}

/*
insert into lessons (title, delivered_on) values ('Requests and Responses', '2017-02-05');
insert into lessons (title, delivered_on) values ('SQL', '2017-04-06');
insert into lessons (title, delivered_on) values ('Bellman Ford', '2017-05-02');
insert into lessons (title, delivered_on) values ('Some Other Lesson', '2017-05-02');
insert into lessons (title, delivered_on) values ('JPA', '2017-05-02');
 */