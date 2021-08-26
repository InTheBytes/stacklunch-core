package com.inthebytes.stacklunch.data.delivery;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inthebytes.stacklunch.data.driver.Driver;

public interface DriverRepository extends JpaRepository<Driver, String> {

}
