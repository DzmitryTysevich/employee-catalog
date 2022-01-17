package com.epam.rd.autotasks.springemployeecatalog.repository;

import com.epam.rd.autotasks.springemployeecatalog.domain.Employee;
import com.epam.rd.autotasks.springemployeecatalog.page.JsonPage;
import com.epam.rd.autotasks.springemployeecatalog.extractor.EmployeeResultSetExtractor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

import static com.epam.rd.autotasks.springemployeecatalog.constants.Constant.ID;
import static com.epam.rd.autotasks.springemployeecatalog.constants.SQLQuery.ALL_EMPLOYEE_FORMAT_QUERY;
import static com.epam.rd.autotasks.springemployeecatalog.constants.SQLQuery.COUNT_EMPLOYEE;
import static com.epam.rd.autotasks.springemployeecatalog.constants.SQLQuery.EMPLOYEE_BY_DEPARTMENT_FORMAT_QUERY;
import static com.epam.rd.autotasks.springemployeecatalog.constants.SQLQuery.EMPLOYEE_BY_DEPARTMENT_NAME_FORMAT_QUERY;
import static com.epam.rd.autotasks.springemployeecatalog.constants.SQLQuery.EMPLOYEE_BY_ID_FORMAT_QUERY;
import static com.epam.rd.autotasks.springemployeecatalog.constants.SQLQuery.EMPLOYEE_BY_MANAGER_FORMAT_QUERY;

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
    public Page<Employee> findAllEmployee(Pageable pageable) {
        Order order = getOrder(pageable);
        String pageableQuery = String.format(
                ALL_EMPLOYEE_FORMAT_QUERY, order.getProperty(), order.getDirection().name(), pageable.getPageSize(), pageable.getOffset()
        );
        return new JsonPage<>(Objects.requireNonNull(getEmployeesList(pageableQuery)), pageable, countRows());
    }

    @Override
    public Employee findById(Long id, boolean full_chain) {
        String queryByIdFormat = String.format(EMPLOYEE_BY_ID_FORMAT_QUERY, id);
        if (full_chain) {
            return null;
        } else return Objects.requireNonNull(jdbcTemplate.query(queryByIdFormat, employeeResultSetExtractor)).get(0);
    }

    @Override
    public Page<Employee> findEmployeesByManager(Long id, Pageable pageable) {
        Order order = getOrder(pageable);
        String pageableQuery = String.format(EMPLOYEE_BY_MANAGER_FORMAT_QUERY,
                id, order.getProperty(), order.getDirection().name(), pageable.getPageSize(), pageable.getOffset());
        return new JsonPage<>(Objects.requireNonNull(getEmployeesList(pageableQuery)), pageable, countRows());
    }

    @Override
    public Page<Employee> findEmployeesByDepId(Long id, Pageable pageable) {
        Order order = getOrder(pageable);
        String pageableQuery = String.format(EMPLOYEE_BY_DEPARTMENT_FORMAT_QUERY,
                id, order.getProperty(), order.getDirection().name(), pageable.getPageSize(), pageable.getOffset());
        return new JsonPage<>(Objects.requireNonNull(getEmployeesList(pageableQuery)), pageable, countRows());
    }

    @Override
    public Page<Employee> findEmployeesByDepName(String depName, Pageable pageable) {
        Order order = getOrder(pageable);
        String pageableQuery = String.format(EMPLOYEE_BY_DEPARTMENT_NAME_FORMAT_QUERY,
                depName, order.getProperty(), order.getDirection().name(), pageable.getPageSize(), pageable.getOffset());
        return new JsonPage<>(Objects.requireNonNull(getEmployeesList(pageableQuery)), pageable, countRows());
    }

    private List<Employee> getEmployeesList(String pageableQuery) {
        return jdbcTemplate.query(pageableQuery, employeeResultSetExtractor);
    }

    private Order getOrder(Pageable pageable) {
        return !pageable.getSort().isEmpty() ? pageable.getSort().toList().get(0) : Order.by(ID);
    }

    private int countRows() {
        return Objects.requireNonNull(jdbcTemplate.queryForObject(COUNT_EMPLOYEE, Integer.class));
    }
}