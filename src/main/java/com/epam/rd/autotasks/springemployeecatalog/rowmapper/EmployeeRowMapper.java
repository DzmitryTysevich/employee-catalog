package com.epam.rd.autotasks.springemployeecatalog.rowmapper;

import com.epam.rd.autotasks.springemployeecatalog.domain.Department;
import com.epam.rd.autotasks.springemployeecatalog.domain.Employee;
import com.epam.rd.autotasks.springemployeecatalog.domain.FullName;
import com.epam.rd.autotasks.springemployeecatalog.domain.Position;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeRowMapper implements RowMapper<Employee> {

    @Override
    public Employee mapRow(ResultSet resultSet, int i) throws SQLException {
        return getEmployee(resultSet, null);
    }

    private Employee getEmployee(ResultSet resultSet, Employee manager) throws SQLException {
        return new Employee(
                resultSet.getLong("id"),
                getFullName(resultSet),
                Position.valueOf(resultSet.getString("position")),
                resultSet.getDate("hireDate").toLocalDate(),
                resultSet.getBigDecimal("salary"),
                manager,
                getDepartment(resultSet)
        );
    }

    private FullName getFullName(ResultSet resultSet) throws SQLException {
        return new FullName(
                resultSet.getString("firstName"),
                resultSet.getString("lastName"),
                resultSet.getString("middleName")
        );
    }

    private Department getDepartment(ResultSet resultSet) throws SQLException {
        return new Department(
                resultSet.getLong("department"),
                resultSet.getString("name"),
                resultSet.getString("location")
        );
    }
}