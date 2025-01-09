package com.epam.employee_service.service;

import com.epam.employee_service.dto.EmployeeDTO;
import com.epam.employee_service.model.Employee;
import com.epam.employee_service.repository.EmployeeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public EmployeeDTO getEmployeeById(final String id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new RuntimeException("Employee does not exists for this id" + id));
        EmployeeDTO employeeDTO = new EmployeeDTO();
        // act like the model mapper
        BeanUtils.copyProperties(employee, employeeDTO, EmployeeDTO.class);

        return employeeDTO;
    }

    public EmployeeDTO saveEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee, Employee.class);
        employeeRepository.save(employee);
        BeanUtils.copyProperties(employee, employeeDTO, EmployeeDTO.class);

        return employeeDTO;
    }
}
