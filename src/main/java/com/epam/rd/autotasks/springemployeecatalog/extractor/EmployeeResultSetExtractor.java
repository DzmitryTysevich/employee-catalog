package com.epam.rd.autotasks.springemployeecatalog.extractor;

import com.epam.rd.autotasks.springemployeecatalog.domain.Employee;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.epam.rd.autotasks.springemployeecatalog.utils.ExtractorUtils.getEmployee;
import static com.epam.rd.autotasks.springemployeecatalog.utils.ExtractorUtils.getManager;

public class EmployeeResultSetExtractor implements ResultSetExtractor<List<Employee>> {

    @Override
    public List<Employee> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        List<Employee> employees = new ArrayList<>();
        while (resultSet.next()) {
            employees.add(getEmployee(resultSet, getManager(resultSet)));
        }
        return employees;
    }
}