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
import com.inthebytes.stacklunch.data.driver.Driver;
import com.inthebytes.stacklunch.data.driver.DriverDto;
import com.inthebytes.stacklunch.data.driver.DriverRepository;
import com.inthebytes.stacklunch.data.role.Role;
import com.inthebytes.stacklunch.data.user.User;
import com.inthebytes.stacklunch.data.user.UserDto;
import com.inthebytes.stacklunch.data.user.UserRepository;

@ContextConfiguration(classes = DataTestConfiguration.class)
@DataJpaTest
@EntityScan(basePackageClasses = {Driver.class, User.class, Role.class})
@EnableJpaRepositories(basePackageClasses = {DriverRepository.class, UserRepository.class})
class DriverDataTest {
	
	@Autowired
	DriverRepository driverRepo;
	
	@Autowired
	UserRepository userRepo;
	
	private static final String sampleDriverId = "test-driver-id";
	private static final String sampleVehicleId = "test-vehicle-id";
	private static final String sampleFinancialId = "test-financial-id";
	private static final Integer sampleStatus = 0;

	private static final String sampleUserId = "customer-id-123";
	private static final String savedDriverUserId = "driver-id-123";
	
	private static final String savedDriverId = "driver-table-id-123";
	private static final String savedVehicleId = "vehicle-id-123";
	private static final String savedFinancialId = "financial-id-123";
	private static final Integer savedStatus = 1;
	
	private User getUser(String id) {
		return userRepo.findById(id).get();
	}

	private User getTestDriverUserAccount() {
		return getUser(sampleUserId);
	}
	
	private User getSavedDriverUserAccount() {
		return getUser(savedDriverUserId);
	}
	
	private void manuallySetDriverProperties(Driver driver) {
		driver.setUser(getTestDriverUserAccount());
		driver.setDriverId(sampleDriverId);
		driver.setVehicleId(sampleVehicleId);
		driver.setFinancialId(sampleFinancialId);
		driver.setStatus(sampleStatus);
	}
	
	private Driver generateTestDriverEntity() {
		Driver driver = new Driver();
		manuallySetDriverProperties(driver);
		return driver;
	}
	
	private DriverDto generateTestDriverDto() {
		return DriverDto.convert(generateTestDriverEntity());
	}

	private Driver getSavedDriver() {
		return driverRepo.findById(savedDriverId).get();
	}
	
	@Test
	void testDtoGettersAndSetters() {
		DriverDto testDto = generateTestDriverDto();
		assertEquals(sampleDriverId, testDto.getDriverId());
		assertEquals(sampleVehicleId, testDto.getVehicleId());
		assertEquals(sampleFinancialId, testDto.getFinancialId());
		assertEquals(sampleStatus, testDto.getStatus());
		assertEquals(UserDto.convert(getTestDriverUserAccount()), testDto.getUser());
	}
	
	@Test
	void testEntityGettersAndSetters() {
		Driver testEntity = generateTestDriverEntity();
		assertEquals(sampleDriverId, testEntity.getDriverId());
		assertEquals(sampleVehicleId, testEntity.getVehicleId());
		assertEquals(sampleFinancialId, testEntity.getFinancialId());
		assertEquals(sampleStatus, testEntity.getStatus());
		assertEquals(getTestDriverUserAccount(), testEntity.getUser());
	}
	
	@Test
	void testDtoHashCodeEqualsAndToString() {
		DriverDto testDto = generateTestDriverDto();
		DriverDto anotherDto = generateTestDriverDto();
		
		assertEquals(testDto.hashCode(), anotherDto.hashCode());
		assertNotEquals(testDto.hashCode(), (new DriverDto()).hashCode());
		
		assertTrue(testDto.equals(anotherDto));
		assertFalse(testDto.equals(new DriverDto()));
		
		assertEquals(testDto.toString(), anotherDto.toString());
		assertNotEquals(testDto.toString(), (new DriverDto()).toString());
	}
	
	@Test
	void testEntityHashCodeEqualsAndToString() {
		Driver testEntity = generateTestDriverEntity();
		Driver anotherEntity = generateTestDriverEntity();
		
		assertEquals(testEntity.hashCode(), anotherEntity.hashCode());
		assertNotEquals(testEntity.hashCode(), (new DriverDto()).hashCode());
		
		assertTrue(testEntity.equals(anotherEntity));
		assertFalse(testEntity.equals(new Driver()));
		
		assertEquals(testEntity.toString(), anotherEntity.toString());
		assertNotEquals(testEntity.toString(), (new DriverDto()).toString());
	}
	
	@Test
	void testDtoConvertMethods() {
		DriverDto testDto = generateTestDriverDto();
		Driver testEntity = generateTestDriverEntity();
		
		testEntity.getUser().setPassword(null);
		assertEquals(testEntity, testDto.convert());
		assertEquals(testDto, DriverDto.convert(testEntity));
		
		List<Driver> entities = new ArrayList<>();
		entities.add(testEntity);
		Page<DriverDto> dtoPage = DriverDto.convert(new PageImpl<>(entities));
		assertEquals(1, dtoPage.getTotalPages());
		assertEquals(1L, dtoPage.getTotalElements());
		assertEquals(testDto, dtoPage.getContent().get(0));
	}
	
	@Test
	void testRepositoryGetByIdOperation() {
		Optional<Driver> driver = driverRepo.findById(savedDriverId);
		assertTrue(driver.isPresent());
		assertEquals(savedVehicleId, driver.get().getVehicleId());
		assertEquals(savedFinancialId, driver.get().getFinancialId());
		assertEquals(savedStatus, driver.get().getStatus());
		assertEquals(getSavedDriverUserAccount(), driver.get().getUser());
	}
	
	
	@Test
	void testRepositoryUpdateOperation() {
		Driver driver = generateTestDriverEntity();
		driver.setDriverId(savedDriverId);
		driverRepo.save(driver);
		
		driver = getSavedDriver();
		assertEquals(sampleVehicleId, driver.getVehicleId());
		assertNotEquals(savedVehicleId, driver.getVehicleId());
		assertEquals(sampleFinancialId, driver.getFinancialId());
		assertNotEquals(savedFinancialId, driver.getFinancialId());
		assertEquals(sampleStatus, driver.getStatus());
		assertNotEquals(savedStatus, driver.getStatus());
		assertEquals(getTestDriverUserAccount(), driver.getUser());
		assertNotEquals(getSavedDriverUserAccount(), driver.getUser());
	}
	
	@Test
	void testRepositoryDeleteOperation() {
		driverRepo.delete(getSavedDriver());
		Optional<Driver> driver = driverRepo.findById(savedDriverId);
		assertFalse(driver.isPresent());
	}
	
	@Test
	void testRepositoryCreateDriverOperation() {
		String driverId = driverRepo.save(generateTestDriverEntity()).getDriverId();
		Optional<Driver> driver = driverRepo.findById(driverId);
		assertTrue(driver.isPresent());
		assertEquals(sampleVehicleId, driver.get().getVehicleId());
		assertEquals(sampleFinancialId, driver.get().getFinancialId());
		assertEquals(sampleStatus, driver.get().getStatus());
		assertEquals(getTestDriverUserAccount(), driver.get().getUser());
	}
}
