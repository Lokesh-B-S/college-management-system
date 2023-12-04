package com.ras.cms.repository;

import com.ras.cms.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository  extends JpaRepository<Address, Long> {
}
