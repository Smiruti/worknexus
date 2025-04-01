package com.example.worknexus.Controller;

import com.example.worknexus.Entity.Employee;
import com.example.worknexus.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/add")
    public Employee addEmployee(@RequestBody Employee employee){
        return employeeService.addEmployee(employee);
    }

    @GetMapping("/{id}")
    public Employee viewEmployeeById (@PathVariable Integer id){
        return employeeService.viewEmployeeById(id);
    }
}
