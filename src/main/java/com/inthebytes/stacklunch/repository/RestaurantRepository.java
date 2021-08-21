package com.inthebytes.stacklunch.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inthebytes.stacklunch.object.entity.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant, String> {

}
