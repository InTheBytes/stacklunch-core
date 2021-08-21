package com.inthebytes.stacklunch.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inthebytes.stacklunch.object.entity.Confirmation;

public interface ConfirmationRepository extends JpaRepository<Confirmation, String> {

}
