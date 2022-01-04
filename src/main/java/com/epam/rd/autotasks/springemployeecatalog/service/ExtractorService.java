package com.epam.rd.autotasks.springemployeecatalog.service;

import com.epam.rd.autotasks.springemployeecatalog.domain.Department;
import com.epam.rd.autotasks.springemployeecatalog.domain.Employee;
import com.epam.rd.autotasks.springemployeecatalog.domain.FullName;
import com.epam.rd.autotasks.springemployeecatalog.domain.Position;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.epam.rd.autotasks.springemployeecatalog.constants.Constant.FIRST_NAME;
import static com.epam.rd.autotasks.springemployeecatalog.constants.Constant.HIRE_DATE;
import static com.epam.rd.autotasks.springemployeecatalog.constants.Constant.ID;
import static com.epam.rd.autotasks.springemployeecatalog.constants.Constant.LAST_NAME;
import static com.epam.rd.autotasks.springemployeecatalog.constants.Constant.LOCATION;
import static com.epam.rd.autotasks.springemployeecatalog.constants.Constant.MIDDLE_NAME;
import static com.epam.rd.autotasks.springemployeecatalog.constants.Constant.NAME;
import static com.epam.rd.autotasks.springemployeecatalog.constants.Constant.POSITION;
import static com.epam.rd.autotasks.springemployeecatalog.constants.Constant.SALARY;

@Service
public class ExtractorService {
    public Employee getEmployee(ResultSet resultSet, Employee manager, Department department) throws SQLException {
        return new Employee(
                resultSet.getLong(ID),
                getFullName(resultSet),
                Position.valueOf(resultSet.getString(POSITION)),
                resultSet.getDate(HIRE_DATE).toLocalDate(),
                resultSet.getBigDecimal(SALARY),
                manager,
                department
        );
    }

    public Department getDepartment(ResultSet resultSet) throws SQLException {
        return new Department(
                resultSet.getLong(ID),
                resultSet.getString(NAME),
                resultSet.getString(LOCATION)
        );
    }

    private FullName getFullName(ResultSet resultSet) throws SQLException {
        return new FullName(
                resultSet.getString(FIRST_NAME),
                resultSet.getString(LAST_NAME),
                resultSet.getString(MIDDLE_NAME)
        );
    }
}