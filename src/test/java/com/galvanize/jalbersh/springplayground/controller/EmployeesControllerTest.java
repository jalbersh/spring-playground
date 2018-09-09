package com.galvanize.jalbersh.springplayground.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.galvanize.jalbersh.springplayground.config.SecurityConfig;
import com.galvanize.jalbersh.springplayground.model.Employee;
import com.galvanize.jalbersh.springplayground.model.Views;
import com.galvanize.jalbersh.springplayground.repository.EmployeeRepository;
import com.galvanize.jalbersh.springplayground.service.EmployeeDetailsService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.util.Base64Utils;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(EmployeesController.class)
@Import(SecurityConfig.class)
public class EmployeesControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    EmployeeRepository repository;

    @MockBean
    private EmployeeDetailsService service;

    private EmployeesController employeesController;

    @Before
    public void setup() {
        Employee employee = new Employee();
        employee.setName("Employee");
        employee.setSalary(24);
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(employee);
        when(service.getAllEmployees()).thenReturn(employeeList);
        when(repository.findAll()).thenReturn(employeeList);
        employeesController = new EmployeesController(service);
    }

    @Test
    public void okResponseWithBasicAuthCredentialsForKnownUser() throws Exception {
        this.mvc
                .perform(get("/employees").header(HttpHeaders.AUTHORIZATION,
                        "Basic " + Base64Utils.encodeToString("employee:my-employee-password".getBytes())))
                .andExpect(status().isOk());
    }

    @Test
    public void okResponseWithBasicAuthCredentialsForAdminOnly() throws Exception {
        ObjectMapper jmap = new ObjectMapper();
        Employee employee = new Employee();
        employee.setName("Employee");
        employee.setSalary(24);
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(employee);
        // Convert Array to JSON
        String json = jmap
                .writerWithView(Views.AdminView.class)
                .writeValueAsString(employeeList);
        MockHttpServletRequestBuilder request = get("/admin/employees")
                .header(HttpHeaders.AUTHORIZATION,
                        "Basic " + Base64Utils.encodeToString("boss:my-boss-password".getBytes()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].salary", is(24)))
                .andExpect(jsonPath("$.[0].name", is("Employee")));
    }

    @Test
    public void okResponseWithBasicAuthCredentialsForAdminOnlyToEmployees() throws Exception {
        ObjectMapper jmap = new ObjectMapper();
        Employee employee = new Employee();
        employee.setName("Employee");
        employee.setSalary(24);
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(employee);
        // Convert Array to JSON
        String json = jmap
                .writerWithView(Views.AdminView.class)
                .writeValueAsString(employeeList);
        MockHttpServletRequestBuilder request = get("/employees")
                .header(HttpHeaders.AUTHORIZATION,
                        "Basic " + Base64Utils.encodeToString("boss:my-boss-password".getBytes()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        MvcResult result = this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].name", is("Employee")))
                .andReturn();
        String content = result.getResponse().getContentAsString();
        System.out.println("content="+content);
        ObjectMapper mapper = new ObjectMapper();
        List<Employee> employees = mapper.readValue(content,new TypeReference<List<Employee>>(){});
        assertThat(employees.isEmpty(), not(true));
        assertThat(employees.size(), is(1));
        assertThat(employees.get(0).getSalary(), not(24));
        assertThat(employees.get(0).getSalary(), is(0));
    }

    @Test
    public void okResponseWithBasicAuthCredentialsForUsers() throws Exception {
        ObjectMapper jmap = new ObjectMapper();
        Employee employee = new Employee();
        employee.setName("Employee");
        employee.setSalary(24);
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(employee);
        // Convert Array to JSON
        String json = jmap
                .writerWithView(Views.UserView.class)
                .writeValueAsString(employeeList);
        MockHttpServletRequestBuilder request = get("/employees")
                .header(HttpHeaders.AUTHORIZATION,
                        "Basic " + Base64Utils.encodeToString("employee:my-employee-password".getBytes()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        MvcResult result = this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].name", is("Employee")))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        System.out.println("content="+content);
        ObjectMapper mapper = new ObjectMapper();
        List<Employee> employees = mapper.readValue(content,new TypeReference<List<Employee>>(){});
        assertThat(employees.isEmpty(), not(true));
        assertThat(employees.size(), is(1));
        assertThat(employees.get(0).getSalary(), not(24));
        assertThat(employees.get(0).getSalary(), is(0));
    }

    @Test
    public void unauthorizedResponseWithBasicAuthCredentialsForAdminOnly() throws Exception {
        this.mvc
                .perform(get("/admin/employees").header(HttpHeaders.AUTHORIZATION,
                        "Basic " + Base64Utils.encodeToString("employee:my-employee-password".getBytes())))
                .andExpect(status().isForbidden());
    }
}
