package com.galvanize.jalbersh.springplayground.service;

import com.galvanize.jalbersh.springplayground.model.Employee;
import com.galvanize.jalbersh.springplayground.repository.EmployeeRepository;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeDetailsService implements UserDetailsService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public EmployeeDetailsService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAllEmployees() {
        return Lists.newArrayList(employeeRepository.findAll());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee employee = employeeRepository.findByUsername(username);
        if (employee == null) throw new UsernameNotFoundException("Username " + username + " not found");
        return employee;
    }

    public UserDetails loadUserById(Long userId) throws UsernameNotFoundException {
        Employee employee = employeeRepository.findById(userId).orElse(null);
        if (employee == null) throw new UsernameNotFoundException("UserId " + userId + " not found");
        return employee;
    }
}
