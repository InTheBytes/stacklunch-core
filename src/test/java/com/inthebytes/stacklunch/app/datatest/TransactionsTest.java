package com.inthebytes.stacklunch.app.datatest;

import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;

import com.inthebytes.stacklunch.data.order.Order;
import com.inthebytes.stacklunch.data.order.OrderRepository;
import com.inthebytes.stacklunch.data.transaction.Transaction;
import com.inthebytes.stacklunch.data.transaction.TransactionRepository;

@ContextConfiguration(classes = DataTestConfiguration.class)
@DataJpaTest
@EntityScan(basePackageClasses = {Transaction.class, Order.class})
@EnableJpaRepositories(basePackageClasses = {TransactionRepository.class, OrderRepository.class})
@TestMethodOrder(OrderAnnotation.class)
@Commit
public class TransactionsTest {

}
