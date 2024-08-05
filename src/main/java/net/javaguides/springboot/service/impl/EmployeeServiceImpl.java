package net.javaguides.springboot.service.impl;


import net.javaguides.springboot.DTO.EmployeeDTO;
import net.javaguides.springboot.model.Employee;

import java.util.List;

public interface EmployeeServiceImpl {

    String createEmployee(EmployeeDTO employeeDTO);

    List<EmployeeDTO> getAllEmployees();

   EmployeeDTO getEmployeeById(Long id);

    String updateEmployee(Long id, EmployeeDTO employeeDTO);

    String deleteEmployee(Long id);


}

