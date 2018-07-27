package com.galvanize.jalbersh.springplayground.controller;

import com.galvanize.jalbersh.springplayground.controller.EndpointsController;
import com.galvanize.jalbersh.springplayground.model.OperationData;
import com.galvanize.jalbersh.springplayground.service.MathService;
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

import static com.galvanize.jalbersh.springplayground.model.OperationDataBuilder.operationDataBuilder;
import static org.mockito.ArgumentMatchers.any;
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
}