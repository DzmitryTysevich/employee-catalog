package com.epam.rd.autotasks.springemployeecatalog.constants;

public class Constant {
    public static final String ALL_FROM_EMPLOYEE = "SELECT * FROM employee";
    public static final String PAGEABLE_FORMAT_QUERY =
            "SELECT id, firstName, lastName, middleName, position, manager, hireDate AS hired, salary, department " +
                    "FROM employee ORDER BY %s %s LIMIT %d OFFSET %d";
    public static final String ALL_FROM_DEPARTMENT = "SELECT * FROM department";
    public static final String COUNT_EMPLOYEE = "select count(*) from employee";
    public static final String ID = "id";
    public static final String MANAGER = "manager";
    public static final String POSITION = "position";
    public static final String HIRE_DATE = "hireDate";
    public static final String SALARY = "salary";
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String MIDDLE_NAME = "middleName";
    public static final String DEPARTMENT = "department";
    public static final String NAME = "name";
    public static final String LOCATION = "location";
}