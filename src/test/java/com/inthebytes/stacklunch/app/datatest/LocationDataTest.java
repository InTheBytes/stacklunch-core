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
import com.inthebytes.stacklunch.data.location.Location;
import com.inthebytes.stacklunch.data.location.LocationDto;
import com.inthebytes.stacklunch.data.location.LocationRepository;

@ContextConfiguration(classes = DataTestConfiguration.class)
@DataJpaTest
@EntityScan(basePackageClasses = Location.class)
@EnableJpaRepositories(basePackageClasses = LocationRepository.class)
class LocationDataTest {

	@Autowired
	LocationRepository locationRepo;
	
	private final static String sampleUnit = "321";
	private final static String sampleStreet = "Somewhere Rd.";
	private final static String sampleCity = "Nowhere";
	private final static String sampleState = "AV";
	private final static Integer sampleZipCode = 99999;
	
	private final static String savedLocationId = "restaurant-location-id";
	private final static String savedUnit = "123";
	private final static String savedStreet = "Somewhere St.";
	private final static String savedCity = "Somewhere";
	private final static String savedState = "VA";
	private final static Integer savedZipCode = 11111;
	
	private void manuallySetTestProperties(Location location) {
		location.setUnit(sampleUnit);
		location.setStreet(sampleStreet);
		location.setCity(sampleCity);
		location.setState(sampleState);
		location.setZipCode(sampleZipCode);
	}
	
	private Location generateTestLocationEntity() {
		Location entity = new Location();
		manuallySetTestProperties(entity);
		return entity;
	}
	
	private LocationDto generateTestLocationDto() {
		return LocationDto.convert(generateTestLocationEntity());
	}
	
	private Optional<Location> getOptionalSavedLocation() {
		return locationRepo.findById(savedLocationId);
	}
	
	private Location getSavedLocation() {
		return getOptionalSavedLocation().get();
	}
	
	@Test
	void testDtoGettersAndSetters() {
		LocationDto testDto = generateTestLocationDto();
		assertEquals(sampleUnit, testDto.getUnit());
		assertEquals(sampleStreet, testDto.getStreet());
		assertEquals(sampleCity, testDto.getCity());
		assertEquals(sampleState, testDto.getState());
		assertEquals(sampleZipCode, testDto.getZipCode());
	}
	
	@Test
	void testEntityGettersAndSetters() {
		Location testEntity = generateTestLocationEntity();
		assertEquals(sampleUnit, testEntity.getUnit());
		assertEquals(sampleStreet, testEntity.getStreet());
		assertEquals(sampleCity, testEntity.getCity());
		assertEquals(sampleState, testEntity.getState());
		assertEquals(sampleZipCode, testEntity.getZipCode());
	}
	
	@Test
	void testDtoHashCodeEqualsAndToString() {
		LocationDto testDto = generateTestLocationDto();
		LocationDto anotherDto = generateTestLocationDto();
		
		assertEquals(testDto.hashCode(), anotherDto.hashCode());
		assertEquals(testDto.toString(), anotherDto.toString());
		assertTrue(testDto.equals(anotherDto));
		
		assertNotEquals(testDto.hashCode(), (new LocationDto()).hashCode());
		assertNotEquals(testDto.toString(), (new LocationDto()).hashCode());
		assertFalse(testDto.equals(new LocationDto()));
	}
	
	@Test
	void testEntityHashCodeEqualsAndToString() {
		Location testEntity = generateTestLocationEntity();
		Location anotherEntity = generateTestLocationEntity();
		
		assertEquals(testEntity.hashCode(), anotherEntity.hashCode());
		assertEquals(testEntity.toString(), anotherEntity.toString());
		assertTrue(testEntity.equals(anotherEntity));
		
		assertNotEquals(testEntity.hashCode(), (new Location()).hashCode());
		assertNotEquals(testEntity.toString(), (new Location()).hashCode());
		assertFalse(testEntity.equals(new Location()));
	}
	
	@Test
	void testDtoConvertMethods() {
		LocationDto testDto = generateTestLocationDto();
		Location testEntity = generateTestLocationEntity();
		
		assertEquals(testEntity, testDto.convert());
		assertEquals(testDto, LocationDto.convert(testEntity));
		
		List<Location> entities = new ArrayList<>();
		entities.add(testEntity);
		Page<LocationDto> dtoPage = LocationDto.convert(new PageImpl<>(entities));
		assertEquals(1, dtoPage.getTotalPages());
		assertEquals(1L, dtoPage.getTotalElements());
		assertEquals(testDto, dtoPage.getContent().get(0));
	}
	
	@Test
	void testRepositoryGetByIdOperation() {
		Optional<Location> location = getOptionalSavedLocation();
		assertTrue(location.isPresent());
		assertEquals(savedUnit, location.get().getUnit());
		assertEquals(savedStreet, location.get().getStreet());
		assertEquals(savedCity, location.get().getCity());
		assertEquals(savedState, location.get().getState());
		assertEquals(savedZipCode, location.get().getZipCode());
	}
	
	@Test
	void testRepositoryUpdateOperation() {
		Location location = generateTestLocationEntity();
		location.setLocationId(savedLocationId);
		locationRepo.save(location);
		location = getSavedLocation();
		assertEquals(sampleUnit, location.getUnit());
		assertNotEquals(savedUnit, location.getUnit());
		assertEquals(sampleStreet, location.getStreet());
		assertNotEquals(savedStreet, location.getStreet());
		assertEquals(sampleCity, location.getCity());
		assertNotEquals(savedCity, location.getCity());
		assertEquals(sampleState, location.getState());
		assertNotEquals(savedState, location.getState());
		assertEquals(sampleZipCode, location.getZipCode());
		assertNotEquals(savedZipCode, location.getZipCode());
	}
	
	@Test
	void testRepositoryDeleteOperation() {
		locationRepo.delete(getSavedLocation());
		Optional<Location> location = getOptionalSavedLocation();
		assertFalse(location.isPresent());
	}
	
	@Test
	void testRepositoryCreateOperation() {
		String locationId = locationRepo.save(generateTestLocationEntity()).getLocationId();
		Optional<Location> savedLocation = locationRepo.findById(locationId);
		assertTrue(savedLocation.isPresent());
		assertEquals(sampleUnit, savedLocation.get().getUnit());
		assertEquals(sampleStreet, savedLocation.get().getStreet());
		assertEquals(sampleCity, savedLocation.get().getCity());
		assertEquals(sampleState, savedLocation.get().getState());
		assertEquals(sampleZipCode, savedLocation.get().getZipCode());
	}
}
