package net.javaguides.springboot.controller;

import lombok.extern.log4j.Log4j2;
import net.javaguides.springboot.DTO.EmployeeDTO;
import net.javaguides.springboot.model.Department;
import net.javaguides.springboot.model.Employee;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import net.javaguides.springboot.service.EmployeeService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/employees")
@Log4j2
public class EmployeeController {

	private static final Logger logger = LogManager.getLogger(EmployeeController.class);

	@Autowired
	private EmployeeService employeeService;

	@PostMapping
	public ResponseEntity<String> createEmployee(@RequestBody EmployeeDTO employeeDTO) {
		logger.info("Creating a new employee: {}", employeeDTO);
		String response = employeeService.createEmployee(employeeDTO);
		logger.info("Employee created successfully: {}", employeeDTO);
		return ResponseEntity.ok(response);
	}

	@GetMapping
	public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
		logger.info("Retrieving all employees");
		List<EmployeeDTO> employees = employeeService.getAllEmployees();
		logger.info("Number of employees retrieved: {}", employees.size());
		return ResponseEntity.ok(employees);
	}

	@GetMapping("/{id}")
	public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long id) {
		logger.info("Retrieving employee with ID: {}", id);
		try {
			EmployeeDTO employee = employeeService.getEmployeeById(id);
			return ResponseEntity.ok(employee);
		} catch (Exception e) {
			logger.error("Employee not found with ID: {}", id, e);
			return ResponseEntity.notFound().build();
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<String> updateEmployee(@PathVariable Long id, @RequestBody EmployeeDTO employeeDTO) {
		logger.info("Updating employee with ID: {}", id);
		try {
			String response = employeeService.updateEmployee(id, employeeDTO);
			logger.info("Employee updated successfully: {}", employeeDTO);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			logger.error("Employee not found with ID: {}", id, e);
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
		logger.info("Deleting employee with ID: {}", id);
		try {
			String response = employeeService.deleteEmployee(id);
			logger.info("Employee deleted successfully with ID: {}", id);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			logger.error("Employee not found with ID: {}", id, e);
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/search")
	public List<Employee> searchEmployees(
			@RequestParam(required = false) String name,
			@RequestParam(required = false) String department,
			@RequestParam(required = false) String jobTitle,
			@RequestParam(required = false) Integer age,
			@RequestParam(required = false) LocalDate startDate) {
	logger.info("Searching employees with criteria: name={}, department={}, jobTitle={}, age={}, startDate={}", name, department, jobTitle, age, startDate);
		return employeeService.searchEmployees(name, department, jobTitle, age, startDate);
	}

	//================================================================================================================================

	@GetMapping("/{departmentId}/employees")
	public ResponseEntity<List<Employee>> getAllEmployees(@PathVariable Long departmentId) {
		logger.info("Retrieving all employees for department id: {}", departmentId);

		List<Employee> employees = employeeService.getAllEmployees1(departmentId);
		if (employees.isEmpty()) {
			logger.info("No employees found for department id: {}", departmentId);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		logger.info("Employees retrieved successfully for department id: {}", departmentId);
		return new ResponseEntity<>(employees, HttpStatus.OK);
	}








	@PostMapping("/{id}/departments/{departmentId}")
	public String assignEmployeeToDepartment(@PathVariable Long id, @PathVariable Long departmentId) {
		logger.info("Assigning employee with ID {} to department with ID {}", id, departmentId);
		return employeeService.assignEmployeeToDepartment(id, departmentId);
	}

	@DeleteMapping("/{id}/departments/{departmentId}")
	public String removeEmployeeFromDepartment(@PathVariable Long id, @PathVariable Long departmentId) {
			logger.info("Removing employee with ID {} from department with ID {}", id);
		return employeeService.removeEmployeeFromDepartment(id, departmentId);
	}

	@GetMapping("/count")
	public Long getEmployeeCount() {
		logger.info("Retrieving total number of employees");
        return employeeService.getEmployeeCount();
	}




}