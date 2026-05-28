package com.worknest.service;

import com.worknest.dto.EmployeeDto;
import com.worknest.entity.Department;
import com.worknest.entity.Employee;
import com.worknest.entity.User;
import com.worknest.repository.DepartmentRepository;
import com.worknest.repository.EmployeeRepository;
import com.worknest.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;

    @Override
    public Employee addEmployee(EmployeeDto dto) {

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Department department = departmentRepository.findById(dto.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found"));

        Employee manager = null;

        if (dto.getManagerId() != null) {
            manager = employeeRepository.findById(dto.getManagerId())
                    .orElseThrow(() -> new RuntimeException("Manager not found"));
        }

        Employee employee = Employee.builder()
                .employeeCode(dto.getEmployeeCode())
                .fullName(dto.getFullName())
                .phone(dto.getPhone())
                .designation(dto.getDesignation())
                .joiningDate(LocalDate.now())
                .salary(dto.getSalary())
                .status(dto.getStatus())
                .user(user)
                .department(department)
                .manager(manager)
                .build();

        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee getEmployeeById(Long id) {

        return employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
    }

    @Override
    public Employee updateEmployee(Long id, EmployeeDto dto) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        employee.setFullName(dto.getFullName());
        employee.setPhone(dto.getPhone());
        employee.setDesignation(dto.getDesignation());
        employee.setSalary(dto.getSalary());
        employee.setStatus(dto.getStatus());

        return employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployee(Long id) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        employeeRepository.delete(employee);
    }
}