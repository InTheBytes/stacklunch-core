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
import com.inthebytes.stacklunch.data.confirmation.Confirmation;
import com.inthebytes.stacklunch.data.confirmation.ConfirmationDto;
import com.inthebytes.stacklunch.data.confirmation.ConfirmationRepository;
import com.inthebytes.stacklunch.data.role.Role;
import com.inthebytes.stacklunch.data.user.User;
import com.inthebytes.stacklunch.data.user.UserDto;
import com.inthebytes.stacklunch.data.user.UserRepository;

@ContextConfiguration(classes = DataTestConfiguration.class)
@DataJpaTest
@EntityScan(basePackageClasses = {Confirmation.class, User.class, Role.class})
@EnableJpaRepositories(basePackageClasses = {ConfirmationRepository.class, UserRepository.class})
class ConfirmationDataTest {
	
	@Autowired
	ConfirmationRepository confirmationRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	private static final String sampleConfirmationToken = "confirmation-token";
	private static final Timestamp sampleCreatedDate = new Timestamp(System.currentTimeMillis());
	private static final Boolean sampleIsConfirmed = false;
	
	private static final String savedTokenId = "customer-confirmation-token-id";
	private static final String savedUserId = "customer-id-123";
	private static final String savedConfirmationToken = "confirmation-token-456";
	private static final Timestamp savedCreatedDate = Timestamp.valueOf("2021-01-01 01:00:00");
	
	private User getTestUser() {
		return userRepo.findById(savedUserId).get();
	}
	
	private void manuallySetTokenProperties(Confirmation token) {
		token.setUser(getTestUser());
		token.setConfirmationToken(sampleConfirmationToken);
		token.setCreatedDate(sampleCreatedDate);
		token.setIsConfirmed(sampleIsConfirmed);
	}
	
	private Confirmation generateTestEntity() {
		Confirmation token = new Confirmation();
		manuallySetTokenProperties(token);
		return token;
	}
	
	private ConfirmationDto generateTestDto() {
		return ConfirmationDto.convert(generateTestEntity());
	}
	
	private Confirmation getSavedConfirmationToken() {
		return confirmationRepo.findById(savedTokenId).get();
	}
	
	@Test
	void testDtoGettersAndSetters() {
		ConfirmationDto testDto = generateTestDto();
		
		assertEquals(UserDto.convert(getTestUser()), testDto.getUser());
		assertEquals(sampleConfirmationToken, testDto.getConfirmationToken());
		assertEquals(sampleCreatedDate, testDto.getCreatedDate());
		assertFalse(testDto.getIsConfirmed());
	}
	
	@Test
	void testEntityGettersAndSetters() {
		Confirmation testEntity = generateTestEntity();
		
		assertEquals(getTestUser(), testEntity.getUser());
		assertEquals(sampleConfirmationToken, testEntity.getConfirmationToken());
		assertEquals(sampleCreatedDate, testEntity.getCreatedDate());
		assertFalse(testEntity.getIsConfirmed());
	}
	
	@Test
	void testDtoHashCodeEqualsAndToString() {
		ConfirmationDto testDto = generateTestDto();
		ConfirmationDto anotherDto = generateTestDto();
		
		assertEquals(testDto.hashCode(), anotherDto.hashCode());
		assertNotEquals(testDto.hashCode(), (new ConfirmationDto()).hashCode());
		
		assertEquals(testDto, anotherDto);
		assertNotEquals(testDto, new ConfirmationDto());
		
		assertEquals(testDto.toString(), anotherDto.toString());
		assertNotEquals(testDto.toString(), (new ConfirmationDto()).toString());
	}
	
	@Test
	void testEntityHashCodeEqualsAndToString() {
		Confirmation testEntity = generateTestEntity();
		Confirmation anotherEntity = generateTestEntity();
		
		assertEquals(testEntity.hashCode(), anotherEntity.hashCode());
		assertNotEquals(testEntity.hashCode(), (new Confirmation()).hashCode());
		
		assertEquals(testEntity, anotherEntity);
		assertNotEquals(testEntity, new Confirmation());
		
		assertEquals(testEntity.toString(), anotherEntity.toString());
		assertNotEquals(testEntity.toString(), (new Confirmation()).toString());
	}
	
	@Test
	void testConfirmationDtoConvertMethods() {
		ConfirmationDto testDto = generateTestDto();
		Confirmation entity = generateTestEntity();
		entity.getUser().setPassword(null);
		
		assertEquals(testDto, ConfirmationDto.convert(entity));
		assertEquals(entity, testDto.convert());
		
		List<Confirmation> entities = new ArrayList<>();
		entities.add(entity);
		Page<ConfirmationDto> dtoPage = ConfirmationDto.convert(new PageImpl<Confirmation>(entities));
		
		assertEquals(1, dtoPage.getTotalPages());
		assertEquals(1L, dtoPage.getTotalElements());
		assertEquals(testDto, dtoPage.getContent().get(0));
	}
	
	@Test
	void testRepositoryGetConfirmationById() {
		Optional<Confirmation> savedToken = confirmationRepo.findById(savedTokenId);
		assertTrue(savedToken.isPresent());
		assertEquals(savedUserId, savedToken.get().getUser().getUserId());
		assertEquals(savedConfirmationToken, savedToken.get().getConfirmationToken());
		assertEquals(savedCreatedDate, savedToken.get().getCreatedDate());
		assertFalse(savedToken.get().getIsConfirmed());
	}
	
	@Test
	void testRepositoryUpdateOperation() {
		Confirmation token = getSavedConfirmationToken();
		token.setIsConfirmed(true);
		token.setConfirmationToken(sampleConfirmationToken);
		token.setCreatedDate(sampleCreatedDate);
		token = confirmationRepo.save(token);
		
		assertEquals(savedTokenId, token.getTokenId());
		assertEquals(savedUserId, token.getUser().getUserId());
		
		assertTrue(token.getIsConfirmed());
		assertEquals(sampleConfirmationToken, token.getConfirmationToken());
		assertNotEquals(savedConfirmationToken, token.getConfirmationToken());
		assertEquals(sampleCreatedDate, token.getCreatedDate());
		assertNotEquals(savedCreatedDate, token.getCreatedDate());
	}
	
	@Test
	void testRepositoryDeleteOperation() {
		confirmationRepo.delete(getSavedConfirmationToken());
		Optional<Confirmation> token = confirmationRepo.findById(savedTokenId);
		assertFalse(token.isPresent());
	}
	
	@Test
	void testRepositoryCreateOperation() {
		String tokenId = confirmationRepo.save(generateTestEntity()).getTokenId();
		Optional<Confirmation> token = confirmationRepo.findById(tokenId);
		assertTrue(token.isPresent());
		assertEquals(sampleCreatedDate, token.get().getCreatedDate());
	}

}
