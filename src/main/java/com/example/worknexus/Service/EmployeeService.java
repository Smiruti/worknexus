package com.example.worknexus.Service;

import com.example.worknexus.Entity.Employee;
import com.example.worknexus.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee addEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee viewEmployeeById(Integer id) {
        return employeeRepository.findById(id).orElse(null);
    }
}
