package com.epam.address_service.service;

import com.epam.address_service.dto.AddressDTO;
import com.epam.address_service.model.Address;
import com.epam.address_service.repository.AddressRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public AddressDTO getAddressById(final Integer id) {
        Address address = addressRepository.findById(id).orElseThrow(() -> new RuntimeException("Address not found this id" + id));
        AddressDTO addressDTO = new AddressDTO();
        BeanUtils.copyProperties(address, addressDTO, AddressDTO.class);

        return addressDTO;
    }

    public AddressDTO saveAddress(AddressDTO addressDTO) {
        Address address = new Address();
        BeanUtils.copyProperties(addressDTO, address, Address.class);
        addressRepository.save(address);
        BeanUtils.copyProperties(address, addressDTO, AddressDTO.class);
        return addressDTO;
    }
}
