package com.epam.rd.autotasks.springemployeecatalog.constants;

public class Constant {
    public static final String SELECT_ALL_FROM_EMPLOYEE = "SELECT * FROM employee e " +
            "LEFT JOIN department d ON e.department = d.id";
    public static final String COUNT_EMPLOYEE = "select count(*) from employee";
    public static final String SPACE = " ";
    public static final String ID = "id";
    public static final String MANAGER ="manager";
    public static final String POSITION ="position";
    public static final String HIRE_DATE ="hireDate";
    public static final String SALARY ="salary";
    public static final String FIRST_NAME ="firstName";
    public static final String LAST_NAME ="lastName";
    public static final String MIDDLE_NAME ="middleName";
    public static final String DEPARTMENT ="department";
    public static final String NAME ="name";
    public static final String LOCATION ="location";
}