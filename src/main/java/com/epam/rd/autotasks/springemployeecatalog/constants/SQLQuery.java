package com.epam.rd.autotasks.springemployeecatalog.constants;

public class SQLQuery {
    public static final String ALL_FROM_EMPLOYEE =
            "SELECT e.id, e.firstName, e.lastName, e.middleName, e.position, e.manager, e.hireDate, e.salary, e.department, d_e.id, d_e.name, d_e.location, " +
                    "FROM employee e " +
                    "LEFT JOIN employee m ON e.manager = m.id " +
                    "LEFT JOIN department d_e ON e.department = d_e.id";

    public static final String ALL_FROM_EMPLOYEE_FORMAT_QUERY =
            "SELECT e.id, e.firstName, e.lastName, e.middleName, e.position, e.manager, e.hireDate AS hired, e.salary, e.department, d_e.id, d_e.name, d_e.location, " +
                    "FROM employee e " +
                    "LEFT JOIN employee m ON e.manager = m.id " +
                    "LEFT JOIN department d_e ON e.department = d_e.id " +
                    "ORDER BY %s %s";
}