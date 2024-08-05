package net.javaguides.springboot.DTO;

import lombok.Data;

import java.time.LocalDate;


public class EmployeeDTO {

    private Long id;
    private String name;
    private String department;
    private String jobTitle;
    private Integer age;
    private LocalDate startDate;

    private Long depId;
    private String depName;

    public EmployeeDTO (){

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }


}
