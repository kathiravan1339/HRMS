package net.javaguides.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.javaguides.springboot.model.Employee;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {


    @Query("select e FROM Employee e " +
            "where (:name IS NULL OR e.name LIKE %:name%) " +
            "AND (:department IS NULL OR e.department LIKE %:department%) " +
            "AND (:jobTitle IS NULL OR e.job_Title LIKE %:jobTitle%) " +
            "AND (:age IS NULL OR e.age = :age) " +
            "AND (:startDate IS NULL OR e.start_Date = :startDate)")
    List<Employee> findByCriteria(
            @Param("name") String name,
            @Param("department") String department,
            @Param("jobTitle") String jobTitle,
            @Param("age") Integer age,
            @Param("startDate") LocalDate startDate);



    @Modifying
    @Transactional
    @Query(value = "DELETE FROM `employees` WHERE `ID` =:employeeId AND `employee_id` =:departmentId ;", nativeQuery = true)
    void deleteByIdAndDep(@Param("employeeId") Long employeeId,@Param("departmentId") Long departmentId);
}

