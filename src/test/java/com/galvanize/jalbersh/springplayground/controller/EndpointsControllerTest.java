package com.galvanize.jalbersh.springplayground.controller;

import com.galvanize.jalbersh.springplayground.controller.EndpointsController;
import com.galvanize.jalbersh.springplayground.model.OperationData;
import com.galvanize.jalbersh.springplayground.service.MathService;
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

    /*
    /flights/tickets/total
     */
//    @Test
    public void testCalculateTotalCost() throws Exception {
        this.mvc.perform(
                post("/flights/tickets/total")
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("Dwayne")))
                .andExpect(jsonPath("$.lastName", is("Johnson")));
    }

}