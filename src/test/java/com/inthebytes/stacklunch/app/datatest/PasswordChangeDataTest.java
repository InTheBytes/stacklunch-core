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
import com.inthebytes.stacklunch.data.passchange.PasswordChange;
import com.inthebytes.stacklunch.data.passchange.PasswordChangeDto;
import com.inthebytes.stacklunch.data.passchange.PasswordChangeRepository;
import com.inthebytes.stacklunch.data.role.Role;
import com.inthebytes.stacklunch.data.user.User;
import com.inthebytes.stacklunch.data.user.UserDto;
import com.inthebytes.stacklunch.data.user.UserRepository;

@ContextConfiguration(classes = DataTestConfiguration.class)
@DataJpaTest
@EntityScan(basePackageClasses = {PasswordChange.class, User.class, Role.class})
@EnableJpaRepositories(basePackageClasses = {PasswordChangeRepository.class, UserRepository.class})
class PasswordChangeDataTest {
	
	@Autowired
	PasswordChangeRepository passChangeRepo;
	
	@Autowired
	UserRepository userRepo;
	
	private final static String savedUserIdWithoutToken = "customer-id-123";
	private final static String sampleConfirmationToken = "test-passchange-token";
	private final static Timestamp sampleCreatedTime = new Timestamp(System.currentTimeMillis());
	
	private final static String savedUserIdWithSavedToken = "admin-id-123";
	private final static String savedConfirmationToken = "password-change-token";
	private final static Timestamp savedCreatedTime = Timestamp.valueOf("2021-01-01 01:05:00");
	
	private User getSavedUserById(String id) {
		User user = userRepo.findById(id).get();
		user.setPassword(null);
		return user;
	}
	
	private User getSavedUserWithoutSavedToken() {
		return getSavedUserById(savedUserIdWithoutToken);
	}
	
	private User getSavedUserWithSavedToken() {
		return getSavedUserById(savedUserIdWithSavedToken);
	}
	
	private void manuallySetTestProperties(PasswordChange token) {
		token.setConfirmationToken(sampleConfirmationToken);
		token.setCreatedTime(sampleCreatedTime);
		token.setUser(getSavedUserWithoutSavedToken());
	}
	
	private PasswordChange generatePasswordChangeEntity() {
		PasswordChange token = new PasswordChange();
		manuallySetTestProperties(token);
		return token;
	}
	
	private PasswordChangeDto generatePasswordChangeDto() {
		return PasswordChangeDto.convert(generatePasswordChangeEntity());
	}
	
	private Optional<PasswordChange> getOptionalSavedToken() {
		return passChangeRepo.findById(savedConfirmationToken);
	}
	
	private PasswordChange getSavedToken() {
		return getOptionalSavedToken().get();
	}
	
	@Test
	void testDtoGettersAndSetters() {
		PasswordChangeDto testDto = generatePasswordChangeDto();
		assertEquals(sampleConfirmationToken, testDto.getConfirmationToken());
		assertEquals(sampleCreatedTime, testDto.getCreatedTime());
		assertEquals(UserDto.convert(getSavedUserWithoutSavedToken()), testDto.getUser());
	}
	
	@Test
	void testEntityGettersAndSetters() {
		PasswordChange testEntity = generatePasswordChangeEntity();
		assertEquals(sampleConfirmationToken, testEntity.getConfirmationToken());
		assertEquals(sampleCreatedTime, testEntity.getCreatedTime());
		assertEquals(getSavedUserWithoutSavedToken(), testEntity.getUser());
	}
	
	@Test
	void testDtoHashCodeEqualsAndToString() {
		PasswordChangeDto testDto = generatePasswordChangeDto();
		PasswordChangeDto anotherDto = generatePasswordChangeDto();
		
		assertEquals(testDto.hashCode(), anotherDto.hashCode());
		assertEquals(testDto.toString(), anotherDto.toString());
		assertTrue(testDto.equals(anotherDto));
		
		assertNotEquals(testDto.hashCode(), (new PasswordChangeDto()).hashCode());
		assertNotEquals(testDto.toString(), (new PasswordChangeDto()).toString());
		assertFalse(testDto.equals(new PasswordChangeDto()));
	}
	
	@Test
	void testEntityHashCodeEqualsAndToString() {
		PasswordChange testEntity = generatePasswordChangeEntity();
		PasswordChange anotherEntity = generatePasswordChangeEntity();
		
		assertEquals(testEntity.hashCode(), anotherEntity.hashCode());
		assertEquals(testEntity.toString(), anotherEntity.toString());
		assertTrue(testEntity.equals(anotherEntity));
		
		assertNotEquals(testEntity.hashCode(), (new PasswordChange()).hashCode());
		assertNotEquals(testEntity.toString(), (new PasswordChange()).toString());
		assertFalse(testEntity.equals(new PasswordChange()));
	}
	
	@Test
	void testDtoConvertMethods() {
		PasswordChangeDto testDto = generatePasswordChangeDto();
		PasswordChange testEntity = generatePasswordChangeEntity();
		
		assertEquals(testEntity, testDto.convert());
		assertEquals(testDto, PasswordChangeDto.convert(testEntity));
		
		List<PasswordChange> entities = new ArrayList<>();
		entities.add(testEntity);
		Page<PasswordChangeDto> dtoPage = PasswordChangeDto.convert(new PageImpl<>(entities));
		assertEquals(1, dtoPage.getTotalPages());
		assertEquals(1L, dtoPage.getTotalElements());
		assertEquals(testDto, dtoPage.getContent().get(0));
	}
	
	@Test
	void testRepositoryGetByIdOperation() {
		Optional<PasswordChange> token = getOptionalSavedToken();
		assertTrue(token.isPresent());
		assertEquals(savedConfirmationToken, token.get().getConfirmationToken());
		assertEquals(savedCreatedTime, token.get().getCreatedTime());
		assertEquals(getSavedUserWithSavedToken(), token.get().getUser());
	}
	
	@Test
	void testRepositoryUpdateOperation() {
		PasswordChange token = getSavedToken();
		token.setCreatedTime(sampleCreatedTime);
		token.setUser(getSavedUserWithoutSavedToken());
		passChangeRepo.save(token);
		token = getSavedToken();
		assertEquals(sampleCreatedTime, token.getCreatedTime());
		assertEquals(getSavedUserWithoutSavedToken(), token.getUser());
		assertNotEquals(savedCreatedTime, token.getCreatedTime());
		assertNotEquals(getSavedUserWithSavedToken(), token.getUser());
	}
	
	@Test
	void testRepositoryDeleteOperation() {
		passChangeRepo.delete(getSavedToken());
		Optional<PasswordChange> token = getOptionalSavedToken();
		assertFalse(token.isPresent());
	}
	
	@Test
	void testRepositoryCreateOperation() {
		passChangeRepo.save(generatePasswordChangeEntity());
		Optional<PasswordChange> token = passChangeRepo.findById(sampleConfirmationToken);
		assertTrue(token.isPresent());
		assertEquals(sampleCreatedTime, token.get().getCreatedTime());
		assertEquals(getSavedUserWithoutSavedToken(), token.get().getUser());
	}
}
