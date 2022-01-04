package com.epam.rd.autotasks.springemployeecatalog.rowmapper;

import com.epam.rd.autotasks.springemployeecatalog.constants.Constant;
import com.epam.rd.autotasks.springemployeecatalog.domain.Department;
import com.epam.rd.autotasks.springemployeecatalog.domain.Employee;

import com.epam.rd.autotasks.springemployeecatalog.service.ExtractorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static com.epam.rd.autotasks.springemployeecatalog.constants.Constant.DEPARTMENT;
import static com.epam.rd.autotasks.springemployeecatalog.constants.Constant.ID;

@Component
public class ManagerResultSetExtractor implements ResultSetExtractor<Map<Long, Employee>> {
    private final ExtractorService extractorService;
    private final JdbcTemplate jdbcTemplate;
    private final DepartmentResultSetExtractor departmentResultSetExtractor;

    @Autowired
    public ManagerResultSetExtractor(ExtractorService extractorService,
                                     JdbcTemplate jdbcTemplate,
                                     DepartmentResultSetExtractor departmentResultSetExtractor) {
        this.extractorService = extractorService;
        this.jdbcTemplate = jdbcTemplate;
        this.departmentResultSetExtractor = departmentResultSetExtractor;
    }

    @Override
    public Map<Long, Employee> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        Map<Long, Employee> managers = new HashMap<>();
        while (resultSet.next()) {
            Department department = getDepartments().get(resultSet.getInt(DEPARTMENT));
            managers.put(resultSet.getLong(ID), extractorService.getEmployee(resultSet, null, department));
        }
        return managers;
    }

    private Map<Integer, Department> getDepartments() {
        return jdbcTemplate.query(Constant.ALL_FROM_DEPARTMENT, departmentResultSetExtractor);
    }
}