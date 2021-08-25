package com.inthebytes.stacklunch;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EntityScan("com.inthebytes.stacklunch.data")
public class DataTestConfiguration {
	

}
