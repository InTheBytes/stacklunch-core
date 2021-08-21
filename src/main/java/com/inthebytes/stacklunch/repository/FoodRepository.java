package com.inthebytes.stacklunch.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inthebytes.stacklunch.object.entity.Food;

public interface FoodRepository extends JpaRepository<Food, String> {

}
