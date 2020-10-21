package com.employee.model;

import java.util.List;

public class EmployeeService {
	private EmployeeDAO_interface dao;

	public EmployeeService() {
		dao = new EmployeeJNDIDAO();
	}
	
	public EmployeeVO addEmployee(String ename, String empid, String emppsw, String enickname, String email) {
		EmployeeVO employeeVO = new EmployeeVO();
		employeeVO.seteName(ename);
		employeeVO.setEmpId(empid);
		employeeVO.setEmpPsw(emppsw);
		employeeVO.seteNickname(enickname);
		employeeVO.setEmpEmail(email);
		dao.insert(employeeVO);
		return employeeVO;
	}

	public EmployeeVO updateEmployee(String empno, String ename, String empid, String emppsw, String enickname, Integer eaccstatus, String email) {
		EmployeeVO employeeVO = new EmployeeVO();
		employeeVO.setEmpNo(empno);
		employeeVO.seteName(ename);
		employeeVO.setEmpId(empid);
		employeeVO.setEmpPsw(emppsw);
		employeeVO.seteNickname(enickname);
		employeeVO.seteAccStatus(eaccstatus);
		employeeVO.setEmpEmail(email);
		dao.update(employeeVO);
		return employeeVO;
	}	
	
	public EmployeeVO getOneEmployee(String empno) {
		return dao.findByPrimaryKey(empno);
	}
	
	public List<EmployeeVO> getAll() {
		return dao.getAll();
	}
}
