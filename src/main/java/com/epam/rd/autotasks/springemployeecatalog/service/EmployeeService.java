package com.epam.rd.autotasks.springemployeecatalog.service;

import com.epam.rd.autotasks.springemployeecatalog.constants.Constant;
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
        return employeeRepository.findAllEmployee(pageable);
    }

    public Employee getEmployeeById(String id, boolean full_chain) {
        return employeeRepository.findById(Long.parseLong(id), full_chain);
    }

    public Page<Employee> getEmployeesByManager(String id, Pageable pageable) {
        return employeeRepository.findEmployeesByManager(Long.parseLong(id), pageable);
    }

    public Page<Employee> getEmployeesByDepIdOrDepName(String value, Pageable pageable) {
        if (value.matches(Constant.REGEX_NUMBER)) {
            return employeeRepository.findEmployeesByDepId(Long.parseLong(value), pageable);
        } else {
            return employeeRepository.findEmployeesByDepName(value, pageable);
        }
    }
}