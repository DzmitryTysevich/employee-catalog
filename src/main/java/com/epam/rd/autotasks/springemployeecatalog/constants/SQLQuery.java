package com.epam.rd.autotasks.springemployeecatalog.constants;

public class SQLQuery {
    public static final String ALL_FROM_EMPLOYEE = "SELECT * FROM employee";
    public static final String ALL_EMPLOYEE_FORMAT_QUERY =
            "SELECT id, firstName, lastName, middleName, position, manager, hireDate AS hired, salary, department " +
                    "FROM employee ORDER BY %s %s LIMIT %d OFFSET %d";
    public static final String EMPLOYEE_BY_ID_FORMAT_QUERY =
            "SELECT id, firstName, lastName, middleName, position, manager, hireDate AS hired, salary, department " +
                    "FROM employee WHERE ID = %d";
    public static final String EMPLOYEE_BY_MANAGER_FORMAT_QUERY =
            "SELECT id, firstName, lastName, middleName, position, manager, hireDate AS hired, salary, department " +
                    "FROM employee WHERE manager = %d ORDER BY %s %s LIMIT %d OFFSET %d";
    public static final String EMPLOYEE_BY_DEPARTMENT_FORMAT_QUERY =
            "SELECT id, firstName, lastName, middleName, position, manager, hireDate AS hired, salary, department " +
                    "FROM employee WHERE department = %d ORDER BY %s %s LIMIT %d OFFSET %d";
    public static final String EMPLOYEE_BY_DEPARTMENT_NAME_FORMAT_QUERY =
            "SELECT e.id, e.firstName, e.lastName, e.middleName, e.position, e.manager, e.hireDate AS hired, e.salary, e.department " +
                    "FROM employee e " +
                    "left join department d on e.department = d.id " +
                    "where d.name = '%s' " +
                    "ORDER BY %s %s LIMIT %d OFFSET %d";
    public static final String ALL_FROM_DEPARTMENT = "SELECT * FROM department";
    public static final String COUNT_EMPLOYEE = "select count(*) from employee";
}