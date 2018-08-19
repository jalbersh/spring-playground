package com.galvanize.jalbersh.springplayground.repository;

import com.galvanize.jalbersh.springplayground.model.Lesson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LessonRepositoryTest {

    @Autowired
    LessonRepository repository;

    @Before
    public void setup() throws Exception {
    }

    @Test
    @Transactional
    @Rollback
    public void testFindByTitle() throws Exception {
        Lesson lesson = new Lesson();
        lesson.setId(5L);
        lesson.setTitle("SQL");
        Calendar cal = Calendar.getInstance();
        cal.set(2017,5,2);
        Date date = cal.getTime();
        lesson.setDeliveredOn(date);
        repository.save(lesson);
        List<Lesson> lessons = repository.findByTitle("SQL");
        assertThat(lessons.isEmpty(),not(true));
        System.out.println("got lessons="+lessons);
        assertThat(lessons.isEmpty(), not(true));
        assertThat(lessons.size(), greaterThan(0));
        assertThat(lessons.get(0).getTitle(), equalTo("SQL"));
    }

    @Test
    @Transactional
    @Rollback
    public void testFindBetween() throws Exception {
        Calendar cal1 = Calendar.getInstance();
        cal1.set(2017,5,1);
        Date date1 = cal1.getTime();
        Calendar cal2 = Calendar.getInstance();
        cal2.set(2017,5,9);
        Date date2 = cal2.getTime();

        Calendar cal = Calendar.getInstance();
        cal.set(2017,5,2);
        Date date = cal.getTime();
        Lesson lesson = new Lesson();
        lesson.setId(5L);
        lesson.setTitle("SQL");
        lesson.setDeliveredOn(date);
        repository.save(lesson);

        List<Lesson> lessons = repository.findAllDeliveredOnBetween(date1,date2);
        assertThat(lessons.isEmpty(),not(true));
        assertThat(lessons.isEmpty(), not(true));
        assertThat(lessons.size(), greaterThan(0));
        Date actualDate = lessons.get(0).getDeliveredOn();
        assertThat(actualDate.getMonth(), equalTo(date.getMonth()));
        assertThat(actualDate.getDay(), equalTo(date.getDay()));
        assertThat(actualDate.getYear(), equalTo(date.getYear()));
        assertThat(actualDate, equalTo(date));
    }
}

