package com.ras.cms.dao;

import com.ras.cms.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository  extends JpaRepository<Address, Long> {
}
