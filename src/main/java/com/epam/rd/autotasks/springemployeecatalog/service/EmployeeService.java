package com.epam.rd.autotasks.springemployeecatalog.service;

import com.epam.rd.autotasks.springemployeecatalog.domain.Employee;
import com.epam.rd.autotasks.springemployeecatalog.repository.EmployeeRepository;
import com.epam.rd.autotasks.springemployeecatalog.repository.EmployeeRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepositoryImpl employeeDaoImpl) {
        this.employeeRepository = employeeDaoImpl;
    }

    public Page<Employee> getEmployees(Pageable pageable) {
        return employeeRepository.findAll(pageable);
    }
}