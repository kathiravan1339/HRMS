package net.javaguides.springboot.model;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "departments")
public class Department {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long iD;

	@Column(name = "NAME")
	private String name;

	@OneToMany(mappedBy = "department_ManyToOne")
	private List<Employee> employees;

	public Department() {
	}




	public Long getiD() {
		return iD;
	}

	public void setiD(Long iD) {
		this.iD = iD;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

}