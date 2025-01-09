package com.epam.employee_service.controller;

import com.epam.employee_service.dto.EmployeeDTO;
import com.epam.employee_service.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<EmployeeDTO> getEmployee(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.FOUND).body(employeeService.getEmployeeAndAddressById(id));
    }

    @PostMapping
    public ResponseEntity<EmployeeDTO> saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.saveEmployeeWithAddress(employeeDTO));
    }
}
