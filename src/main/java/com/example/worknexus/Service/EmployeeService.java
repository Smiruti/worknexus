package com.example.worknexus.Service;

import com.example.worknexus.Entity.Employee;
import com.example.worknexus.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public List<Employee> viewAll(){
        return employeeRepository.findAll();
    }

    public void deleteEmployeeById(Integer id) {
        employeeRepository.deleteById(id);
    }

    public Employee updateEmployee(Integer id, Employee updatedEmployee) {
        Optional<Employee> eList = employeeRepository.findById(id);
        if (eList.isPresent()){
            Employee emp = eList.get();
            emp.setName(updatedEmployee.getName());
            emp.setEmail(updatedEmployee.getEmail());
            emp.setPassword(updatedEmployee.getPassword());
            return employeeRepository.save(emp);
        }
        else {
            return null;
        }
    }
}
