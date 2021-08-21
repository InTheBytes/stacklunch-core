package com.inthebytes.stacklunch.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;

import com.inthebytes.stacklunch.DataTestConfiguration;
import com.inthebytes.stacklunch.object.dto.RoleDto;
import com.inthebytes.stacklunch.object.dto.UserDto;
import com.inthebytes.stacklunch.object.dto.UserRegistrationDto;
import com.inthebytes.stacklunch.object.entity.Role;
import com.inthebytes.stacklunch.object.entity.User;
import com.inthebytes.stacklunch.repository.RoleRepository;
import com.inthebytes.stacklunch.repository.UserRepository;

@ContextConfiguration(classes = DataTestConfiguration.class)
@DataJpaTest
@EnableJpaRepositories(basePackageClasses = {UserRepository.class, RoleRepository.class})
@EntityScan(basePackageClasses = {User.class, Role.class})
@TestMethodOrder(OrderAnnotation.class)
@Commit
public class UserDataTest {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private RoleRepository roleRepo;
	
	private static RoleDto role = new RoleDto();
	private static String userId = "user-id";
	private String username = "testUser";
	private String email = "test@email.com";
	private String phone = "(000) 111-2222";
	private String firstName = "Test";
	private String lastName = "User";
	private Boolean active = false;
	private String password = "password";
	
	
	private UserRegistrationDto makeTestUser() {
		UserRegistrationDto user = new UserRegistrationDto();
		user.setUserId(userId);
		user.setRole(role);
		user.setUsername(username);
		user.setEmail(email);
		user.setPhone(phone);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setActive(active);
		user.setPassword(password);
		return user;
	}
	
	@Test
	@Order(1)
	public void testMappersWithNull() {
		assertNull(RoleDto.convert(null));
		assertNull(UserDto.convert(null));
		assertNull(UserRegistrationDto.convert(null));
	}
	
	@Test
	@Order(2)
	public void testCreateRoleAndMappings() {
		role.setName("Test");
		role.setRoleId("role-id");
		Role entity = roleRepo.save(role.convert());
		role = RoleDto.convert(entity);
		assertEquals("Test", role.getName());
		Role testEntity = role.convert();
		assertTrue(entity.equals(testEntity));
		assertEquals(entity.toString(), testEntity.toString());
		RoleDto testRole = RoleDto.convert(testEntity);
		assertTrue(role.equals(testRole));
		assertEquals(role.toString(), testRole.toString());
		assertEquals(role.hashCode(), testRole.hashCode());
	}
	
	@Test
	@Order(3)
	public void testCreateUserAndMappings() {
		UserRegistrationDto user = makeTestUser();
		assertTrue(user.equals(makeTestUser()));
		assertEquals(user.toString(), makeTestUser().toString());
		assertEquals(user.hashCode(), makeTestUser().hashCode());
		User entity = userRepo.save(user.convert());
		UserDto result = UserDto.convert(entity);
		userId = result.getUserId();
		assertEquals(role, result.getRole());
		assertEquals(username, result.getUsername());
		assertEquals(email, result.getEmail());
		assertEquals(phone, result.getPhone());
		assertEquals(firstName, result.getFirstName());
		assertEquals(lastName, result.getLastName());
		assertFalse(result.getActive());
		assertNotNull(entity.getPassword());
		user.setUserId(entity.getUserId());
		assertTrue(entity.equals(user.convert()));
		assertEquals(entity.toString(), user.convert().toString());
		assertEquals(entity.hashCode(), user.convert().hashCode());
		UserDto testUser = UserDto.convert(user.convert());
		assertTrue(result.equals(testUser));
		assertEquals(result.toString(), testUser.toString());
		assertEquals(result.hashCode(), testUser.hashCode());
	}
	
	@Test
	@Order(4)
	public void testGetUser() {
		Optional<User> entity = userRepo.findById(userId);
		Optional<User> empty = userRepo.findById("Bad-ID");
		assertTrue(entity.isPresent());
		assertFalse(empty.isPresent());
	}
	
	@Test
	@Order(5)
	public void testUpdateUser() {
		User entity = userRepo.findById(userId).get();
		entity.setActive(true);
		User saved = userRepo.save(entity);
		assertTrue(saved.getActive());
	}
	
	@Test
	@Order(6)
	public void testDeleteUser() {
		User entity = userRepo.findById(userId).get();
		userRepo.delete(entity);
		Optional<User> empty = userRepo.findById(userId);
		assertFalse(empty.isPresent());
	}
	
	@Test
	@Order(7)
	public void testGetRole() {
		Optional<Role> entity = roleRepo.findById(role.getRoleId());
		Optional<Role> empty = roleRepo.findById("Bad-ID");
		assertTrue(entity.isPresent());
		assertFalse(empty.isPresent());
		RoleDto testRole = RoleDto.convert(entity.get());
		assertTrue(testRole.equals(role));
		assertEquals(role.toString(), testRole.toString());
	}
	
	@Test
	@Order(8)
	public void testUpdateRole() {
		Role entity = roleRepo.findById(role.getRoleId()).get();
		entity.setName("Updated-Role");
		Role saved = roleRepo.save(entity);
		assertEquals("Updated-Role", saved.getName());
	}
	
	@Test
	@Order(9)
	public void testDeleteRole() {
		Role entity = roleRepo.findById(role.getRoleId()).get();
		roleRepo.delete(entity);
		Optional<Role> saved = roleRepo.findById(role.getRoleId());
		assertFalse(saved.isPresent());
	}

}
