package com.galvanize.jalbersh.springplayground.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.galvanize.jalbersh.springplayground.model.Employee;
import com.galvanize.jalbersh.springplayground.model.Views;
import com.galvanize.jalbersh.springplayground.service.EmployeeDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EmployeesController {

    @Autowired
    private EmployeeDetailsService es;

    public EmployeesController(EmployeeDetailsService es) {
        this.es = es;
    }

//    @GetMapping("/employees")
////    public String getEmployees() {
////        return "Super secret list of employees";
////    }

    @GetMapping("/employees")
    public String getAllEmployeesNoSalary() {
        List<Employee> employees = es.getAllEmployees();
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper
                    .writerWithView(Views.UserView.class)
                    .writeValueAsString(employees);

        } catch (JsonProcessingException e) {
            System.out.println("Error parsing json: "+e.getMessage());
            return "";
        }
    }

    @GetMapping("/admin/employees")
    public String getAllEmployees() {
        List<Employee> employees = es.getAllEmployees();
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper
                    .writerWithView(Views.AdminView.class)
                    .writeValueAsString(employees);

        } catch (JsonProcessingException e) {
            System.out.println("Error parsing json: "+e.getMessage());
            return "";
        }
    }

    @GetMapping("/me")
    public Employee getMe(@AuthenticationPrincipal Employee employee) {
        return employee;
    }}