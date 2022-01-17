package com.epam.rd.autotasks.springemployeecatalog.controller;

import com.epam.rd.autotasks.springemployeecatalog.domain.Employee;
import com.epam.rd.autotasks.springemployeecatalog.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<Employee> getEmployees(Pageable pageable) {
        return employeeService.getEmployees(pageable);
    }

    @GetMapping(value = "/{ID}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Employee getEmployee(@PathVariable String ID, @RequestParam(required = false) boolean full_chain) {
        return employeeService.getEmployeeById(ID, full_chain);
    }

    @GetMapping(value = "/by_manager/{ID}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<Employee> getEmployeesByManager(@PathVariable String ID, Pageable pageable) {
        return employeeService.getEmployeesByManager(ID, pageable);
    }

    @GetMapping(value = "/by_department/{value}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<Employee> getEmployeesByDepIdOrDepName(@PathVariable String value, Pageable pageable) {
        return employeeService.getEmployeesByDepIdOrDepName(value, pageable);
    }
}