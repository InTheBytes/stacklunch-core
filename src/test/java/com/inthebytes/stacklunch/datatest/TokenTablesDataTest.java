package com.inthebytes.stacklunch.datatest;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import com.inthebytes.stacklunch.stub.StubConfiguration;

@ContextConfiguration(classes = StubConfiguration.class)
@DataJpaTest
public class TokenTablesDataTest {

}
