package com.epam.address_service.controller;

import com.epam.address_service.dto.AddressDTO;
import com.epam.address_service.service.AddressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "address")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<AddressDTO> getAddress(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.FOUND).body(addressService.getAddressById(id));
    }

    @PostMapping
    public ResponseEntity<AddressDTO> saveAddress(@RequestBody AddressDTO addressDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(addressService.saveAddress(addressDTO));
    }
}
