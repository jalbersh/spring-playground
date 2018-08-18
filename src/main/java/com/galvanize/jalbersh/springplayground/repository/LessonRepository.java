package com.galvanize.jalbersh.springplayground.repository;

import com.galvanize.jalbersh.springplayground.model.Lesson;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface LessonRepository extends CrudRepository<Lesson, Long> {
    public Optional<List<Lesson>> findByTitle(String title);
    @Query("select l FROM Lesson l where l.deliveredOn >= :date1 AND l.deliveredOn <= :date2")
    public Optional<List<Lesson>> findAllDeliveredOnBetween(@Param("date1") Date date1, @Param("date2") Date date2);
}

