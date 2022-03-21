package com.example.tesodevchallenge.service.impl;

import com.example.tesodevchallenge.model.Address;
import com.example.tesodevchallenge.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    @Override
    public String formattedAddress(Address address) {
        return address.getAddressLine() + "/" + address.getCity() + "/" + address.getCountry();
    }
}
