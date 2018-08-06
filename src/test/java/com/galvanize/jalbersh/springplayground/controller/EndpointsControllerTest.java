package com.galvanize.jalbersh.springplayground.controller;

import com.fasterxml.classmate.util.ResolvedTypeCache;
import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.galvanize.jalbersh.springplayground.controller.EndpointsController;
import com.galvanize.jalbersh.springplayground.model.OperationData;
import com.galvanize.jalbersh.springplayground.model.Passenger;
import com.galvanize.jalbersh.springplayground.model.Ticket;
import com.galvanize.jalbersh.springplayground.service.MathService;
import com.galvanize.jalbersh.springplayground.service.TicketService;
import org.assertj.core.internal.bytebuddy.matcher.ElementMatcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.expression.Operation;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(EndpointsController.class)
@ContextConfiguration(locations={"classpath:app-context.xml"})
public class EndpointsControllerTest {

    private EndpointsController ec;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private MathService ms;

    @Autowired
    private TicketService ts;

    @Before
    public void setup() throws Exception {
        ec = new EndpointsController();
    }

    @Test
    public void testGetEndpoint() throws Exception {
        this.mvc.perform(get("/math/tasks").accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().string("These are tasks\n"));
    }

    @Test
    public void testPostEndpoint() throws Exception {
        this.mvc.perform(post("/math/tasks").accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().string("You just POSTed to /tasks\n"));
    }

    @Test
    public void testGetPi() throws Exception {
        this.mvc.perform(get("/math/pi").accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().string("3.141592653589793\n"));
    }

    @Test
    public void testMathCalculate() throws Exception {
        this.mvc.perform(get("/math/calculate?operation=add&x=2&y=3").accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().string("5.0\n"));
    }

    @Test
    public void testMathCalculateAdd() throws Exception {
        this.mvc.perform(get("/math/calculate?operation=add&x=4&y=6").accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().string("10.0\n"));
    }

    @Test
    public void testMathCalculateMultiple() throws Exception {
        this.mvc.perform(get("/math/calculate?operation=multiply&x=4&y=6").accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().string("24.0\n"));
    }

    @Test
    public void testMathCalculateSubtract() throws Exception {
        this.mvc.perform(get("/math/calculate?operation=subtract&x=4&y=6").accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().string("-2.0\n"));
    }

    @Test
    public void testMathCalculateDivide() throws Exception {
        this.mvc.perform(get("/math/calculate?operation=divide&x=30&y=5").accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().string("6.0\n"));
    }

    @Test
    public void testMathCalculateNoOperation() throws Exception {
        this.mvc.perform(get("/math/calculate?x=30&y=5").accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().string("35.0\n"));
    }

    @Test
    public void testMathSum() throws Exception {
        this.mvc.perform(post("/math/sum?n=3&n=5&n=7").accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().string("15.0\n"));
    }

    @Test
    public void testMathVolume() throws Exception {
        this.mvc.perform(get("/math/volume/3/4/5").accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().string("60.0\n"));
    }

//    @Test
    public void testMathAreaCircleWithPlainFormData() throws Exception {
        MockHttpServletRequestBuilder request1 = post("/math/area")
                .accept(MediaType.TEXT_PLAIN)
                .param("type", "rectangle")
                .param("width", "3")
                .param("height", "4");
        this.mvc.perform(request1)
                .andExpect(status().isOk())
                .andExpect(content().string("12"));
    }

//    @Test
    public void testMathAreaCircleWithFormData() throws Exception {
        MockHttpServletRequestBuilder request1 = post("/math/area")
                .accept(MediaType.APPLICATION_FORM_URLENCODED)
                .param("type", "circle")
                .param("width", "3")
                .param("height", "4");
        this.mvc.perform(request1)
                .andExpect(status().isOk())
                .andExpect(content().string(Double.toString(Math.PI*Math.pow(4.0,2.0))));
    }

//    @Test
    public void testMathAreaRectangleWithFormData() throws Exception {
        MockHttpServletRequestBuilder request1 = post("/math/area")
                .accept(MediaType.APPLICATION_FORM_URLENCODED)
                .param("type", "circle")
                .param("width", "3")
                .param("height", "4");
        this.mvc.perform(request1)
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(Math.PI*Math.pow(4.0,2.0))));

    }

    @Test
    public void testSingleFlight() throws Exception {
        this.mvc.perform(
                get("/flights/flight")
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.tickets[0].passenger.FirstName", is("Dwayne")))
                .andExpect(jsonPath("$.tickets[0].passenger.LastName", is("Johnson")));
    }

    @Test
    public void testFlight1() throws Exception {
        this.mvc.perform(
                get("/flights/flight")
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.tickets[0].passenger.FirstName", is("Dwayne")))
                .andExpect(jsonPath("$.tickets[0].passenger.LastName", is("Johnson")));
    }

    @Test
    public void testFlight2() throws Exception {
        this.mvc.perform(
                get("/flights/flight")
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.tickets[0].passenger.FirstName", is("Dwayne")));
    }

    @Test
    public void testFlights() throws Exception {
        this.mvc.perform(
                get("/flights")
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.[0].tickets[0].passenger.FirstName", is("Dwayne")))
                .andExpect(jsonPath("$.[1].tickets[1].passenger.FirstName", is("John")))
                .andExpect(jsonPath("$.[1].tickets[2].passenger.FirstName", is("The Rock")));
    }

    @Test
    public void testSumTicketsJsonAsString() throws Exception {
        String json = "[{\"passenger\": {\"FirstName\": \"Some name\", \"LastName\": \"Some other name\"}, \"price\": 200}, {\"passenger\": {\"FirstName\": \"Name B\", \"LastName\": \"Name C\"}, \"price\": 150}]";
        MockHttpServletRequestBuilder request = post("/flights/tickets/total")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string("result: 350.0"));
    }

    @Test
    public void testSumTicketsFromFile() throws Exception {
//        String json = "[{\"passenger\": {\"FirstName\": \"Some name\", \"LastName\": \"Some other name\"}, \"price\": 200}, {\"passenger\": {\"FirstName\": \"Name B\", \"LastName\": \"Name C\"}, \"price\": 150}]";
        String json = getJSON("/data.json");
        MockHttpServletRequestBuilder request = post("/flights/tickets/total")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string("result: 600.0"));
    }

    private String getJSON(String path) throws Exception {
        URL url = this.getClass().getResource(path);
        return new String(Files.readAllBytes(Paths.get(url.getFile())));
    }

    @Test
    public void testSumTicketsFromJacksonObjectToString() throws Exception {
        ObjectMapper jmap = new ObjectMapper();
        Passenger passenger1 = new Passenger("Dwayne","Johnson");
        Passenger passenger2 = new Passenger("John","Cena");
        Passenger passenger3 = new Passenger("The Rock",null);
        Ticket ticket1 = new Ticket(passenger1,200.0);
        Ticket ticket2 = new Ticket(passenger2,180.0);
        Ticket ticket3 = new Ticket(passenger3,160.0);
        Ticket[] tickets = Stream.of(ticket1,ticket2,ticket3).toArray(Ticket[]::new);
        // Convert Array to JSON
        String json = jmap.writeValueAsString(tickets);
//        json from map=[{"passenger":{"FirstName":"Dwayne","LastName":"Johnson"},"price":200.0},{"passenger":{"FirstName":"John","LastName":"Cena"},"price":180.0},{"passenger":{"FirstName":"The Rock"},"price":160.0}]
        MockHttpServletRequestBuilder request = post("/flights/tickets/total")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string("result: 540.0"));
    }

    @Test
    public void testObjectParams() throws Exception {
        MockHttpServletRequestBuilder request = post("/jr/object-example")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"q\": \"other\", \"from\": \"2010\"}");

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string("Search: q=something from=2008"));
    }
}