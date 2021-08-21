package com.inthebytes.stacklunch.data;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;

import com.inthebytes.stacklunch.DataTestConfiguration;
import com.inthebytes.stacklunch.object.entity.Authorization;
import com.inthebytes.stacklunch.object.entity.Confirmation;
import com.inthebytes.stacklunch.object.entity.PasswordChange;
import com.inthebytes.stacklunch.repository.AuthorizationRepository;
import com.inthebytes.stacklunch.repository.ConfirmationRepository;
import com.inthebytes.stacklunch.repository.PasswordChangeRepository;

@ContextConfiguration(classes = DataTestConfiguration.class)
@DataJpaTest
@EnableJpaRepositories(basePackageClasses = {
		AuthorizationRepository.class, ConfirmationRepository.class, PasswordChangeRepository.class})
@EntityScan(basePackageClasses = {Authorization.class, Confirmation.class, PasswordChange.class})
public class TokenTablesDataTest {

}
