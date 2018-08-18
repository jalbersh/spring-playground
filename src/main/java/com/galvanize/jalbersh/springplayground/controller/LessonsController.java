package com.galvanize.jalbersh.springplayground.controller;

import com.galvanize.jalbersh.springplayground.model.Lesson;
import com.galvanize.jalbersh.springplayground.repository.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping("/lessons")
public class LessonsController {

    private final LessonRepository repository;

    @Autowired
    public LessonsController(LessonRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(value = "/{id}", method = DELETE)
    public void delete(@PathVariable long id) {
        System.out.println("delete path param id="+id);
        this.repository.deleteById(id);
    }

    @RequestMapping(value = "", method = POST, consumes = "application/json", produces = "application/json")
    public Lesson create(@RequestBody Lesson lesson) {
        return this.repository.save(lesson);
    }

    @RequestMapping(value = "/5", method = POST, produces = "application/json")
    public Lesson create5() {
        Lesson lesson = new Lesson();
        lesson.setId(5L);
        lesson.setTitle("JPL");
        lesson.setDeliveredOn(Calendar.getInstance().getTime());
        return this.repository.save(lesson);
    }

    @RequestMapping(value = "/between?{date1}&{date2}", method = RequestMethod.GET,
            consumes = "application/json", produces = "application/json")
    public List<Lesson> getBetween(@PathVariable String date1str, @PathVariable String date2str) throws Exception {
        System.out.println("in getBetween: got date1str="+date1str+" and date2str="+date2str);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd");
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = sdf.parse(date1str);
            date2 = sdf.parse(date2str);
        } catch (ParseException pe) {
            System.out.println("ParseException caught: "+pe.getMessage());
            return null;
        } catch (NumberFormatException nfe) {
            System.out.println("ParseException caught: "+nfe.getMessage());
            return null;
        }

        System.out.println("calling repository.findAllDeliveredOnBetween with date1="+date1+" and date2="+date2);
        List<Lesson> lessons = this.repository.findAllDeliveredOnBetween(date1,date2).orElse(new ArrayList<Lesson>());
        System.out.println("lessons="+lessons);
        return lessons;
    }

    @RequestMapping(value = "/{id}", method = POST, produces = "application/json")
    public Lesson createId(@PathVariable long id) {
        Lesson lesson = new Lesson();
        lesson.setId(id);
        lesson.setTitle("JPL");
        lesson.setDeliveredOn(Calendar.getInstance().getTime());
        return this.repository.save(lesson);
    }

    @RequestMapping(value = "/{id}", method = PATCH, produces = "application/json")
    public Lesson updateId(@PathVariable long id) {
        System.out.println("path param id="+id);
        Lesson lesson = this.repository.findById(id).orElse(new Lesson(id,"JPL",Calendar.getInstance().getTime()));
        this.repository.save(lesson);
        lesson.setId(5L);
        return this.repository.save(lesson);
    }

    @RequestMapping(value = "/count", method = GET, produces = "application/json")
    public long getCount() {
        System.out.println("in getCount");
        return this.repository.count();
    }

    @RequestMapping(value = "", method = GET, produces = "application/json")
    public Iterable<Lesson> all() {
        System.out.println("in GET /lessons");
        return this.repository.findAll();
    }

    @RequestMapping(value = "/find/{title}", method = GET, produces = "application/json")
    public List<Lesson> getByTitle(@PathVariable String title) {
        List<Lesson> lessons = repository.findByTitle(title).orElse(null);
        System.out.println("lessons="+lessons);
        return lessons;
    }

//    @RequestMapping(value = "/5", method = GET, produces = "application/json")
//    public Optional<Lesson> get5() {
//        System.out.println("in GET /lessons/5");
//        return this.repository.findById(5L);
//    }

//    @RequestMapping(value = "/{id}", method = GET, produces = "application/json")
//    public Lesson getById(@PathVariable long id) {
//        Lesson lesson = new Lesson();
//        lesson.setId(id);
//        lesson.setTitle("JPL");
//        lesson.setDeliveredOn(Calendar.getInstance().getTime());
//        return this.repository.save(lesson);
//    }

}

/*
insert into lessons (title, delivered_on) values ('Requests and Responses', '2017-02-05');
insert into lessons (title, delivered_on) values ('SQL', '2017-04-06');
insert into lessons (title, delivered_on) values ('Bellman Ford', '2017-05-02');
insert into lessons (title, delivered_on) values ('Some Other Lesson', '2017-05-02');
insert into lessons (title, delivered_on) values ('JPA', '2017-05-02');
 */