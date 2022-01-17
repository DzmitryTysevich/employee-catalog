package com.epam.rd.autotasks.springemployeecatalog.extractor;

import com.epam.rd.autotasks.springemployeecatalog.domain.Department;
import com.epam.rd.autotasks.springemployeecatalog.domain.Employee;
import com.epam.rd.autotasks.springemployeecatalog.domain.FullName;
import com.epam.rd.autotasks.springemployeecatalog.domain.Position;
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

import static com.epam.rd.autotasks.springemployeecatalog.constants.Constant.DEPARTMENT;
import static com.epam.rd.autotasks.springemployeecatalog.constants.Constant.FIRST_NAME;
import static com.epam.rd.autotasks.springemployeecatalog.constants.Constant.HIRE_DATE;
import static com.epam.rd.autotasks.springemployeecatalog.constants.Constant.ID;
import static com.epam.rd.autotasks.springemployeecatalog.constants.Constant.LAST_NAME;
import static com.epam.rd.autotasks.springemployeecatalog.constants.Constant.MANAGER;
import static com.epam.rd.autotasks.springemployeecatalog.constants.Constant.MIDDLE_NAME;
import static com.epam.rd.autotasks.springemployeecatalog.constants.Constant.POSITION;
import static com.epam.rd.autotasks.springemployeecatalog.constants.Constant.SALARY;
import static com.epam.rd.autotasks.springemployeecatalog.constants.SQLQuery.ALL_FROM_DEPARTMENT;
import static com.epam.rd.autotasks.springemployeecatalog.constants.SQLQuery.ALL_FROM_EMPLOYEE;

@Component
public class EmployeeResultSetExtractor implements ResultSetExtractor<List<Employee>> {
    private final JdbcTemplate jdbcTemplate;
    private final ManagerResultSetExtractor managerResultSetExtractor;

    @Autowired
    public EmployeeResultSetExtractor(JdbcTemplate jdbcTemplate,
                                      ManagerResultSetExtractor managerResultSetExtractor) {
        this.jdbcTemplate = jdbcTemplate;
        this.managerResultSetExtractor = managerResultSetExtractor;
    }

    @Override
    public List<Employee> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        List<Employee> employees = new LinkedList<>();
        while (resultSet.next()) {
            employees.add(new Employee(
                            resultSet.getLong(ID),
                            new FullName(
                                    resultSet.getString(FIRST_NAME),
                                    resultSet.getString(LAST_NAME),
                                    resultSet.getString(MIDDLE_NAME)
                            ),
                            Position.valueOf(resultSet.getString(POSITION)),
                            resultSet.getDate(HIRE_DATE).toLocalDate(),
                            resultSet.getBigDecimal(SALARY),
                            getManagers().get(resultSet.getLong(MANAGER)),
                            getDepartments().get(resultSet.getInt(DEPARTMENT))
                    )
            );
        }
        return employees;
    }

    private Map<Long, Employee> getManagers() {
        return jdbcTemplate.query(ALL_FROM_EMPLOYEE, managerResultSetExtractor);
    }

    private Map<Integer, Department> getDepartments() {
        return jdbcTemplate.query(ALL_FROM_DEPARTMENT, new DepartmentResultSetExtractor());
    }
}