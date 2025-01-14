package com.epam.address_service.controller;

import com.epam.address_service.dto.AddressDTO;
import com.epam.address_service.service.AddressService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "address")
public class AddressController {

    private static final Logger LOGGER = LogManager.getLogger(AddressController.class);

    @Autowired
    private Environment environment;

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<AddressDTO> getAddress(@PathVariable String id) {
        LOGGER.info("Current port : {}", environment.getProperty("server.port"));
        return ResponseEntity.status(HttpStatus.OK).body(addressService.getAddressById(id));
    }

    @PostMapping
    public ResponseEntity<AddressDTO> saveAddress(@RequestBody AddressDTO addressDTO) {
        LOGGER.info("Current port : {}", environment.getProperty("server.port"));
        return ResponseEntity.status(HttpStatus.CREATED).body(addressService.saveAddress(addressDTO));
    }
}
