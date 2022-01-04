package com.epam.rd.autotasks.springemployeecatalog.rowmapper;

import com.epam.rd.autotasks.springemployeecatalog.domain.Department;
import com.epam.rd.autotasks.springemployeecatalog.service.ExtractorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static com.epam.rd.autotasks.springemployeecatalog.constants.Constant.*;

@Component
public class DepartmentResultSetExtractor implements ResultSetExtractor<Map<Integer, Department>> {
    private final ExtractorService extractorService;

    @Autowired
    public DepartmentResultSetExtractor(ExtractorService extractorService) {
        this.extractorService = extractorService;
    }

    @Override
    public Map<Integer, Department> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        Map<Integer, Department> departmentMap = new HashMap<>();
        while (resultSet.next()) {
            departmentMap.put(resultSet.getInt(ID), extractorService.getDepartment(resultSet));
        }
        return departmentMap;
    }
}