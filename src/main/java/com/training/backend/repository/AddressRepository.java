package com.training.backend.repository;

import com.training.backend.entity.Address;
import com.training.backend.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AddressRepository extends CrudRepository<Address, String> {

    List<Address> findByUser(User user);

}