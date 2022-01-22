package com.epam.rd.autotasks.springemployeecatalog.extractor;

import com.epam.rd.autotasks.springemployeecatalog.domain.Department;
import com.epam.rd.autotasks.springemployeecatalog.domain.Employee;
import com.epam.rd.autotasks.springemployeecatalog.domain.FullName;
import com.epam.rd.autotasks.springemployeecatalog.domain.Position;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.epam.rd.autotasks.springemployeecatalog.constants.Constant.DEPARTMENT;
import static com.epam.rd.autotasks.springemployeecatalog.constants.Constant.FIRST_NAME;
import static com.epam.rd.autotasks.springemployeecatalog.constants.Constant.HIRE_DATE;
import static com.epam.rd.autotasks.springemployeecatalog.constants.Constant.ID;
import static com.epam.rd.autotasks.springemployeecatalog.constants.Constant.LAST_NAME;
import static com.epam.rd.autotasks.springemployeecatalog.constants.Constant.LOCATION;
import static com.epam.rd.autotasks.springemployeecatalog.constants.Constant.MANAGER;
import static com.epam.rd.autotasks.springemployeecatalog.constants.Constant.MIDDLE_NAME;
import static com.epam.rd.autotasks.springemployeecatalog.constants.Constant.NAME;
import static com.epam.rd.autotasks.springemployeecatalog.constants.Constant.POSITION;
import static com.epam.rd.autotasks.springemployeecatalog.constants.Constant.SALARY;
import static java.sql.Types.NULL;

@Component
public class EmployeeResultSetExtractorManagersManager implements ResultSetExtractor<List<Employee>> {

    @Override
    public List<Employee> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        List<Employee> employees = new ArrayList<>();
        while (resultSet.next()) {
            employees.add(getEmployee(resultSet, getManager(resultSet)));
        }
        return employees;
    }

    private Employee getEmployee(ResultSet resultSet, Employee manager) throws SQLException {
        return new Employee(
                resultSet.getLong(ID),
                getFullName(resultSet),
                Position.valueOf(resultSet.getString(POSITION)),
                resultSet.getDate(HIRE_DATE).toLocalDate(),
                resultSet.getBigDecimal(SALARY),
                resultSet.getLong(MANAGER) == NULL ? null : manager,
                resultSet.getLong(DEPARTMENT) == NULL ? null : getDepartments(resultSet).get(resultSet.getLong(DEPARTMENT))
        );
    }

    private FullName getFullName(ResultSet resultSet) throws SQLException {
        return new FullName(
                resultSet.getString(FIRST_NAME),
                resultSet.getString(LAST_NAME),
                resultSet.getString(MIDDLE_NAME)
        );
    }

    private Employee getManager(ResultSet resultSet) throws SQLException {
        int currentRow = resultSet.getRow();
        long managerId = resultSet.getLong(MANAGER);
        resultSet.beforeFirst();
        Employee manager = null;
        while (resultSet.next()) {
            if (resultSet.getLong(ID) == managerId) {
                manager = getEmployee(resultSet, getManager(resultSet));
                break;
            }
        }
        resultSet.absolute(currentRow);
        return manager;
    }

    private Map<Long, Department> getDepartments(ResultSet resultSet) throws SQLException {
        int currentRow = resultSet.getRow();
        resultSet.beforeFirst();
        Map<Long, Department> departments = new HashMap<>();
        while (resultSet.next()) {
            departments.put(
                    resultSet.getLong(DEPARTMENT),
                    new Department(resultSet.getLong(DEPARTMENT), resultSet.getString(NAME), resultSet.getString(LOCATION))
            );
        }
        resultSet.absolute(currentRow);
        return departments;
    }
}