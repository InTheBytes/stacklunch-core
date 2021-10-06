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

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;

import com.inthebytes.stacklunch.app.stub.DataTestConfiguration;
import com.inthebytes.stacklunch.data.authorization.Authorization;
import com.inthebytes.stacklunch.data.authorization.AuthorizationDto;
import com.inthebytes.stacklunch.data.authorization.AuthorizationRepository;

@ContextConfiguration(classes = DataTestConfiguration.class)
@DataJpaTest
@EntityScan(basePackageClasses = Authorization.class)
@EnableJpaRepositories(basePackageClasses = AuthorizationRepository.class)
class AuthorizationDataTest {
	
	@Autowired
	AuthorizationRepository authRepo;
	
	private static final Timestamp sampleTokenTimestamp = Timestamp.from(Instant.now());
	private static final String sampleTokenName = "test-token";
	private static final Timestamp savedTokenTimestamp = Timestamp.valueOf("2021-01-01 01:00:00");
	private static final String savedTokenName = "logged-out-token";
	
	private void manuallySetPropertiesForTest(AuthorizationDto dto) {
		dto.setExpirationDate(sampleTokenTimestamp);
		dto.setToken(sampleTokenName);
	}
	
	private AuthorizationDto generateTestDto() {
		AuthorizationDto dto = new AuthorizationDto();
		manuallySetPropertiesForTest(dto);
		return dto;
	}
	
	private Authorization generateTestEntity() {
		return generateTestDto().convert();
	}
	
	private Optional<Authorization> getSavedAuthorizationToken() {
		return authRepo.findById(savedTokenName);
	}
	
	@Test
	void testDtoGettersAndSetters() {
		AuthorizationDto testDto = generateTestDto();
		assertEquals(sampleTokenName, testDto.getToken());
		assertEquals(sampleTokenTimestamp, testDto.getExpirationDate());
	}
	
	@Test
	void testEntityGettersAndSetters() {
		Authorization testEntity = generateTestEntity();
		assertEquals(sampleTokenName, testEntity.getToken());
		assertEquals(sampleTokenTimestamp, testEntity.getExpirationDate());
	}
	
	@Test
	void testDtoConvertMethods() {
		List<Authorization> entities = new ArrayList<>();
		entities.add(generateTestEntity());
		Page<Authorization> entityPage = new PageImpl<Authorization>(entities);
		Page<AuthorizationDto> dtoPage = AuthorizationDto.convert(entityPage);
		assertEquals(1, dtoPage.getTotalPages());
		assertEquals(1L, dtoPage.getTotalElements());
		assertEquals(generateTestDto(), dtoPage.getContent().get(0));
	}
	
	@Test
	void testDtotHashCodeEqualsAndString() {
		AuthorizationDto testDto = generateTestDto();
		AuthorizationDto anotherDto = generateTestDto();
		assertEquals(testDto, anotherDto);
		assertNotEquals(testDto, new AuthorizationDto());
		assertEquals(testDto.hashCode(), anotherDto.hashCode());
		assertNotEquals(testDto.hashCode(), (new AuthorizationDto()).hashCode());
		assertEquals(testDto.toString(), anotherDto.toString());
		assertNotEquals(testDto.toString(), (new AuthorizationDto()).toString());
	}
	
	@Test
	void testEntityHashCodeEqualsAndString() {
		Authorization testEntity = generateTestEntity();
		Authorization anotherEntity = generateTestEntity();
		assertEquals(testEntity, anotherEntity);
		assertNotEquals(testEntity, new Authorization());
		assertEquals(testEntity.hashCode(), anotherEntity.hashCode());
		assertNotEquals(testEntity.hashCode(), (new Authorization()).hashCode());
		assertEquals(testEntity.toString(), anotherEntity.toString());
		assertNotEquals(testEntity.toString(),(new Authorization()).toString());
	}
	
	@Test
	void testRepositoryGetTokenById() {
		Optional<Authorization> authToken = getSavedAuthorizationToken();
		assertTrue(authToken.isPresent());
		assertEquals(savedTokenTimestamp, authToken.get().getExpirationDate());
	}
	
	@Test
	void testRepositoryUpdateExistingToken() {
		Authorization authToken = getSavedAuthorizationToken().get();
		authToken.setExpirationDate(sampleTokenTimestamp);
		authToken.setToken(sampleTokenName);
		authToken = authRepo.save(authToken);
		assertEquals(sampleTokenTimestamp, authToken.getExpirationDate());
		assertEquals(sampleTokenName, authToken.getToken());
	}
	
	@Test
	void testDeleteAuthorizationToken() {
		authRepo.delete(getSavedAuthorizationToken().get());
		Optional<Authorization> authToken = getSavedAuthorizationToken();
		assertFalse(authToken.isPresent());
	}
	
	@Test
	void testCreateAuthorizationToken() {
		Authorization authToken = generateTestEntity();
		authRepo.save(authToken);
		Optional<Authorization> savedToken = authRepo.findById(sampleTokenName);
		assertTrue(savedToken.isPresent());
		assertEquals(sampleTokenTimestamp, savedToken.get().getExpirationDate());
	}

}
