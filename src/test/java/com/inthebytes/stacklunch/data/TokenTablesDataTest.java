package com.inthebytes.stacklunch.data;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import com.inthebytes.stacklunch.DataTestConfiguration;

@ContextConfiguration(classes = DataTestConfiguration.class)
@DataJpaTest
public class TokenTablesDataTest {

}
