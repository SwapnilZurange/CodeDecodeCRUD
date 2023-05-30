package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.custom.exception.BusinessException;
import com.example.demo.custom.exception.ControllerException;
import com.example.demo.entity.Employee;
import com.example.demo.service.EmployeeServiceInterface;

@RestController
@RequestMapping("/code")
public class EmployeeController {

	@Autowired
	private EmployeeServiceInterface employeeServiceInterface;

	@PostMapping("/save")
	public ResponseEntity<?> addEmployee(@RequestBody Employee employee) {
		try {
			Employee employeeSaved = employeeServiceInterface.addEmployee(employee);
			return new ResponseEntity<Employee>(employeeSaved, HttpStatus.CREATED);
		} catch (BusinessException be) {
			ControllerException ce = new ControllerException(be.getErrorCode(), be.getErrorMessage());
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		} catch (Exception be) {
			ControllerException ce = new ControllerException("611", "Something went wrong in controller");
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/all")
	public ResponseEntity<List<Employee>> geAllEmployee() {
		List<Employee> listOfAllEmps = employeeServiceInterface.getAllEmployees();
		return new ResponseEntity<List<Employee>>(listOfAllEmps, HttpStatus.OK);
	}

	@GetMapping("/emp/{empId}")
	public ResponseEntity<?> getEmployeeById(@PathVariable("empId") Long empId) {
		try {
			Employee emp = employeeServiceInterface.getEmployeeById(empId);
			return new ResponseEntity<Employee>(emp, HttpStatus.OK);
		} catch (BusinessException be) {
			ControllerException ce = new ControllerException(be.getErrorCode(), be.getErrorMessage());
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		} catch (Exception be) {
			ControllerException ce = new ControllerException("612", "Something went wrong in controller");
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/delete")
	@GetMapping("/emp/{empId}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable("empId") Long empId) {
		Employee emp = employeeServiceInterface.getEmployeeById(empId);
		return new ResponseEntity<Employee>(emp, HttpStatus.OK);
	}

	@DeleteMapping("/delete/{empId}")
	public ResponseEntity<Void> deleteEmpById(@PathVariable("empId") Long empId) {
		employeeServiceInterface.deleteEmpById(empId);
		return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
	}
}