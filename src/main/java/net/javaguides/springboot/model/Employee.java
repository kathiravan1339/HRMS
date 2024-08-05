package net.javaguides.springboot.model;


import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Table(name = "employees")
public class Employee {

	public Employee() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long iD;

	@Column(name = "Name")
	private String name;

	@Column(name = "Department")
	private String department;

	@Column(name = "Job_Title")
	private String job_Title;

	@Column(name = "Age")
	private Integer age;

	@Column(name = "Start_Date")
	private LocalDate start_Date;

	@ManyToOne
	@JoinColumn(name = "EMPLOYEE_ID")
	private Department department_ManyToOne;





	public Department getDepartment_ManyToOne() {
		return department_ManyToOne;
	}

	public void setDepartment_ManyToOne(Department department_ManyToOne) {
		this.department_ManyToOne = department_ManyToOne;
	}

	public Long getiD() {
		return iD;
	}

	public void setiD(Long iD) {
		this.iD = iD;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}



	public String getJob_Title() {
		return job_Title;
	}

	public void setJob_Title(String job_Title) {
		this.job_Title = job_Title;
	}

	public LocalDate getStart_Date() {
		return start_Date;
	}

	public void setStart_Date(LocalDate start_Date) {
		this.start_Date = start_Date;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}






}