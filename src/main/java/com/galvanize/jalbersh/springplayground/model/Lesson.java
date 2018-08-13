package com.galvanize.jalbersh.springplayground.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;

import java.util.Date;

@Entity
@Table(name = "lessons")
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Column(columnDefinition = "date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date deliveredOn;

    public Lesson() {
    }

    public Lesson(Long id, String title, Date deliveredOn) {
        this.id = id;
        this.title = title;
        this.deliveredOn = deliveredOn;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDeliveredOn() {
        return deliveredOn;
    }

    public void setDeliveredOn(Date deliveredOn) {
        this.deliveredOn = deliveredOn;
    }
}

