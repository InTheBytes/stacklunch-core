package com.inthebytes.stacklunch.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inthebytes.stacklunch.object.entity.Location;

public interface LocationRepository extends JpaRepository<Location, String> {

}
