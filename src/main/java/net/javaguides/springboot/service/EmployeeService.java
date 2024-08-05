package net.javaguides.springboot.service;

import lombok.extern.log4j.Log4j2;
import net.javaguides.springboot.DTO.EmployeeDTO;
import net.javaguides.springboot.exception.EmployeeNotFoundException;
import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.model.Department;
import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.repository.DepartmentRepository;
import net.javaguides.springboot.repository.EmployeeRepository;
import net.javaguides.springboot.service.impl.EmployeeServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
public class EmployeeService implements EmployeeServiceImpl {

    private static final Logger logger = LogManager.getLogger(EmployeeService.class);

    @Autowired
    private EmployeeRepository employeeRepository;


    @Autowired
    private DepartmentRepository departmentRepository;


    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }



    public String createEmployee(EmployeeDTO employeeDTO) {
        try {
            Employee employee = new Employee();
            employee.setName(employeeDTO.getName());
            employee.setDepartment(employeeDTO.getDepartment());
            employee.setJob_Title(employeeDTO.getJobTitle());
            employee.setAge(employeeDTO.getAge());
            employee.setStart_Date(employeeDTO.getStartDate());
            employeeRepository.save(employee);
            logger.info("Employee saved: {}", employee);
            return "Saved Successfully";
        } catch (DataAccessException e) {
            logger.error("Error saving employee: {}", employeeDTO, e);
            return "Error saving employee";
        }
    }

    public List<EmployeeDTO> getAllEmployees() {
        logger.info("Retrieving all employees");
        return employeeRepository.findAll()
                .stream()
                .map(employee -> {
                    EmployeeDTO dto = new EmployeeDTO();
                    dto.setName(employee.getName());
                    dto.setDepartment(employee.getDepartment());
                    dto.setJobTitle(employee.getJob_Title());
                    dto.setAge(employee.getAge());
                    dto.setStartDate(employee.getStart_Date());
                    return dto;
                }).collect(Collectors.toList());
    }

    public EmployeeDTO getEmployeeById(Long id) {
        logger.info("Retrieving employee by ID: {}", id);
        return employeeRepository.findById(id)
                .map(employee -> {
                    EmployeeDTO dto = new EmployeeDTO();
                    dto.setName(employee.getName());
                    dto.setDepartment(employee.getDepartment());
                    dto.setJobTitle(employee.getJob_Title());
                    dto.setAge(employee.getAge());
                    dto.setStartDate(employee.getStart_Date());
                    return dto;
                }).orElseThrow(() -> {
                    logger.error("Employee not found with ID: {}", id);
                    return new EmployeeNotFoundException("Employee not found with ID: " + id);
                });
    }

    public String updateEmployee(Long id, EmployeeDTO employeeDTO) {
        logger.info("Updating employee with ID: {}", id);
        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        try{
        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            employee.setName(employeeDTO.getName());
            employee.setDepartment(employeeDTO.getDepartment());
            employee.setJob_Title(employeeDTO.getJobTitle());
            employee.setAge(employeeDTO.getAge());
            employee.setStart_Date(employeeDTO.getStartDate());
            employeeRepository.save(employee);
            logger.info("Employee updated: {}", employee);
            return "Updated Successfully";
        } else {
            logger.error("Employee not found with ID: {}", id);
            throw new EmployeeNotFoundException("Employee not found with ID: " + id);
        }
    } catch (DataAccessException e){
        e.printStackTrace();
    }catch (NullPointerException e){
        e.printStackTrace();
    }
        return "Delete Successfully";
    }

    public String deleteEmployee(Long id) {
        logger.info("Deleting employee with ID: {}", id);
        if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id);
            logger.info("Employee deleted with ID: {}", id);
            return "Deleted Successfully";
        } else {
            logger.error("Employee not found with ID: {}", id);
            throw new EmployeeNotFoundException("Employee not found with ID: " + id);
        }
    }


    public List<Employee> searchEmployees(String name, String department, String jobTitle, Integer age, LocalDate startDate) {
        logger.info("Service: Searching employees with criteria: name={}, department={}, jobTitle={}, age={}, startDate={}",
                name, department, jobTitle, age, startDate);
        return employeeRepository.findByCriteria(name, department, jobTitle, age, startDate);
    }

    //=====================================================================================================================


    public List<Employee> getAllEmployees1(Long departmentId) {
        logger.debug("Looking for department id: {}", departmentId);
        Optional<Department> departmentOptional = departmentRepository.findById(departmentId);

        if (departmentOptional.isPresent()) {
            logger.debug("Department found with id: {}", departmentId);
            return departmentOptional.get().getEmployees();
        } else {
            logger.error("Department not found with id: {}", departmentId);
            throw new ResourceNotFoundException("Department not found with id: " + departmentId);
        }
    }



    public String assignEmployeeToDepartment(Long employeeId, Long departmentId) {
        logger.info("Service: Assigning employee with ID {} to department with ID {}", employeeId, departmentId);
        try {
            EmployeeDTO employeeDTO = getEmployeeById(employeeId);
            Employee employee = new Employee();
            employee.setName(employeeDTO.getName());
            employee.setDepartment(employeeDTO.getDepartment());
            employee.setJob_Title(employeeDTO.getJobTitle());
            employee.setAge(employeeDTO.getAge());
            employee.setStart_Date(employeeDTO.getStartDate());

            Department department = departmentRepository.findById(departmentId).orElseThrow(() -> new ResourceNotFoundException("Department not found"));
            employee.setDepartment_ManyToOne(department);
            employeeRepository.save(employee);
        } catch (DataAccessException e){
            e.printStackTrace();
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        logger.info("Successfully assigned employee with ID {} to department with ID {}", employeeId, departmentId);
        return "assignEmployeeToDepartment";
    }

    public String removeEmployeeFromDepartment(Long employeeId, Long departmentId) {
        logger.info("Service: Removing employee with ID {} from department with ID {}", employeeId);

            employeeRepository.deleteByIdAndDep(employeeId, departmentId);
            logger.info("Successfully removed employee with ID {} from department with ID {}", employeeId);

        return "removeEmployeeFromDepartment";
    }

    public long getEmployeeCount() {
        return employeeRepository.count();
    }



}