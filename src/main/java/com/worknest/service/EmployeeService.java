package com.worknest.service;

import com.worknest.dto.EmployeeDto;
import com.worknest.entity.Employee;

import java.util.List;

public interface EmployeeService {

    Employee addEmployee(EmployeeDto employeeDto);

    List<Employee> getAllEmployees();

    Employee getEmployeeById(Long id);

    Employee updateEmployee(Long id, EmployeeDto employeeDto);

    void deleteEmployee(Long id);
}