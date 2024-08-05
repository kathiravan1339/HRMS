package net.javaguides.springboot;

import net.javaguides.springboot.DTO.EmployeeDTO;
import net.javaguides.springboot.controller.EmployeeController;
import net.javaguides.springboot.exception.EmployeeNotFoundException;
import net.javaguides.springboot.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class EmployeeControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
    }

    @Test
    void createEmployee() throws Exception {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setName("John Doe");
        employeeDTO.setDepartment("IT");
        employeeDTO.setJobTitle("Developer");
        employeeDTO.setAge(30);
        employeeDTO.setStartDate(LocalDate.parse("2021-01-01"));

        when(employeeService.createEmployee(any(EmployeeDTO.class))).thenReturn("Saved Successfully");

        mockMvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"John Doe\",\"department\":\"IT\",\"jobTitle\":\"Developer\",\"age\":30,\"startDate\":\"2021-01-01\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Saved Successfully"));

        verify(employeeService, times(1)).createEmployee(any(EmployeeDTO.class));
    }

    @Test
    void getAllEmployees() throws Exception {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setName("John Doe");
        employeeDTO.setDepartment("IT");
        employeeDTO.setJobTitle("Developer");
        employeeDTO.setAge(30);
        employeeDTO.setStartDate(LocalDate.parse("2021-01-01"));

        List<EmployeeDTO> employees = Arrays.asList(employeeDTO);

        when(employeeService.getAllEmployees()).thenReturn(employees);

        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[0].department").value("IT"))
                .andExpect(jsonPath("$[0].jobTitle").value("Developer"))
                .andExpect(jsonPath("$[0].age").value(30))
                .andExpect(jsonPath("$[0].startDate").value("2021-01-01"));

        verify(employeeService, times(1)).getAllEmployees();
    }

    @Test
    void getEmployeeById() throws Exception {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setName("John Doe");
        employeeDTO.setDepartment("IT");
        employeeDTO.setJobTitle("Developer");
        employeeDTO.setAge(30);
        employeeDTO.setStartDate(LocalDate.parse("2021-01-01"));

        when(employeeService.getEmployeeById(anyLong())).thenReturn(employeeDTO);

        mockMvc.perform(get("/employees/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.department").value("IT"))
                .andExpect(jsonPath("$.jobTitle").value("Developer"))
                .andExpect(jsonPath("$.age").value(30))
                .andExpect(jsonPath("$.startDate").value("2021-01-01"));

        verify(employeeService, times(1)).getEmployeeById(anyLong());
    }

    @Test
    void getEmployeeByIdNotFound() throws Exception {
        when(employeeService.getEmployeeById(anyLong())).thenThrow(new EmployeeNotFoundException("Employee not found"));

        mockMvc.perform(get("/employees/1"))
                .andExpect(status().isNotFound());

        verify(employeeService, times(1)).getEmployeeById(anyLong());
    }

    @Test
    void updateEmployee() throws Exception {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setName("John Doe");
        employeeDTO.setDepartment("IT");
        employeeDTO.setJobTitle("Developer");
        employeeDTO.setAge(30);
        employeeDTO.setStartDate(LocalDate.parse("2021-01-01"));

        when(employeeService.updateEmployee(anyLong(), any(EmployeeDTO.class))).thenReturn("Updated Successfully");

        mockMvc.perform(put("/employees/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"John Doe\",\"department\":\"IT\",\"jobTitle\":\"Developer\",\"age\":30,\"startDate\":\"2021-01-01\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Updated Successfully"));

        verify(employeeService, times(1)).updateEmployee(anyLong(), any(EmployeeDTO.class));
    }

    @Test
    void updateEmployeeNotFound() throws Exception {
        when(employeeService.updateEmployee(anyLong(), any(EmployeeDTO.class))).thenThrow(new EmployeeNotFoundException("Employee not found"));

        mockMvc.perform(put("/employees/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"John Doe\",\"department\":\"IT\",\"jobTitle\":\"Developer\",\"age\":30,\"startDate\":\"2021-01-01\"}"))
                .andExpect(status().isNotFound());

        verify(employeeService, times(1)).updateEmployee(anyLong(), any(EmployeeDTO.class));
    }

    @Test
    void deleteEmployee() throws Exception {
        when(employeeService.deleteEmployee(anyLong())).thenReturn("Deleted Successfully");

        mockMvc.perform(delete("/employees/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Deleted Successfully"));

        verify(employeeService, times(1)).deleteEmployee(anyLong());
    }

    @Test
    void deleteEmployeeNotFound() throws Exception {
        when(employeeService.deleteEmployee(anyLong())).thenThrow(new EmployeeNotFoundException("Employee not found"));

        mockMvc.perform(delete("/employees/1"))
                .andExpect(status().isNotFound());

        verify(employeeService, times(1)).deleteEmployee(anyLong());
    }
}
