package com.epam.rd.autotasks.springemployeecatalog.extractor;

import com.epam.rd.autotasks.springemployeecatalog.domain.Department;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static com.epam.rd.autotasks.springemployeecatalog.constants.Constant.ID;
import static com.epam.rd.autotasks.springemployeecatalog.constants.Constant.LOCATION;
import static com.epam.rd.autotasks.springemployeecatalog.constants.Constant.NAME;

public class DepartmentResultSetExtractor implements ResultSetExtractor<Map<Integer, Department>> {

    @Override
    public Map<Integer, Department> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        Map<Integer, Department> departmentMap = new HashMap<>();
        while (resultSet.next()) {
            departmentMap.put(resultSet.getInt(ID), new Department(
                    resultSet.getLong(ID), resultSet.getString(NAME), resultSet.getString(LOCATION))
            );
        }
        return departmentMap;
    }
}