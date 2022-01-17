package com.epam.rd.autotasks.springemployeecatalog.repository;

import com.epam.rd.autotasks.springemployeecatalog.domain.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployeeRepository {
    Page<Employee> findAllEmployee(Pageable pageable);

    Employee findById(Long id, boolean full_chain);

    Page<Employee> findEmployeesByManager(Long id, Pageable pageable);

    Page<Employee> findEmployeesByDepId(Long id, Pageable pageable);

    Page<Employee> findEmployeesByDepName(String value, Pageable pageable);
}