package com.epam.rd.autotasks.springemployeecatalog.rowmapper;

import com.epam.rd.autotasks.springemployeecatalog.domain.Employee;
import com.epam.rd.autotasks.springemployeecatalog.service.ExtractorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.epam.rd.autotasks.springemployeecatalog.constants.Constant.MANAGER;
import static com.epam.rd.autotasks.springemployeecatalog.constants.Constant.SELECT_ALL_FROM_EMPLOYEE;

@Component
public class EmployeeResultSetExtractor implements ResultSetExtractor<List<Employee>> {
    private final JdbcTemplate jdbcTemplate;
    private final ManagerResultSetExtractor managerResultSetExtractor;

    @Autowired
    public EmployeeResultSetExtractor(JdbcTemplate jdbcTemplate, ManagerResultSetExtractor managerResultSetExtractor) {
        this.jdbcTemplate = jdbcTemplate;
        this.managerResultSetExtractor = managerResultSetExtractor;
    }

    @Override
    public List<Employee> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        ExtractorService extractorService = new ExtractorService();
        List<Employee> employees = new LinkedList<>();
        while (resultSet.next()) {
            employees.add(extractorService.getEmployee(resultSet, getManagers().get(resultSet.getLong(MANAGER))));
        }
        return employees;
    }

    private Map<Long, Employee> getManagers() {
        return jdbcTemplate.query(SELECT_ALL_FROM_EMPLOYEE, managerResultSetExtractor);
    }
}