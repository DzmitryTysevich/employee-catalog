package com.epam.rd.autotasks.springemployeecatalog.extractor;

import com.epam.rd.autotasks.springemployeecatalog.constants.SQLQuery;
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
import java.util.HashMap;
import java.util.Map;

import static com.epam.rd.autotasks.springemployeecatalog.constants.Constant.DEPARTMENT;
import static com.epam.rd.autotasks.springemployeecatalog.constants.Constant.FIRST_NAME;
import static com.epam.rd.autotasks.springemployeecatalog.constants.Constant.HIRE_DATE;
import static com.epam.rd.autotasks.springemployeecatalog.constants.Constant.ID;
import static com.epam.rd.autotasks.springemployeecatalog.constants.Constant.LAST_NAME;
import static com.epam.rd.autotasks.springemployeecatalog.constants.Constant.MIDDLE_NAME;
import static com.epam.rd.autotasks.springemployeecatalog.constants.Constant.POSITION;
import static com.epam.rd.autotasks.springemployeecatalog.constants.Constant.SALARY;

@Component
public class ManagerResultSetExtractor implements ResultSetExtractor<Map<Long, Employee>> {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ManagerResultSetExtractor(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Map<Long, Employee> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        Map<Long, Employee> managers = new HashMap<>();
        while (resultSet.next()) {
            managers.put(resultSet.getLong(ID), new Employee(
                            resultSet.getLong(ID),
                            new FullName(
                                    resultSet.getString(FIRST_NAME),
                                    resultSet.getString(LAST_NAME),
                                    resultSet.getString(MIDDLE_NAME)
                            ),
                            Position.valueOf(resultSet.getString(POSITION)),
                            resultSet.getDate(HIRE_DATE).toLocalDate(),
                            resultSet.getBigDecimal(SALARY),
                            null,
                            getDepartments().get(resultSet.getInt(DEPARTMENT))
                    )
            );
        }
        return managers;
    }

    private Map<Integer, Department> getDepartments() {
        return jdbcTemplate.query(SQLQuery.ALL_FROM_DEPARTMENT, new DepartmentResultSetExtractor());
    }
}