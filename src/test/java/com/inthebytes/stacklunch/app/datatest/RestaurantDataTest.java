package com.inthebytes.stacklunch.app.datatest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
import com.inthebytes.stacklunch.data.location.Location;
import com.inthebytes.stacklunch.data.location.LocationDto;
import com.inthebytes.stacklunch.data.restaurant.Restaurant;
import com.inthebytes.stacklunch.data.restaurant.RestaurantDto;
import com.inthebytes.stacklunch.data.restaurant.RestaurantRepository;
import com.inthebytes.stacklunch.data.role.Role;
import com.inthebytes.stacklunch.data.user.User;
import com.inthebytes.stacklunch.data.user.UserDto;

@ContextConfiguration(classes = DataTestConfiguration.class)
@DataJpaTest
@EntityScan(basePackageClasses = {Restaurant.class, Location.class, Food.class, User.class, Role.class})
@EnableJpaRepositories(basePackageClasses = RestaurantRepository.class)
class RestaurantDataTest {
	
	@Autowired
	RestaurantRepository restaurantRepo;
	
	private final static String sampleName = "Unit Test Restaurant";
	private final static String sampleCuisine = "Unit Test";
	
	private final static String savedRestaurantId = "restaurant-id-123";
	
	private final static String savedName = "Test Restaurant";
	private final static String savedCuisine = "Test";
	private final static int savedFoodCount = 3;
	private final static int savedManagerCount = 1;
	private final static String savedLocationId = "restaurant-location-id";
	
	private void manuallySetTestProperties(Restaurant entity) {
		entity.setName(sampleName);
		entity.setCuisine(sampleCuisine);
		entity.setLocation(generateTestLocation());
		entity.setFoods(generateTestFoodSet());
		entity.setManager(generateTestManagerSet());
	}
	
	private Set<Food> generateTestFoodSet() {
		return new HashSet<>();
	}
	
	private Set<User> generateTestManagerSet() {
		User user = new User();
		Set<User> managers = new HashSet<>();
		managers.add(user);
		return managers;
	}
	
	private Location generateTestLocation() {
		Location location = new Location();
		location.setUnit("0");
		location.setStreet("Test St.");
		location.setCity("Test");
		location.setCity("TS");
		location.setZipCode(99999);
		return location;
	}
	
	private Restaurant generateTestRestaurantEntity() {
		Restaurant entity = new Restaurant();
		manuallySetTestProperties(entity);
		return entity;
	}
	
	private RestaurantDto generateTestRestaurantDto() {
		return RestaurantDto.convert(generateTestRestaurantEntity());
	}
	
	private Optional<Restaurant> getOptionalSavedRestaurant() {
		return restaurantRepo.findById(savedRestaurantId);
	}
	
	private Restaurant getSavedRestaurant() {
		return getOptionalSavedRestaurant().get();
	}

	@Test
	void testDtoGettersAndSetters() {
		RestaurantDto testDto = generateTestRestaurantDto();
		assertEquals(sampleName, testDto.getName());
		assertEquals(sampleCuisine, testDto.getCuisine());
		assertEquals(LocationDto.convert(generateTestLocation()), testDto.getLocation());
		
		Set<FoodDto> dtoFoodSet = generateTestFoodSet().stream().map(FoodDto::convert).collect(Collectors.toSet());
		Set<UserDto> dtoManagerSet = generateTestManagerSet().stream().map(UserDto::convert).collect(Collectors.toSet());
		assertEquals(dtoFoodSet, testDto.getFoods());
		assertEquals(dtoManagerSet, testDto.getManager());
	}
	
	@Test
	void testEntityGettersAndSetters() {
		Restaurant testEntity = generateTestRestaurantEntity();
		assertEquals(sampleName, testEntity.getName());
		assertEquals(sampleCuisine, testEntity.getCuisine());
		assertEquals(generateTestLocation(), testEntity.getLocation());
		assertEquals(generateTestFoodSet(), testEntity.getFoods());
		assertEquals(generateTestManagerSet(), testEntity.getManager());
	}
	
	@Test
	void testDtoHashCodeEqualsAndToString() {
		RestaurantDto testDto = generateTestRestaurantDto();
		RestaurantDto anotherDto = generateTestRestaurantDto();
		
		assertEquals(testDto.hashCode(), anotherDto.hashCode());
		assertNotEquals(testDto.hashCode(), (new RestaurantDto()).hashCode());
		
		assertEquals(testDto.toString(), anotherDto.toString());
		assertNotEquals(testDto.toString(), (new RestaurantDto()).toString());
		
		assertTrue(testDto.equals(anotherDto));
		assertFalse(testDto.equals(new RestaurantDto()));
	}
	
	@Test
	void testEntityHashCodeEqualsAndToString() {
		Restaurant testEntity = generateTestRestaurantEntity();
		Restaurant anotherEntity = generateTestRestaurantEntity();
		
		assertEquals(testEntity.hashCode(), anotherEntity.hashCode());
		assertNotEquals(testEntity.hashCode(), (new Restaurant()).hashCode());
		
		assertEquals(testEntity.toString(), anotherEntity.toString());
		assertNotEquals(testEntity.toString(), (new Restaurant()).toString());
		
		assertTrue(testEntity.equals(anotherEntity));
		assertFalse(testEntity.equals(new Restaurant()));
	}
	
	@Test
	void testDtoConvertMethods() {
		RestaurantDto testDto = generateTestRestaurantDto();
		Restaurant testEntity = generateTestRestaurantEntity();
		
		assertEquals(testEntity, testDto.convert());
		assertEquals(testDto, RestaurantDto.convert(testEntity));
		
		List<Restaurant> entities = new ArrayList<>();
		entities.add(testEntity);
		Page<RestaurantDto> dtoPage = RestaurantDto.convert(new PageImpl<>(entities));
		assertEquals(1, dtoPage.getTotalElements());
		assertEquals(1L, dtoPage.getTotalElements());
		assertEquals(testDto, dtoPage.getContent().get(0));
	}
	
	@Test
	void testRepositoryGetByIdOperation() {
		Optional<Restaurant> restaurant = getOptionalSavedRestaurant();
		assertTrue(restaurant.isPresent());
		assertEquals(savedName, restaurant.get().getName());
		assertEquals(savedCuisine, restaurant.get().getCuisine());
		assertEquals(savedFoodCount, restaurant.get().getFoods().size());
		assertEquals(savedManagerCount, restaurant.get().getManager().size());
		assertEquals(savedLocationId, restaurant.get().getLocation().getLocationId());
	}
	
	@Test
	void testRepositoryUpdateOperation() {
		Restaurant entity = generateTestRestaurantEntity();
		entity.setRestaurantId(savedRestaurantId);
		restaurantRepo.save(entity);
		entity = getSavedRestaurant();
		assertEquals(sampleName, entity.getName());
		assertEquals(sampleCuisine, entity.getCuisine());
		assertEquals(generateTestFoodSet(), entity.getFoods());
		assertEquals(generateTestManagerSet(), entity.getManager());
		
		Location newSavedLocation = generateTestLocation();
		newSavedLocation.setLocationId(entity.getLocation().getLocationId());
		assertEquals(newSavedLocation, entity.getLocation());
		
		assertNotEquals(savedLocationId, newSavedLocation.getLocationId());
	}
	
	@Test
	void testRepositoryDeleteOperation() {
		restaurantRepo.delete(getSavedRestaurant());
		Optional<Restaurant> restaurant = getOptionalSavedRestaurant();
		assertFalse(restaurant.isPresent());
	}
	
	@Test
	void testRepositoryCreateOperation() {
		String restaurantId = restaurantRepo.save(generateTestRestaurantEntity()).getRestaurantId();
		Optional<Restaurant> entity = restaurantRepo.findById(restaurantId);
		assertTrue(entity.isPresent());
		assertEquals(sampleName, entity.get().getName());
		assertEquals(sampleCuisine, entity.get().getCuisine());
		assertEquals(generateTestFoodSet(), entity.get().getFoods());
		assertEquals(generateTestManagerSet(), entity.get().getManager());
	}
}
