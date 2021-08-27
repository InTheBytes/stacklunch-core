package com.inthebytes.stacklunch.app.datatest;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;

import com.inthebytes.stacklunch.data.authorization.Authorization;
import com.inthebytes.stacklunch.data.authorization.AuthorizationRepository;
import com.inthebytes.stacklunch.data.confirmation.Confirmation;
import com.inthebytes.stacklunch.data.confirmation.ConfirmationRepository;
import com.inthebytes.stacklunch.data.passchange.PasswordChange;
import com.inthebytes.stacklunch.data.passchange.PasswordChangeRepository;

@ContextConfiguration(classes = DataTestConfiguration.class)
@DataJpaTest
@EntityScan(basePackageClasses = {Authorization.class, Confirmation.class, PasswordChange.class})
@EnableJpaRepositories(basePackageClasses = {AuthorizationRepository.class, ConfirmationRepository.class, PasswordChangeRepository.class})
@TestMethodOrder(OrderAnnotation.class)
@Commit
public class TokenTablesDataTest {

}
