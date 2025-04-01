package com.example.worknexus.Controller;

import com.example.worknexus.Entity.Employee;
import com.example.worknexus.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/all")
    public List<Employee> viewAll(){
        return employeeService.viewAll();
    }

    @DeleteMapping("/{id}")
    public String deleteEmployeeById(@PathVariable Integer id){
        employeeService.deleteEmployeeById(id);
        return "User id : "+id+" is deleted successfully";
    }

    @PutMapping("/update/{id}")
    public Employee updateEmployee(@PathVariable Integer id,@RequestBody Employee employee){
        return employeeService.updateEmployee(id, employee);
    }
}
