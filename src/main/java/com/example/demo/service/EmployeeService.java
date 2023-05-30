package com.example.demo.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.custom.exception.BusinessException;
import com.example.demo.entity.Employee;
import com.example.demo.service.repos.EmployeeCrudRepo;

@Service
public class EmployeeService implements EmployeeServiceInterface {

	@Autowired
	EmployeeCrudRepo employeeCrudRepo;

	@Override
	public Employee addEmployee(Employee employee) {

		if (employee.getName().isEmpty() || employee.getName().length() == 0) {
			throw new BusinessException("601", "Please send proper name, its Blank");
		}
		try {
			Employee savedEmployee = employeeCrudRepo.save(employee);
			return savedEmployee;
		} catch (IllegalArgumentException iae) {
			throw new BusinessException("602", "given employee is null" + iae.getMessage());
		} catch (Exception e) {
			throw new BusinessException("603",
					"Something went wrong in service layer while saving employee" + e.getMessage());
		}
	}

	@Override
	public List<Employee> getAllEmployees() {
		List<Employee> empList = null;
		try {
			empList = employeeCrudRepo.findAll();
		}catch (Exception e) {
			throw new BusinessException("605",
					"Something went wrong in service layer while fetching all employees " + e.getMessage());
		}
		if (empList.isEmpty())
			throw new BusinessException("604", "Hey List is completely empty, we have nothing to return");
		return empList;
	}

	@Override
	public Employee getEmployeeById(Long empId) {
		try {
			return employeeCrudRepo.findById(empId).get();
		} catch (IllegalArgumentException iae) {
			throw new BusinessException("606",
					"given employee id is null, please send id to be searched" + iae.getMessage());
		} catch (NoSuchElementException nse) {
			throw new BusinessException("607", "given employee id does not exist in DB" + nse.getMessage());
		}
	}

	@Override
	public void deleteEmpById(Long empId) {
		try {
			employeeCrudRepo.deleteById(empId);
		} catch (IllegalArgumentException iae) {
			throw new BusinessException("608",
					"given employee id is null, please send id to be searched" + iae.getMessage());
		}
	}
}
