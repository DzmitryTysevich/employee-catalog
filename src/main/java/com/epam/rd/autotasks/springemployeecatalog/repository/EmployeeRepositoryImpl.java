package com.epam.rd.autotasks.springemployeecatalog.repository;

import com.epam.rd.autotasks.springemployeecatalog.domain.Employee;
import com.epam.rd.autotasks.springemployeecatalog.extractor.EmployeeResultSetExtractorManagersManager;
import com.epam.rd.autotasks.springemployeecatalog.extractor.EmployeeResultSetExtractor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.epam.rd.autotasks.springemployeecatalog.constants.Constant.ID;
import static com.epam.rd.autotasks.springemployeecatalog.constants.SQLQuery.ALL_FROM_EMPLOYEE;
import static com.epam.rd.autotasks.springemployeecatalog.constants.SQLQuery.ALL_FROM_EMPLOYEE_FORMAT_QUERY;

@Repository
public class EmployeeRepositoryImpl implements EmployeeRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public EmployeeRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Employee> findAllEmployee(Pageable pageable) {
        String employeeFormatQuery = getEmployeeFormatQuery(pageable);
        return getPageableEmployees(pageable, getEmployees(employeeFormatQuery));
    }

    @Override
    public Optional<Employee> findByIdWIthManager(Long id) {
        List<Employee> employees = jdbcTemplate.query(
                getPreparedStatementCreator(ALL_FROM_EMPLOYEE), new EmployeeResultSetExtractor()
        );
        return getEmployeeFilteredById(employees, id);
    }

    @Override
    public Optional<Employee> findByIdWithManagersManager(Long id) {
        List<Employee> employees = jdbcTemplate.query(
                getPreparedStatementCreator(ALL_FROM_EMPLOYEE), new EmployeeResultSetExtractorManagersManager()
        );
        return getEmployeeFilteredById(employees, id);
    }

    @Override
    public List<Employee> findEmployeesByManager(Long managerId, Pageable pageable) {
        String employeeFormatQuery = getEmployeeFormatQuery(pageable);
        List<Employee> employees = getEmployees(employeeFormatQuery);
        return getPageableEmployees(pageable, getEmployeesFilteredByManager(employees, managerId));
    }

    @Override
    public List<Employee> findEmployeesByDepId(Long id, Pageable pageable) {
        String employeeFormatQuery = getEmployeeFormatQuery(pageable);
        List<Employee> employees = getEmployees(employeeFormatQuery);
        return getPageableEmployees(pageable, getEmployeesFilteredByDepId(employees, id));
    }

    @Override
    public List<Employee> findEmployeesByDepName(String depName, Pageable pageable) {
        String employeeFormatQuery = getEmployeeFormatQuery(pageable);
        List<Employee> employees = getEmployees(employeeFormatQuery);
        return getPageableEmployees(pageable, getEmployeesFilteredByDepName(employees, depName));
    }

    private Optional<Employee> getEmployeeFilteredById(List<Employee> employees, Long id) {
        return Optional.ofNullable(Objects.requireNonNull(employees).stream()
                .filter(employee -> Objects.equals(employee.getId(), id))
                .collect(Collectors.toList()).get(0));
    }

    private List<Employee> getEmployeesFilteredByManager(List<Employee> employees, Long managerId) {
        return employees.stream()
                .filter(employee -> employee.getManager() != null && Objects.equals(employee.getManager().getId(), managerId))
                .collect(Collectors.toList());
    }

    private List<Employee> getEmployeesFilteredByDepId(List<Employee> employees, Long depId) {
        return employees.stream()
                .filter(employee -> employee.getDepartment() != null && Objects.equals(employee.getDepartment().getId(), depId))
                .collect(Collectors.toList());
    }

    private List<Employee> getEmployeesFilteredByDepName(List<Employee> employees, String depName) {
        return employees.stream()
                .filter(employee -> employee.getDepartment() != null && Objects.equals(employee.getDepartment().getName(), depName))
                .collect(Collectors.toList());
    }

    private List<Employee> getEmployees(String query) {
        return jdbcTemplate.query(getPreparedStatementCreator(query), new EmployeeResultSetExtractor());
    }

    private List<Employee> getPageableEmployees(Pageable pageable, List<Employee> employees) {
        int fromIndex = getFromIndex(pageable);
        int toIndex = getToIndex(pageable, employees, fromIndex);
        if (fromIndex > toIndex) {
            return Collections.emptyList();
        } else {
            return Objects.requireNonNull(employees).subList(fromIndex, toIndex);
        }
    }

    private int getToIndex(Pageable pageable, List<Employee> employees, int fromIndex) {
        return Math.min(fromIndex + pageable.getPageSize(), Objects.requireNonNull(employees).size());
    }

    private int getFromIndex(Pageable pageable) {
        return pageable.getPageNumber() * pageable.getPageSize();
    }

    private String getEmployeeFormatQuery(Pageable pageable) {
        Order order = getOrder(pageable);
        return String.format(ALL_FROM_EMPLOYEE_FORMAT_QUERY, order.getProperty(), order.getDirection().name());
    }

    private PreparedStatementCreator getPreparedStatementCreator(String pageableQuery) {
        return connection -> connection.prepareStatement(pageableQuery, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
    }

    private Order getOrder(Pageable pageable) {
        return !pageable.getSort().isEmpty() ? pageable.getSort().toList().get(0) : Order.by(ID);
    }
}