package com.galvanize.jalbersh.springplayground.service;

import com.galvanize.jalbersh.springplayground.controller.EndpointsController;
import com.galvanize.jalbersh.springplayground.controller.HelloController;
import com.galvanize.jalbersh.springplayground.model.OperationData;
import com.galvanize.jalbersh.springplayground.model.OperationDataBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(MathService.class)
public class MathServiceTest {
    MathService ms;

    @Before
    public void setup() throws Exception {
        ms = new MathService();
    }


    @Test
    public void testCalculate() throws Exception {
        OperationData data = new OperationDataBuilder().operation("add").x("4").y("5").build();
        assert(ms.calculate(data)).equals("9.0\n");
    }

    @Test
    public void testSum() throws Exception {
        String[] ns = {"3","5","7"};
        assert(ms.sum(ns)).equals("15.0\n");
    }

    @Test
    public void testVolume() throws Exception {
        assert(ms.volume(3,4,5)).equals("60.0\n");
    }

    @Test
    public void testAreaRectangle() throws Exception {
        assert(ms.area("rectangle", 4, 5, 0).equals("20"));
    }

    @Test
    public void testAreaCircle() throws Exception {
        assert(ms.area("circle", 0, 0, 3).equals(String.valueOf(Math.PI*Math.pow(3.0,2.0))));
    }

    @Test
    public void testAreaInvalidRectangle() throws Exception {
        assert(ms.area("rectangle", 0, 0, 3).equals("Invalid"));
    }

    @Test
    public void testAreaInvalidCircle() throws Exception {
        assert(ms.area("circle", 3, 4, 5).equals("Invalid"));
    }}