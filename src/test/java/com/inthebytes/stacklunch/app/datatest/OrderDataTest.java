package com.inthebytes.stacklunch.app.datatest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
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
import com.inthebytes.stacklunch.data.location.Location;
import com.inthebytes.stacklunch.data.location.LocationDto;
import com.inthebytes.stacklunch.data.location.LocationRepository;
import com.inthebytes.stacklunch.data.order.Order;
import com.inthebytes.stacklunch.data.order.OrderDto;
import com.inthebytes.stacklunch.data.order.OrderFood;
import com.inthebytes.stacklunch.data.order.OrderRepository;
import com.inthebytes.stacklunch.data.restaurant.Restaurant;
import com.inthebytes.stacklunch.data.restaurant.RestaurantDto;
import com.inthebytes.stacklunch.data.restaurant.RestaurantRepository;
import com.inthebytes.stacklunch.data.role.Role;
import com.inthebytes.stacklunch.data.user.User;
import com.inthebytes.stacklunch.data.user.UserDto;
import com.inthebytes.stacklunch.data.user.UserRepository;

@ContextConfiguration(classes = DataTestConfiguration.class)
@DataJpaTest
@EntityScan(basePackageClasses = 
	{Order.class, User.class, Role.class, Restaurant.class, Location.class, OrderFood.class, Food.class})
@EnableJpaRepositories(basePackageClasses = {OrderRepository.class, LocationRepository.class, RestaurantRepository.class, UserRepository.class})
class OrderDataTest {
	
	@Autowired
	OrderRepository orderRepo;
	
	@Autowired
	RestaurantRepository restaurantRepo;
	
	@Autowired
	LocationRepository locationRepo;
	
	@Autowired
	UserRepository userRepo;
	
	private final static Long hourInMilliSeconds = (long) (1000 * 60 * 60);
	
	private final static Integer sampleStatus = 0;
	private final static Timestamp sampleWindowStart = Timestamp.from(Instant.now());
	private final static Timestamp sampleWindowEnd = new Timestamp(sampleWindowStart.getTime() + hourInMilliSeconds);
	private final static String sampleSpecialInstructions = "Test Order";
	
	private final static String savedOrderId = "order-id-123";
	private final static String savedCustomerId = "customer-id-123";
	private final static String savedRestaurantId = "restaurant-id-123";
	private final static String savedLocationId = "destination-location-id";
	
	private final static Integer savedStatus = 1;
	private final static Timestamp savedWindowStart = Timestamp.valueOf("2021-01-01 01:00:00");
	private final static Timestamp savedWindowEnd = Timestamp.valueOf("2021-01-01 02:00:00");
	private final static String savedSpecialInstructions = "Test the order table";
	
	public Location getTestLocation() {
		return locationRepo.findById(savedLocationId).get();
	}
	
	public User getTestCustomerUser() {
		return userRepo.findById(savedCustomerId).get();
	}
	
	public Restaurant getTestRestaurant() {
		return restaurantRepo.findById(savedRestaurantId).get();
	}
	
	private void manuallySetTestProperties(Order entity) {
		entity.setStatus(sampleStatus);
		entity.setWindowStart(sampleWindowStart);
		entity.setWindowEnd(sampleWindowEnd);
		entity.setSpecialInstructions(sampleSpecialInstructions);
		entity.setDestination(getTestLocation());
		entity.setCustomer(getTestCustomerUser());
		entity.setRestaurant(getTestRestaurant());
		entity.setFoods(generateTestFoods(entity));
	}
	
	public Set<OrderFood> generateTestFoods(Order orderContainingFood) {
		return getTestRestaurant().getFoods().stream().map(x -> {
			final OrderFood foodItem = new OrderFood();
			foodItem.setFood(x);
			foodItem.setOrder(orderContainingFood);
			foodItem.setQuantity(1);
			return foodItem;
		}).collect(Collectors.toSet());
	}
	
	private Order generateTestOrderEntity() {
		Order entity = new Order();
		manuallySetTestProperties(entity);
		return entity;
	}
	
	private OrderDto generateTestOrderDto() {
		return OrderDto.convert(generateTestOrderEntity());
	}
	
	private Optional<Order> getOptionalSavedOrder() {
		return orderRepo.findById(savedOrderId);
	}
	
	private Order getSavedOrder() {
		return getOptionalSavedOrder().get();
	}
	
	@Test
	void testDtoGettersAndSetters() {
		OrderDto testDto = generateTestOrderDto();
		assertEquals(sampleStatus, testDto.getStatus());
		assertEquals(sampleWindowStart, testDto.getWindowStart());
		assertEquals(sampleWindowEnd, testDto.getWindowEnd());
		assertEquals(sampleSpecialInstructions, testDto.getSpecialInstructions());
		assertEquals(LocationDto.convert(getTestLocation()), testDto.getDestination());
		assertEquals(UserDto.convert(getTestCustomerUser()), testDto.getCustomer());
		assertEquals(RestaurantDto.convert(getTestRestaurant()), testDto.getRestaurant());
	}
	
	@Test
	void testEntityGettersAndSetters() {
		Order testEntity = generateTestOrderEntity();
		assertEquals(sampleStatus, testEntity.getStatus());
		assertEquals(sampleWindowStart, testEntity.getWindowStart());
		assertEquals(sampleWindowEnd, testEntity.getWindowEnd());
		assertEquals(sampleSpecialInstructions, testEntity.getSpecialInstructions());
		assertEquals(getTestLocation(), testEntity.getDestination());
		assertEquals(getTestCustomerUser(), testEntity.getCustomer());
		assertEquals(getTestRestaurant(), testEntity.getRestaurant());
	}
	
	@Test
	void testDtoEqualsHashCodeAndToString() {
		OrderDto testDto = generateTestOrderDto();
		OrderDto anotherDto = generateTestOrderDto();

		assertEquals(testDto.hashCode(), anotherDto.hashCode());
		assertNotEquals(testDto.hashCode(), (new OrderDto()).hashCode());
		
		assertEquals(testDto.toString(), anotherDto.toString());
		assertNotEquals(testDto.toString(), (new OrderDto()).toString());
		
		assertEquals(testDto, anotherDto);
		assertNotEquals(testDto, new OrderDto());
	}
	
	@Test
	void testEntityEqualsHashCodeAndToString() {
		Order testEntity = generateTestOrderEntity();
		Order anotherEntity = generateTestOrderEntity();

		assertEquals(testEntity.hashCode(), anotherEntity.hashCode());
		assertNotEquals(testEntity.hashCode(), (new Order()).hashCode());
		
		assertEquals(testEntity.toString(), anotherEntity.toString());
		assertNotEquals(testEntity.toString(), (new Order()).toString());
		
		assertEquals(testEntity, anotherEntity);
		assertNotEquals(testEntity, new Order());
	}
	
	@Test
	void testDtoConvertMethods() {
		OrderDto testDto = generateTestOrderDto();
		Order testEntity = generateTestOrderEntity();
		
		assertEquals(testDto, OrderDto.convert(testEntity));
		
		List<Order> entities = new ArrayList<>();
		entities.add(testEntity);
		Page<OrderDto> dtoPage = OrderDto.convert(new PageImpl<>(entities));
		assertEquals(1, dtoPage.getTotalPages());
		assertEquals(1L, dtoPage.getTotalElements());
		assertEquals(testDto, dtoPage.getContent().get(0));
	}
	
	@Test
	void testRepositoryGetOperation() {
		Optional<Order> entity = orderRepo.findById(savedOrderId);
		assertTrue(entity.isPresent());
		assertEquals(savedStatus, entity.get().getStatus());
		assertEquals(savedWindowStart, entity.get().getWindowStart());
		assertEquals(savedWindowEnd, entity.get().getWindowEnd());
		assertEquals(savedSpecialInstructions, entity.get().getSpecialInstructions());
		assertEquals(getTestLocation(), entity.get().getDestination());
		assertEquals(getTestCustomerUser(), entity.get().getCustomer());
		assertEquals(getTestRestaurant(), entity.get().getRestaurant());
	}
	
	@Test
	void testRepositoryUpdateOperation() {
		Order newOrder = generateTestOrderEntity();
		newOrder.setOrderId(savedOrderId);
		newOrder.setFoods(getSavedOrder().getFoods());
		orderRepo.save(newOrder);
		
		Order entity = getSavedOrder();
		assertEquals(sampleStatus, entity.getStatus());
		assertEquals(sampleWindowStart, entity.getWindowStart());
		assertEquals(sampleWindowEnd, entity.getWindowEnd());
		assertEquals(sampleSpecialInstructions, entity.getSpecialInstructions());
		
		assertNotEquals(savedStatus, entity.getStatus());
		assertNotEquals(savedWindowStart, entity.getWindowStart());
		assertNotEquals(savedWindowEnd, entity.getWindowEnd());
		assertNotEquals(savedSpecialInstructions, entity.getSpecialInstructions());
	}
	
	@Test
	void testRepositoryDeleteOperation() {
		orderRepo.delete(getSavedOrder());
		Optional<Order> entity = getOptionalSavedOrder();
		assertFalse(entity.isPresent());
	}
	
	@Test
	void testRepositoryCreateOperation() {
		String orderId = orderRepo.save(generateTestOrderEntity()).getOrderId();
		Optional<Order> entity = orderRepo.findById(orderId);
		
		assertTrue(entity.isPresent());
		assertEquals(sampleStatus, entity.get().getStatus());
		assertEquals(sampleWindowStart, entity.get().getWindowStart());
		assertEquals(sampleWindowEnd, entity.get().getWindowEnd());
		assertEquals(sampleSpecialInstructions, entity.get().getSpecialInstructions());
		assertEquals(getTestLocation(), entity.get().getDestination());
		assertEquals(getTestCustomerUser(), entity.get().getCustomer());
		assertEquals(getTestRestaurant(), entity.get().getRestaurant());
	}
	
	@Test
	void testRepositorySaveableFromDtoConvert() {
		Order newOrder = generateTestOrderDto().convert();
		newOrder.getCustomer().setPassword(getTestCustomerUser().getPassword());
		newOrder.getRestaurant().setManager(getTestRestaurant().getManager());
		
		String orderId = orderRepo.save(newOrder).getOrderId();
		Optional<Order> entity = orderRepo.findById(orderId);
		
		assertTrue(entity.isPresent());
		assertEquals(sampleStatus, entity.get().getStatus());
		assertEquals(sampleWindowStart, entity.get().getWindowStart());
		assertEquals(sampleWindowEnd, entity.get().getWindowEnd());
		assertEquals(sampleSpecialInstructions, entity.get().getSpecialInstructions());
		assertEquals(getTestLocation(), entity.get().getDestination());
		assertEquals(getTestCustomerUser(), entity.get().getCustomer());
		assertEquals(getTestRestaurant(), entity.get().getRestaurant());
	}
}
