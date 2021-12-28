package com.epam.rd.autotasks.springemployeecatalog.repository;

import com.epam.rd.autotasks.springemployeecatalog.domain.Employee;
import com.epam.rd.autotasks.springemployeecatalog.rowmapper.EmployeeRowMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeRepositoryImpl implements EmployeeRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public EmployeeRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Page<Employee> findAll(Pageable pageable) {
        int count = countRows();
        Order order = getOrder(pageable);

        String query = "SELECT * FROM employee e "
                + "LEFT JOIN department d ON e.department = d.id "
                + "ORDER BY " + order.getProperty() + " " + order.getDirection().name()
                + " LIMIT " + pageable.getPageSize()
                + " OFFSET " + pageable.getOffset();
        List<Employee> employees = jdbcTemplate.query(query, new EmployeeRowMapper());
        return new PageImpl<>(employees, pageable, count);
    }

    private Order getOrder(Pageable pageable) {
        return !pageable.getSort().isEmpty() ? pageable.getSort().toList().get(0) : Order.by("id");
    }

    private int countRows() {
        String rowCountSql = "select count(*) from employee";
        return jdbcTemplate.queryForObject(rowCountSql, Integer.class);
    }
}