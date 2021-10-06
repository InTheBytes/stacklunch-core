package com.inthebytes.stacklunch.app.datatest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
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
import com.inthebytes.stacklunch.data.role.Role;
import com.inthebytes.stacklunch.data.role.RoleDto;
import com.inthebytes.stacklunch.data.user.User;
import com.inthebytes.stacklunch.data.user.UserDto;
import com.inthebytes.stacklunch.data.user.UserRegistrationDto;
import com.inthebytes.stacklunch.data.user.UserRepository;

@ContextConfiguration(classes = DataTestConfiguration.class)
@DataJpaTest
@EntityScan(basePackageClasses = {User.class, Role.class})
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
class UserDataTest {
	
	@Autowired
	UserRepository userRepo;
	
	private static final String sampleUsername = "test-user";
	private static final String samplePassword = "test-pass";
	private static final String sampleEmail = "test@email.test";
	private static final String samplePhone = "(555) 555-5555";
	private static final String sampleFirstName = "Test";
	private static final String sampleLastName = "Account";
	private static final Boolean sampleActive = true;

	private static final String savedUserId = "customer-id-123";
	
	private static final String savedUsername = "test-customer";
	private static final String savedPassword = "customer-pass";
	private static final String savedEmail = "customer@email.test";
	private static final String savedPhone = "(222) 222-2222";
	private static final String savedFirstName = "Customer";
	private static final String savedLastName = "Test";
	
	private void manuallySetTestProperties(UserRegistrationDto user) {
		user.setUsername(sampleUsername);
		user.setPassword(samplePassword);
		user.setEmail(sampleEmail);
		user.setPhone(samplePhone);
		user.setFirstName(sampleFirstName);
		user.setLastName(sampleLastName);
		user.setActive(sampleActive);
		user.setRole(generateTestRole());
	}
	
	private RoleDto generateTestRole() {
		RoleDto role = new RoleDto();
		role.setName("test-role");
		return role;
	}
	
	private UserRegistrationDto generateTestUserDtoWithPassword() {
		UserRegistrationDto dto = new UserRegistrationDto();
		manuallySetTestProperties(dto);
		return dto;
	}
	
	private User generateTestUserEntity() {
		return generateTestUserDtoWithPassword().convert();
	}
	
	private UserDto generateTestUserDtoWithoutPassword() {
		return UserDto.convert(generateTestUserEntity());
	}
	
	private Optional<User> getOptionalSavedUser() {
		return userRepo.findById(savedUserId);
	}
	
	private User getSavedUser() {
		return getOptionalSavedUser().get();
	}
	
	@Test
	void testDtoGettersAndSetters() {
		UserDto testDto = generateTestUserDtoWithoutPassword();
		assertEquals(sampleUsername, testDto.getUsername());
		assertEquals(sampleEmail, testDto.getEmail());
		assertEquals(samplePhone, testDto.getPhone());
		assertEquals(sampleFirstName, testDto.getFirstName());
		assertEquals(sampleLastName, testDto.getLastName());
		assertTrue(testDto.getActive());
	}
	
	@Test
	void testRegistrationDtoPasswordGettersAndSetters() {
		UserRegistrationDto testDto = generateTestUserDtoWithPassword();
		assertEquals(samplePassword, testDto.getPassword());
	}
	
	@Test
	void testEntityGettersAndSetters() {
		User testEntity = generateTestUserEntity();
		assertEquals(sampleUsername, testEntity.getUsername());
		assertEquals(samplePassword, testEntity.getPassword());
		assertEquals(sampleEmail, testEntity.getEmail());
		assertEquals(samplePhone, testEntity.getPhone());
		assertEquals(sampleFirstName, testEntity.getFirstName());
		assertEquals(sampleLastName, testEntity.getLastName());
		assertTrue(testEntity.getActive());
	}
	
	@Test
	void testBothDtoHashCode() {
		UserDto testDto = generateTestUserDtoWithoutPassword();
		UserRegistrationDto testRegDto = generateTestUserDtoWithPassword();
		
		assertEquals(generateTestUserDtoWithoutPassword().hashCode(), testDto.hashCode());
		assertNotEquals(testDto.hashCode(), (new UserDto()).hashCode());
		
		assertEquals(generateTestUserDtoWithPassword().hashCode(), testRegDto.hashCode());
		assertNotEquals(testRegDto.hashCode(), (new UserRegistrationDto()).hashCode());
		
		assertNotEquals(testDto.hashCode(), testRegDto.hashCode());
	}
	
	@Test
	void testBothDtoToString() {
		UserDto testDto = generateTestUserDtoWithoutPassword();
		UserRegistrationDto testRegDto = generateTestUserDtoWithPassword();
		
		assertEquals(generateTestUserDtoWithoutPassword().toString(), testDto.toString());
		assertNotEquals(testDto.toString(), (new UserDto()).toString());
		
		assertEquals(generateTestUserDtoWithPassword().toString(), testRegDto.toString());
		assertNotEquals(testRegDto.toString(), (new UserRegistrationDto()).toString());
		
		assertNotEquals(testDto.toString(), testRegDto.toString());
	}
	
	@Test
	void testBothDtoEquals() {
		UserDto testDto = generateTestUserDtoWithoutPassword();
		UserRegistrationDto testRegDto = generateTestUserDtoWithPassword();
		
		assertEquals(testDto, generateTestUserDtoWithoutPassword());
		assertNotEquals(testDto, new UserDto());
		
		assertEquals(testRegDto, generateTestUserDtoWithPassword());
		assertNotEquals(testRegDto, new UserRegistrationDto());
		
		assertNotEquals(testRegDto, testDto);
		assertNotEquals(testDto, testRegDto);
	}
	
	@Test
	void testEntityHashCodeEqualsAndToString() {
		User testEntity = generateTestUserEntity();
		User anotherEntity = generateTestUserEntity();
		
		assertEquals(testEntity.hashCode(), anotherEntity.hashCode());
		assertNotEquals(testEntity.hashCode(), (new User()).hashCode());
		
		assertEquals(testEntity.toString(), anotherEntity.toString());
		assertNotEquals(testEntity.toString(), (new User()).toString());
		
		assertEquals(testEntity, anotherEntity);
		assertNotEquals(testEntity, new User());
	}
	
	@Test
	void testDtoConvertMethods() {
		UserDto testDto = generateTestUserDtoWithoutPassword();
		UserRegistrationDto testRegDto = generateTestUserDtoWithPassword();
		User testEntity = generateTestUserEntity();
		
		assertEquals(testEntity, testRegDto.convert());
		assertNotEquals(testEntity, testDto.convert());
		assertNull(testDto.convert().getPassword());
		
		assertEquals(testDto, UserDto.convert(testEntity));
		assertNotEquals(testRegDto, UserRegistrationDto.convert(testEntity));
		assertEquals(testDto, UserRegistrationDto.convert(testEntity));
		
		List<User> entities = new ArrayList<>();
		entities.add(testEntity);
		Page<UserDto> dtoPage = UserDto.convert(new PageImpl<>(entities));
		assertEquals(1, dtoPage.getTotalElements());
		assertEquals(1L, dtoPage.getTotalElements());
		assertEquals(testDto, dtoPage.getContent().get(0));
		
		dtoPage = UserRegistrationDto.convert(new PageImpl<>(entities));
		assertEquals(1, dtoPage.getTotalElements());
		assertEquals(1L, dtoPage.getTotalElements());
		assertNotEquals(testRegDto, dtoPage.getContent().get(0));
		assertEquals(testDto, dtoPage.getContent().get(0));
	}
	
	@Test
	void testRepositoryGetByIdOperation() {
		Optional<User> user = getOptionalSavedUser();
		assertTrue(user.isPresent());
		assertEquals(savedUsername, user.get().getUsername());
		assertEquals(savedPassword, user.get().getPassword());
		assertEquals(savedEmail, user.get().getEmail());
		assertEquals(savedPhone, user.get().getPhone());
		assertEquals(savedFirstName, user.get().getFirstName());
		assertEquals(savedLastName, user.get().getLastName());
		assertFalse(user.get().getActive());
	}
	
	@Test
	void testRepositoryUpdateOperation() {
		User user = generateTestUserEntity();
		user.setUserId(savedUserId);
		userRepo.save(user);
		user = getSavedUser();

		assertEquals(sampleUsername, user.getUsername());
		assertEquals(samplePassword, user.getPassword());
		assertEquals(sampleEmail, user.getEmail());
		assertEquals(samplePhone, user.getPhone());
		assertEquals(sampleFirstName, user.getFirstName());
		assertEquals(sampleLastName, user.getLastName());
		assertTrue(user.getActive());

		assertNotEquals(savedUsername, user.getUsername());
		assertNotEquals(savedPassword, user.getPassword());
		assertNotEquals(savedEmail, user.getEmail());
		assertNotEquals(savedPhone, user.getPhone());
		assertNotEquals(savedFirstName, user.getFirstName());
		assertNotEquals(savedLastName, user.getLastName());
	}
	
	@Test
	void testRepositoryDeleteOperation() {
		userRepo.delete(getSavedUser());
		Optional<User> user = getOptionalSavedUser();
		assertFalse(user.isPresent());
	}
	
	@Test
	void testRepositoryCreateOperation() {
		String newUserId = userRepo.save(generateTestUserEntity()).getUserId();
		Optional<User> newUser = userRepo.findById(newUserId);
		assertTrue(newUser.isPresent());
		assertEquals(sampleUsername, newUser.get().getUsername());
		assertEquals(samplePassword, newUser.get().getPassword());
		assertEquals(sampleEmail, newUser.get().getEmail());
		assertEquals(samplePhone, newUser.get().getPhone());
		assertEquals(sampleFirstName, newUser.get().getFirstName());
		assertEquals(sampleLastName, newUser.get().getLastName());
		assertTrue(newUser.get().getActive());
	}

}
