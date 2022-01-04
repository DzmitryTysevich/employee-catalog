package com.epam.rd.autotasks.springemployeecatalog.repository;

import com.epam.rd.autotasks.springemployeecatalog.domain.Employee;
import com.epam.rd.autotasks.springemployeecatalog.page.JsonPage;
import com.epam.rd.autotasks.springemployeecatalog.rowmapper.EmployeeResultSetExtractor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

import static com.epam.rd.autotasks.springemployeecatalog.constants.Constant.*;

@Repository
public class EmployeeRepositoryImpl implements EmployeeRepository {
    private final JdbcTemplate jdbcTemplate;
    private final EmployeeResultSetExtractor employeeResultSetExtractor;

    @Autowired
    public EmployeeRepositoryImpl(JdbcTemplate jdbcTemplate, EmployeeResultSetExtractor employeeResultSetExtractor) {
        this.jdbcTemplate = jdbcTemplate;
        this.employeeResultSetExtractor = employeeResultSetExtractor;
    }

    @Override
    public Page<Employee> findAll(Pageable pageable) {
        Order order = getOrder(pageable);
        String query = String.format(PAGEABLE_FORMAT_QUERY,
                order.getProperty(), order.getDirection().name(), pageable.getPageSize(), pageable.getOffset()
        );
        List<Employee> employees = jdbcTemplate.query(query, employeeResultSetExtractor);
        return new JsonPage<>(Objects.requireNonNull(employees), pageable, countRows());
    }

    private Order getOrder(Pageable pageable) {
        return !pageable.getSort().isEmpty() ? pageable.getSort().toList().get(0) : Order.by(ID);
    }

    private int countRows() {
        return Objects.requireNonNull(jdbcTemplate.queryForObject(COUNT_EMPLOYEE, Integer.class));
    }
}