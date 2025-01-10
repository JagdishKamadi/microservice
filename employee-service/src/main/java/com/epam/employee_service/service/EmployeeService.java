package com.epam.employee_service.service;

import com.epam.employee_service.dto.AddressDTO;
import com.epam.employee_service.dto.EmployeeDTO;
import com.epam.employee_service.model.Employee;
import com.epam.employee_service.repository.EmployeeRepository;
import com.epam.employee_service.rest_client.AddressClientService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WebClient webClient;

    @Autowired
    private AddressClientService addressClientService;

    @Value("${address.service.base.url}")
    private String baseUrl;

    public EmployeeDTO getEmployee(final String id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new RuntimeException("Employee does not exists for this id" + id));
        EmployeeDTO employeeDTO = new EmployeeDTO();
        AddressDTO addressDTO = addressClientService.getAddress(id).getBody();
        employeeDTO.setAddressDTO(addressDTO);
        // act like the model mapper
        BeanUtils.copyProperties(employee, employeeDTO, EmployeeDTO.class);

        return employeeDTO;
    }

    public EmployeeDTO saveEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee, Employee.class);
        employee = employeeRepository.save(employee);
        BeanUtils.copyProperties(employee, employeeDTO, EmployeeDTO.class);

        AddressDTO addressDTO = employeeDTO.getAddressDTO();
        addressDTO.setId(employeeDTO.getId());
        addressDTO = addressClientService.saveAddress(addressDTO).getBody();
        employeeDTO.setAddressDTO(addressDTO);

        return employeeDTO;
    }
}
