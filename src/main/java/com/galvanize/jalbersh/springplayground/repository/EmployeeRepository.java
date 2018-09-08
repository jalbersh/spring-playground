package com.galvanize.jalbersh.springplayground.repository;

import com.galvanize.jalbersh.springplayground.model.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {
}
