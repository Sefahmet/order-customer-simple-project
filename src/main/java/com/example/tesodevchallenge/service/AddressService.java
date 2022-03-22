package com.example.tesodevchallenge.service;

import com.example.tesodevchallenge.model.entity.Address;

import java.util.UUID;

public interface AddressService {

    Address getAdrress(UUID id);
    String formattedAddress(Address address);
}
