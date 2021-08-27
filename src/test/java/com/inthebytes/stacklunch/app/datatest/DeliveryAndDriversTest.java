package com.inthebytes.stacklunch.app.datatest;

import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;

import com.inthebytes.stacklunch.data.delivery.Delivery;
import com.inthebytes.stacklunch.data.delivery.DeliveryRepository;
import com.inthebytes.stacklunch.data.driver.Driver;
import com.inthebytes.stacklunch.data.driver.DriverRepository;
import com.inthebytes.stacklunch.data.order.Order;
import com.inthebytes.stacklunch.data.order.OrderRepository;
import com.inthebytes.stacklunch.data.user.User;
import com.inthebytes.stacklunch.data.user.UserRepository;

@ContextConfiguration(classes = DataTestConfiguration.class)
@DataJpaTest
@EntityScan(basePackageClasses = {Delivery.class, Driver.class, Order.class, User.class})
@EnableJpaRepositories(basePackageClasses = {OrderRepository.class, DriverRepository.class, DeliveryRepository.class, UserRepository.class})
@TestMethodOrder(OrderAnnotation.class)
@Commit
public class DeliveryAndDriversTest {

}
