package com.epam.employee_service.service;

import com.epam.employee_service.dto.AddressDTO;
import com.epam.employee_service.dto.EmployeeDTO;
import com.epam.employee_service.model.Employee;
import com.epam.employee_service.repository.EmployeeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WebClient webClient;

    @Value("${address.service.base.url}")
    private String baseUrl;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;

    }

    public EmployeeDTO getEmployeeAndAddressById(final String id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new RuntimeException("Employee does not exists for this id" + id));
        EmployeeDTO employeeDTO = new EmployeeDTO();
//        AddressDTO addressDTO = restTemplate.getForObject(baseUrl + "/address/{id}", AddressDTO.class, employee.getId());
        Mono<AddressDTO> addressDTOMono = webClient.get()
                .uri(baseUrl + "/address/{id}", employee.getId())
                .retrieve()
                .bodyToMono(AddressDTO.class);
        employeeDTO.setAddressDTO(addressDTOMono.block());
        // act like the model mapper
        BeanUtils.copyProperties(employee, employeeDTO, EmployeeDTO.class);

        return employeeDTO;
    }

    public EmployeeDTO saveEmployeeWithAddress(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee, Employee.class);
        employee = employeeRepository.save(employee);
        BeanUtils.copyProperties(employee, employeeDTO, EmployeeDTO.class);

        AddressDTO addressDTO = employeeDTO.getAddressDTO();
        addressDTO.setId(employeeDTO.getId());
//        AddressDTO addressDTOResponseEntity = restTemplate.postForObject(baseUrl + "/address", addressDTO, AddressDTO.class);
        Mono<AddressDTO> addressDTOMono = webClient.post()
                .uri(baseUrl + "/address")
                .bodyValue(addressDTO)
                .retrieve()
                .bodyToMono(AddressDTO.class);
        employeeDTO.setAddressDTO(addressDTOMono.block());

        return employeeDTO;
    }
}
