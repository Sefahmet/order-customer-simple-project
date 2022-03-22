package com.example.tesodevchallenge.service.impl;

import com.example.tesodevchallenge.exception.NotFoundException;
import com.example.tesodevchallenge.model.entity.Address;
import com.example.tesodevchallenge.repository.AddressRepository;
import com.example.tesodevchallenge.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    @Override
    public Address getAdrress(UUID id) {
        Optional<Address> byId = addressRepository.findById(id);
        return byId.orElseThrow(()-> new NotFoundException("Address"));
    }

    @Override
    public String formattedAddress(Address address) {
        return address.getAddressLine() + "/" + address.getCity() + "/" + address.getCountry();
    }
}
