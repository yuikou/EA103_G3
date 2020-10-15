package com.employee.model;

import java.util.List;


public interface EmployeeDAO_interface {
	public void insert(EmployeeVO employeeVO);
    public void update(EmployeeVO employeeVO);
    public EmployeeVO findByPrimaryKey(String empNo);
    public List<EmployeeVO> getAll();
}
