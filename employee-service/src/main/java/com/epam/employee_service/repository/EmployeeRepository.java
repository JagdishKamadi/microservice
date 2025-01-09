package com.epam.employee_service.repository;

import com.epam.employee_service.model.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends MongoRepository<Employee, String> {

    Optional<Employee> findEmployeeById(String employeeId);
}
