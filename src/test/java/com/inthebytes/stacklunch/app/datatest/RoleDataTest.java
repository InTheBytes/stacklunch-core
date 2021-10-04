package com.inthebytes.stacklunch.app.datatest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;

import com.inthebytes.stacklunch.data.role.Role;
import com.inthebytes.stacklunch.data.role.RoleDto;
import com.inthebytes.stacklunch.data.role.RoleRepository;

@ContextConfiguration(classes = DataTestConfiguration.class)
@DataJpaTest
@EntityScan(basePackageClasses = Role.class)
@EnableJpaRepositories(basePackageClasses = RoleRepository.class)
class RoleDataTest {
	
	@Autowired
	RoleRepository roleRepo;
	
	@Test
	void testGetRoleAndDtoMethods() {
		Optional<Role> role = roleRepo.findById(1);
		assertTrue(role.isPresent());
		RoleDto dto = new RoleDto();
		dto.setName(role.get().getName());
		dto.setRoleId(role.get().getRoleId());
		assertEquals(dto, RoleDto.convert(role.get()));
		assertEquals(dto.hashCode(), RoleDto.convert(role.get()).hashCode());
		assertEquals(dto.toString(), RoleDto.convert(role.get()).toString());
		assertEquals(role.get(), dto.convert());
		assertEquals(role.get().hashCode(), dto.convert().hashCode());
		assertEquals(role.get().toString(), dto.convert().toString());
	}
	
	@Test
	void testRolePages() {
		Page<Role> roles = roleRepo.findAll(PageRequest.of(0, 4));
		assertFalse(roles.getContent().contains(null));
		Page<RoleDto> dtos = RoleDto.convert(roles);
		assertFalse(dtos.getContent().contains(null));
		assertEquals(dtos.getContent().get(0), RoleDto.convert(roles.getContent().get(0)));
		assertEquals(dtos.getContent().get(1), RoleDto.convert(roles.getContent().get(1)));
		assertEquals(dtos.getContent().get(2), RoleDto.convert(roles.getContent().get(2)));
		assertEquals(dtos.getContent().get(3), RoleDto.convert(roles.getContent().get(3)));
	}
	
	@Test
	void testCreateRole() {
		RoleDto newRole = new RoleDto();
		newRole.setName("test-role");
		Role role = roleRepo.save(newRole.convert());
		assertNotNull(role.getRoleId());
		assertEquals("test-role", role.getName());
		assertEquals(role, roleRepo.findById(role.getRoleId()).get());
	}

	@Test
	void testDeleteBehavior() {
		roleRepo.deleteById(1);
		assertThrows(DataIntegrityViolationException.class,() -> {
			roleRepo.flush();
		});
	}
	
	@Test
	void testUpdateBehavior() {
		Optional<Role> oldRole = roleRepo.findById(1);
		Role newRole = oldRole.get();
		newRole.setName("new-admin-role");
		assertEquals("new-admin-role", roleRepo.findById(1).get().getName());
	}
}
