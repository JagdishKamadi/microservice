package com.epam.employee_service.rest_client;

import com.epam.employee_service.dto.AddressDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "address-service-client", url = "http://localhost:8082", path = "address-app/api/address")
public interface AddressClientService {

    @GetMapping(value = "/{id}")
    ResponseEntity<AddressDTO> getAddress(@PathVariable String id);

    @PostMapping
    ResponseEntity<AddressDTO> saveAddress(@RequestBody AddressDTO addressDTO);

}
