package com.galvanize.jalbersh.springplayground.controller;

import com.galvanize.jalbersh.springplayground.config.SecurityConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.Base64Utils;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(EmployeesController.class)
@Import(SecurityConfig.class)
public class EmployeesControllerTest {

    @Autowired
    MockMvc mvc;

    @Test
    public void okResponseWithBasicAuthCredentialsForKnownUser() throws Exception {
        this.mvc
                .perform(get("/employees").header(HttpHeaders.AUTHORIZATION,
                        "Basic " + Base64Utils.encodeToString("employee:my-employee-password".getBytes())))
                .andExpect(status().isOk());
    }

}
