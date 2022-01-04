package com.epam.rd.autotasks.springemployeecatalog.rowmapper;

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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.epam.rd.autotasks.springemployeecatalog.constants.Constant.ALL_FROM_DEPARTMENT;
import static com.epam.rd.autotasks.springemployeecatalog.constants.Constant.ALL_FROM_EMPLOYEE;
import static com.epam.rd.autotasks.springemployeecatalog.constants.Constant.DEPARTMENT;
import static com.epam.rd.autotasks.springemployeecatalog.constants.Constant.MANAGER;

@Component
public class EmployeeResultSetExtractor implements ResultSetExtractor<List<Employee>> {
    private final JdbcTemplate jdbcTemplate;
    private final ManagerResultSetExtractor managerResultSetExtractor;
    private final DepartmentResultSetExtractor departmentResultSetExtractor;

    @Autowired
    public EmployeeResultSetExtractor(JdbcTemplate jdbcTemplate,
                                      ManagerResultSetExtractor managerResultSetExtractor,
                                      DepartmentResultSetExtractor departmentResultSetExtractor) {
        this.jdbcTemplate = jdbcTemplate;
        this.managerResultSetExtractor = managerResultSetExtractor;
        this.departmentResultSetExtractor = departmentResultSetExtractor;
    }

    @Override
    public List<Employee> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        ExtractorService extractorService = new ExtractorService();
        List<Employee> employees = new LinkedList<>();
        while (resultSet.next()) {
            Employee manager = getManagers().get(resultSet.getLong(MANAGER));
            Department department = getDepartments().get(resultSet.getInt(DEPARTMENT));
            employees.add(extractorService.getEmployee(resultSet, manager, department));
        }
        return employees;
    }

    private Map<Long, Employee> getManagers() {
        return jdbcTemplate.query(ALL_FROM_EMPLOYEE, managerResultSetExtractor);
    }

    private Map<Integer, Department> getDepartments() {
        return jdbcTemplate.query(ALL_FROM_DEPARTMENT, departmentResultSetExtractor);
    }
}