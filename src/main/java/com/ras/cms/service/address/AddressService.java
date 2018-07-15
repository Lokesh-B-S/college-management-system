package com.ras.cms.service.address;

import com.ras.cms.domain.Address;

import java.util.List;

public interface AddressService {
    List<Address> findAll();
    Address findOne(Long id);
    Address saveAddress(Address address);
    void deleteAddress(Long id);
}
