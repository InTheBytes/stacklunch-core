package com.inthebytes.stacklunch.app.datatest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
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
import com.inthebytes.stacklunch.data.delivery.Delivery;
import com.inthebytes.stacklunch.data.delivery.DeliveryDto;
import com.inthebytes.stacklunch.data.delivery.DeliveryRepository;
import com.inthebytes.stacklunch.data.driver.Driver;
import com.inthebytes.stacklunch.data.driver.DriverDto;
import com.inthebytes.stacklunch.data.food.Food;
import com.inthebytes.stacklunch.data.location.Location;
import com.inthebytes.stacklunch.data.order.Order;
import com.inthebytes.stacklunch.data.order.OrderDto;
import com.inthebytes.stacklunch.data.restaurant.Restaurant;
import com.inthebytes.stacklunch.data.role.Role;
import com.inthebytes.stacklunch.data.user.User;

@ContextConfiguration(classes = DataTestConfiguration.class)
@DataJpaTest
@EntityScan(basePackageClasses = {
		Delivery.class, Order.class, Driver.class, User.class, Role.class, Location.class, Restaurant.class, Food.class})
@EnableJpaRepositories(basePackageClasses = DeliveryRepository.class)
class DeliveryDataTest {
	
	@Autowired
	DeliveryRepository deliveryRepo;
	
	private static final Long halfHourInMilliSeconds = (long) (1000 * 60 * 30);
	private static final Timestamp sampleStartTime = new Timestamp(System.currentTimeMillis());
	private static final Timestamp samplePickupTime = new Timestamp(sampleStartTime.getTime() + halfHourInMilliSeconds);
	private static final Timestamp sampleDeliverTime = new Timestamp(samplePickupTime.getTime() + halfHourInMilliSeconds);
	private static final Order sampleOrder = generateTestOrder();
	private static final Driver sampleDriver = generateTestDriver();
	
	private static final String savedDeliveryId = "delivery-id-123";
	private static final String savedOrderId = "order-id-123";
	private static final String savedDriverId = "driver-table-id-123";
	private static final Timestamp savedStartTime = Timestamp.valueOf("2021-01-01 01:05:00");
	private static final Timestamp savedPickupTime = Timestamp.valueOf("2021-01-01 01:30:00");
	private static final Timestamp savedDeliverTime = Timestamp.valueOf("2021-01-01 01:45:00");
	
	private static Order generateTestOrder() {
		Order order = new Order();
		order.setOrderId("order-id");
		order.setCustomer(new User());
		order.setRestaurant(new Restaurant());
		order.setDestination(new Location());
		order.setStatus(0);
		order.setWindowStart(new Timestamp(sampleStartTime.getTime() - halfHourInMilliSeconds));
		order.setWindowEnd(new Timestamp(sampleDeliverTime.getTime() + halfHourInMilliSeconds));
		order.setSpecialInstructions("");
		return order;
	}
	
	private static Driver generateTestDriver() {
		Driver driver = new Driver();
		driver.setDriverId("driver-id");
		driver.setUser(new User());
		driver.setVehicleId("vehicle-id");
		driver.setFinancialId("financial-id");
		driver.setStatus(0);
		return driver;
	}

	private void manuallySetDeliveryProperties(Delivery delivery) {
		delivery.setDriver(sampleDriver);
		delivery.setOrder(sampleOrder);
		delivery.setStartTime(sampleStartTime);
		delivery.setPickupTime(samplePickupTime);
		delivery.setDeliverTime(sampleDeliverTime);
	}
	
	private Delivery generateTestDeliveryEntity() {
		Delivery delivery = new Delivery();
		manuallySetDeliveryProperties(delivery);
		return delivery;
	}
	
	private DeliveryDto generateTestDeliveryDto() {
		return DeliveryDto.convert(generateTestDeliveryEntity());
	}
	
	private Delivery getSavedDelivery() {
		return deliveryRepo.findById(savedDeliveryId).get();
	}
	
	@Test
	void testDtoGettersAndSetters() {
		DeliveryDto testDto = generateTestDeliveryDto();
		assertEquals(sampleStartTime, testDto.getStartTime());
		assertEquals(samplePickupTime, testDto.getPickupTime());
		assertEquals(sampleDeliverTime, testDto.getDeliverTime());
		assertEquals(OrderDto.convert(sampleOrder), testDto.getOrder());
		assertEquals(DriverDto.convert(sampleDriver), testDto.getDriver());
	}
	
	@Test
	void testEntityGettersAndSetters() {
		Delivery testEntity = generateTestDeliveryEntity();
		assertEquals(sampleStartTime, testEntity.getStartTime());
		assertEquals(samplePickupTime, testEntity.getPickupTime());
		assertEquals(sampleDeliverTime, testEntity.getDeliverTime());
		assertEquals(sampleOrder, testEntity.getOrder());
		assertEquals(sampleDriver, testEntity.getDriver());
	}
	
	@Test
	void testDtoHashCodeEqualsAndToString() {
		DeliveryDto testDto = generateTestDeliveryDto();
		DeliveryDto anotherDto = generateTestDeliveryDto();
		
		assertEquals(testDto.hashCode(), anotherDto.hashCode());
		assertNotEquals(testDto.hashCode(), (new DeliveryDto()).hashCode());
		
		assertTrue(testDto.equals(anotherDto));
		assertFalse(testDto.equals(new DeliveryDto()));
		
		assertEquals(testDto.toString(), anotherDto.toString());
		assertNotEquals(testDto.toString(), (new DeliveryDto()).toString());
	}
	
	@Test
	void testEntityHashCodeEqualsAndToString() {
		Delivery testEntity = generateTestDeliveryEntity();
		Delivery anotherEntity = generateTestDeliveryEntity();
		
		assertEquals(testEntity.hashCode(), anotherEntity.hashCode());
		assertNotEquals(testEntity.hashCode(), (new DeliveryDto()).hashCode());
		
		assertTrue(testEntity.equals(anotherEntity));
		assertFalse(testEntity.equals(new Delivery()));
		
		assertEquals(testEntity.toString(), anotherEntity.toString());
		assertNotEquals(testEntity.toString(), (new DeliveryDto()).toString());	
	}
	
	@Test
	void testDtoConvertMethods() {
		DeliveryDto testDto = generateTestDeliveryDto();
		Delivery testEntity = generateTestDeliveryEntity();
		
		assertEquals(testEntity, testDto.convert());
		assertEquals(testDto, DeliveryDto.convert(testEntity));
		
		List<Delivery> entities = new ArrayList<>();
		entities.add(testEntity);
		Page<DeliveryDto> dtoPage = DeliveryDto.convert(new PageImpl<>(entities));

		assertEquals(1, dtoPage.getTotalPages());
		assertEquals(1L, dtoPage.getTotalElements());
		assertEquals(testDto, dtoPage.getContent().get(0));
	}
	
	@Test
	void testRepositoryGetDeliveryById() {
		Optional<Delivery> delivery = deliveryRepo.findById(savedDeliveryId);
		assertTrue(delivery.isPresent());
		assertEquals(savedOrderId, delivery.get().getOrder().getOrderId());
		assertEquals(savedDriverId, delivery.get().getDriver().getDriverId());
		assertEquals(savedStartTime, delivery.get().getStartTime());
		assertEquals(savedPickupTime, delivery.get().getPickupTime());
		assertEquals(savedDeliverTime, delivery.get().getDeliverTime());
	}
	
	@Test
	void testRepositoryUpdateOperation() {
		Delivery delivery = getSavedDelivery();
		delivery.setStartTime(sampleStartTime);
		delivery.setPickupTime(samplePickupTime);
		delivery.setDeliverTime(sampleDeliverTime);
		delivery = deliveryRepo.save(delivery);
		
		assertEquals(sampleStartTime, delivery.getStartTime());
		assertEquals(samplePickupTime, delivery.getPickupTime());
		assertEquals(sampleDeliverTime, delivery.getDeliverTime());
	}
	
	@Test
	void testRepositoryDeleteOperation() {
		deliveryRepo.delete(getSavedDelivery());
		Optional<Delivery> delivery = deliveryRepo.findById(savedDeliveryId);
		assertFalse(delivery.isPresent());
	}
	
	@Test
	void testRepositoryCreateOperation() {
		Delivery delivery = generateTestDeliveryEntity();
		String deliveryId = deliveryRepo.save(delivery).getDeliveryId();
		
		Optional<Delivery> newDelivery = deliveryRepo.findById(deliveryId);
		assertTrue(newDelivery.isPresent());
		assertEquals(sampleStartTime, newDelivery.get().getStartTime());
	}

}
