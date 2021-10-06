package com.inthebytes.stacklunch.app.datatest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;

import com.inthebytes.stacklunch.app.stub.DataTestConfiguration;
import com.inthebytes.stacklunch.data.food.Food;
import com.inthebytes.stacklunch.data.food.FoodDto;
import com.inthebytes.stacklunch.data.food.FoodRepository;
import com.inthebytes.stacklunch.data.location.Location;
import com.inthebytes.stacklunch.data.restaurant.Restaurant;
import com.inthebytes.stacklunch.data.restaurant.RestaurantDto;
import com.inthebytes.stacklunch.data.restaurant.RestaurantRepository;
import com.inthebytes.stacklunch.data.role.Role;
import com.inthebytes.stacklunch.data.user.User;

@ContextConfiguration(classes = DataTestConfiguration.class)
@DataJpaTest
@EntityScan(basePackageClasses = {Food.class, Restaurant.class, Location.class, User.class, Role.class})
@EnableJpaRepositories(basePackageClasses = {FoodRepository.class, RestaurantRepository.class})
class FoodDataTest {
	
	@Autowired
	FoodRepository foodRepo;
	
	@Autowired
	RestaurantRepository restaurantRepo;
	
	private static final String sampleName = "Test Food";
	private static final Double samplePrice = 10.60;
	private static final String sampleDescription = "A food just for integration testing";
	
	private static final String savedRestaurantId = "restaurant-id-123";
	
	private static final String savedFoodId = "food-id-123";
	private static final String savedName = "Test Burger";
	private static final Double savedPrice = 3.50;
	private static final String savedDescription = "A burger for testing";
	
	private Restaurant getSavedRestaurant() {
		Restaurant restaurant = restaurantRepo.findById(savedRestaurantId).get();
		removeAllRestaurantManagerPasswords(restaurant);
		return restaurant;
	}

	private void removeAllRestaurantManagerPasswords(Restaurant restaurant) {
		restaurant.getManager().stream().forEach(managerAccount -> managerAccount.setPassword(null));
	}
	
	private void manuallySetTestFoodProperties(Food food) {
		food.setName(sampleName);
		food.setPrice(samplePrice);
		food.setDescription(sampleDescription);
		food.setRestaurant(getSavedRestaurant());
	}
	
	private Food generateTestFoodEntity() {
		Food entity = new Food();
		manuallySetTestFoodProperties(entity);
		return entity;
	}
	
	private FoodDto generateTestFoodDto() {
		return FoodDto.convert(generateTestFoodEntity());
	}
	
	private Food getSavedFood() {
		return foodRepo.findById(savedFoodId).get();
	}

	@Test
	void testDtoGettersAndSetters() {
		FoodDto testDto = generateTestFoodDto();
		assertEquals(sampleName, testDto.getName());
		assertEquals(samplePrice, testDto.getPrice());
		assertEquals(sampleDescription, testDto.getDescription());
		RestaurantDto restaurantDto = RestaurantDto.convert(getSavedRestaurant());
		restaurantDto.setFoods(null);
		assertEquals(restaurantDto, testDto.getRestaurant());
	}
	
	@Test
	void testEntityGettersAndSetters() {
		Food testEntity = generateTestFoodEntity();
		assertEquals(sampleName, testEntity.getName());
		assertEquals(samplePrice, testEntity.getPrice());
		assertEquals(sampleDescription, testEntity.getDescription());
		assertEquals(getSavedRestaurant(), testEntity.getRestaurant());
	}
	
	@Test
	void testDtoHashCodeEqualsAndToString() {
		FoodDto testDto = generateTestFoodDto();
		FoodDto anotherDto = generateTestFoodDto();
		
		assertEquals(testDto.hashCode(), anotherDto.hashCode());
		assertTrue(testDto.equals(anotherDto));
		assertEquals(testDto.toString(), anotherDto.toString());
		
		assertNotEquals(testDto.hashCode(), (new FoodDto()).hashCode());
		assertFalse(testDto.equals(new FoodDto()));
		assertNotEquals(testDto.toString(), (new FoodDto()).toString());
	}
	
	@Test
	void testEntityHashCodeEqualsAndToString() {
		Food testEntity = generateTestFoodEntity();
		Food anotherEntity = generateTestFoodEntity();
		
		assertEquals(testEntity.hashCode(), anotherEntity.hashCode());
		assertTrue(testEntity.equals(anotherEntity));
		assertEquals(testEntity.toString(), anotherEntity.toString());
		
		assertNotEquals(testEntity.hashCode(), (new Food()).hashCode());
		assertFalse(testEntity.equals(new Food()));
		assertNotEquals(testEntity.toString(), (new Food()).toString());
	}
	
	@Test
	void testDtoConvertMethods() {
		FoodDto testDto = generateTestFoodDto();
		Food testEntity = generateTestFoodEntity();
		
		assertEquals(testEntity, testDto.convert());
		assertEquals(testDto, FoodDto.convert(testEntity));
		
		List<Food> entities = new ArrayList<>();
		entities.add(testEntity);
		Page<FoodDto> dtoPage = FoodDto.convert(new PageImpl<>(entities));
		assertEquals(1, dtoPage.getTotalPages());
		assertEquals(1L, dtoPage.getTotalElements());
		assertEquals(testDto, dtoPage.getContent().get(0));
	}
	
	@Test
	void testRepositoryGetByIdOperation() {
		Optional<Food> food = foodRepo.findById(savedFoodId);
		assertTrue(food.isPresent());
		assertEquals(savedName, food.get().getName());
		assertEquals(savedPrice, food.get().getPrice());
		assertEquals(savedDescription, food.get().getDescription());
	}
	
	@Test
	void testRepositoryUpdateOperation() {
		Food food = generateTestFoodEntity();
		food.setFoodId(savedFoodId);
		foodRepo.save(food);
		food = getSavedFood();
		assertEquals(sampleName, food.getName());
		assertEquals(samplePrice, food.getPrice());
		assertEquals(sampleDescription, food.getDescription());
	}
	
	@Test
	void testRepositoryDeleteOperation() {
		foodRepo.delete(getSavedFood());
		Optional<Food> deletedFood = foodRepo.findById(savedFoodId);
		assertFalse(deletedFood.isPresent());
	}
	
	@Test
	void testRepositoryCreateOperation() {
		String foodId = foodRepo.save(generateTestFoodEntity()).getFoodId();
		Optional<Food> savedFood = foodRepo.findById(foodId);
		assertTrue(savedFood.isPresent());
		assertEquals(sampleName, savedFood.get().getName());
	}
}
