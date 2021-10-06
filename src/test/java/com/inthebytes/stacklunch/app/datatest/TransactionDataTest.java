package com.inthebytes.stacklunch.app.datatest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.sql.Timestamp;
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
import com.inthebytes.stacklunch.data.food.Food;
import com.inthebytes.stacklunch.data.location.Location;
import com.inthebytes.stacklunch.data.order.Order;
import com.inthebytes.stacklunch.data.order.OrderDto;
import com.inthebytes.stacklunch.data.order.OrderRepository;
import com.inthebytes.stacklunch.data.restaurant.Restaurant;
import com.inthebytes.stacklunch.data.role.Role;
import com.inthebytes.stacklunch.data.transaction.Transaction;
import com.inthebytes.stacklunch.data.transaction.TransactionDto;
import com.inthebytes.stacklunch.data.transaction.TransactionRepository;
import com.inthebytes.stacklunch.data.user.User;

@ContextConfiguration(classes = DataTestConfiguration.class)
@DataJpaTest
@EntityScan(basePackageClasses = 
	{Transaction.class, Order.class, User.class, Role.class, Location.class, Restaurant.class, Food.class})
@EnableJpaRepositories(basePackageClasses = {TransactionRepository.class, OrderRepository.class})
class TransactionDataTest {
	
	@Autowired
	TransactionRepository transRepo;
	
	@Autowired
	OrderRepository orderRepo;

	private final static String sampleStripeId = "test-stripe-id";
	private final static BigDecimal sampleSubtotal = new BigDecimal("5.0");
	private final static BigDecimal sampleTax = new BigDecimal("0.3");
	private final static BigDecimal sampleFee = new BigDecimal("1.0");
	private final static BigDecimal sampleDiscount = new BigDecimal("0.75");
	private final static BigDecimal sampleTip = new BigDecimal("2.00");
	private final static BigDecimal sampleTotal = 
			sampleSubtotal.add(sampleTax).add(sampleFee).add(sampleDiscount).add(sampleTip);
	private final static Timestamp samplePaymentTime = new Timestamp(System.currentTimeMillis());
	
	private final static String savedTransactionId = "transaction-id-123";
	private final static String savedOrderId = "order-id-123";
	
	private final static String savedStripeId = "stripe-id-123";
	private final static BigDecimal savedSubtotal = new BigDecimal("6.38");
	private final static BigDecimal savedTax = new BigDecimal("0.22");
	private final static BigDecimal savedFee = new BigDecimal("1.40");
	private final static BigDecimal savedDiscount = new BigDecimal("0.50");
	private final static BigDecimal savedTip = new BigDecimal("1.50");
	private final static BigDecimal savedTotal = 
			savedSubtotal.add(savedTax).add(savedFee).add(savedDiscount).add(savedTip);
	private final static Timestamp savedPaymentTime = Timestamp.valueOf("2021-01-01 01:10:00");
	
	private Order getTestOrder() {
		return orderRepo.findById("order-id-345").get();
	}
	
	private void manuallySetTestProperties(Transaction entity) {
		entity.setStripeId(sampleStripeId);
		entity.setSubtotal(sampleSubtotal);
		entity.setTax(sampleTax);
		entity.setFee(sampleFee);
		entity.setDiscount(sampleDiscount);
		entity.setTip(sampleTip);
		entity.setTotal(sampleTotal);
		entity.setPaymentTime(samplePaymentTime);
		entity.setOrder(getTestOrder());
		entity.getOrder().getCustomer().setPassword(null);
		entity.getOrder().getRestaurant().getManager().stream().forEach(x -> x.setPassword(null));
	}
	
	private Transaction generateTestTransactionEntity() {
		Transaction entity = new Transaction();
		manuallySetTestProperties(entity);
		return entity;
	}
	
	private TransactionDto generateTestTransactionDto() {
		return TransactionDto.convert(generateTestTransactionEntity());
	}
	
	private Optional<Transaction> getOptionalSavedTransaction() {
		return transRepo.findById(savedTransactionId);
	}
	
	private Transaction getSavedTransaction() {
		return getOptionalSavedTransaction().get();
	}
	
	@Test
	void testDtoGettersAndSetters() {
		TransactionDto testDto = generateTestTransactionDto();
		assertEquals(sampleStripeId, testDto.getStripeId());
		assertEquals(sampleSubtotal, testDto.getSubtotal());
		assertEquals(sampleTax, testDto.getTax());
		assertEquals(sampleFee, testDto.getFee());
		assertEquals(sampleDiscount, testDto.getDiscount());
		assertEquals(sampleTip, testDto.getTip());
		assertEquals(sampleTotal, testDto.getTotal());
		assertEquals(samplePaymentTime, testDto.getPaymentTime());
		assertEquals(OrderDto.convert(getTestOrder()), testDto.getOrder());
	}
	
	@Test
	void testEntityGettersAndSetters() {
		Transaction testEntity = generateTestTransactionEntity();
		assertEquals(sampleStripeId, testEntity.getStripeId());
		assertEquals(sampleSubtotal, testEntity.getSubtotal());
		assertEquals(sampleTax, testEntity.getTax());
		assertEquals(sampleFee, testEntity.getFee());
		assertEquals(sampleDiscount, testEntity.getDiscount());
		assertEquals(sampleTip, testEntity.getTip());
		assertEquals(sampleTotal, testEntity.getTotal());
		assertEquals(samplePaymentTime, testEntity.getPaymentTime());
		assertEquals(getTestOrder(), testEntity.getOrder());
	}
	
	@Test
	void testDtoHashCodeEqualsAndToString() {
		TransactionDto testDto = generateTestTransactionDto();
		TransactionDto anotherDto = generateTestTransactionDto();
		
		assertEquals(testDto.hashCode(), anotherDto.hashCode());
		assertNotEquals(testDto.hashCode(), (new TransactionDto()).hashCode());
		
		assertEquals(testDto.toString(), anotherDto.toString());
		assertNotEquals(testDto.toString(), (new TransactionDto()).toString());
		
		assertEquals(testDto, anotherDto);
		assertNotEquals(testDto, new TransactionDto());
	}
	
	@Test
	void testEntityHashCodeEqualsAndToString() {
		Transaction testEntity = generateTestTransactionEntity();
		Transaction anotherEntity = generateTestTransactionEntity();
		
		assertEquals(testEntity.hashCode(), anotherEntity.hashCode());
		assertNotEquals(testEntity.hashCode(), (new Transaction()).hashCode());
		
		assertEquals(testEntity.toString(), anotherEntity.toString());
		assertNotEquals(testEntity.toString(), (new Transaction()).toString());
		
		assertEquals(testEntity, anotherEntity);
		assertNotEquals(testEntity, new Transaction());
		
	}
	
	@Test
	void testDtoConvertMethods() {
		TransactionDto testDto = generateTestTransactionDto();
		Transaction testEntity = generateTestTransactionEntity();
		
		assertEquals(testDto, TransactionDto.convert(testEntity));
		
		List<Transaction> entities = new ArrayList<>();
		entities.add(testEntity);
		Page<TransactionDto> dtoPage = TransactionDto.convert(new PageImpl<>(entities));
		assertEquals(1, dtoPage.getTotalPages());
		assertEquals(1L, dtoPage.getTotalElements());
		assertEquals(testDto, dtoPage.getContent().get(0));
	}
	
	@Test
	void testRepositoryGetByIdOperation() {
		Optional<Transaction> transaction = getOptionalSavedTransaction();
		assertTrue(transaction.isPresent());
		assertEquals(savedStripeId, transaction.get().getStripeId());
		assertEquals(savedSubtotal, transaction.get().getSubtotal());
		assertEquals(savedTax, transaction.get().getTax());
		assertEquals(savedFee, transaction.get().getFee());
		assertEquals(savedDiscount, transaction.get().getDiscount());
		assertEquals(savedTip, transaction.get().getTip());
		assertEquals(savedTotal, transaction.get().getTotal());
		assertEquals(savedPaymentTime, transaction.get().getPaymentTime());
		assertEquals(savedOrderId, transaction.get().getOrder().getOrderId());
	}
	
	@Test
	void testRepositoryUpdateOperation() {
		Transaction newTransaction = generateTestTransactionEntity();
		newTransaction.setTransactionId(savedTransactionId);
		transRepo.save(newTransaction);
		
		Transaction transaction = getSavedTransaction();
		assertEquals(sampleStripeId, transaction.getStripeId());
		assertEquals(sampleSubtotal, transaction.getSubtotal());
		assertEquals(sampleTax, transaction.getTax());
		assertEquals(sampleFee, transaction.getFee());
		assertEquals(sampleDiscount, transaction.getDiscount());
		assertEquals(sampleTip, transaction.getTip());
		assertEquals(sampleTotal, transaction.getTotal());
		assertEquals(samplePaymentTime, transaction.getPaymentTime());
		
		assertNotEquals(savedStripeId, transaction.getStripeId());
		assertNotEquals(savedSubtotal, transaction.getSubtotal());
		assertNotEquals(savedTax, transaction.getTax());
		assertNotEquals(savedFee, transaction.getFee());
		assertNotEquals(savedDiscount, transaction.getDiscount());
		assertNotEquals(savedTip, transaction.getTip());
		assertNotEquals(savedTotal, transaction.getTotal());
		assertNotEquals(savedPaymentTime, transaction.getPaymentTime());
		assertNotEquals(savedOrderId, transaction.getOrder().getOrderId());
	}
	
	@Test
	void testRepositoryDeleteOperation() {
		transRepo.delete(getSavedTransaction());
		assertFalse(getOptionalSavedTransaction().isPresent());
	}
	
	@Test
	void testRepositoryCreateOperation() {
		String transId = transRepo.save(generateTestTransactionEntity()).getTransactionId();
		Optional<Transaction> newTransaction = transRepo.findById(transId);
		assertTrue(newTransaction.isPresent());
		assertEquals(sampleStripeId, newTransaction.get().getStripeId());
		assertEquals(sampleSubtotal, newTransaction.get().getSubtotal());
		assertEquals(sampleTax, newTransaction.get().getTax());
		assertEquals(sampleFee, newTransaction.get().getFee());
		assertEquals(sampleDiscount, newTransaction.get().getDiscount());
		assertEquals(sampleTip, newTransaction.get().getTip());
		assertEquals(sampleTotal, newTransaction.get().getTotal());
		assertEquals(samplePaymentTime, newTransaction.get().getPaymentTime());
		
		Order generatedOrder = getTestOrder();
		generatedOrder.setOrderId(newTransaction.get().getOrder().getOrderId());
		assertEquals(generatedOrder, newTransaction.get().getOrder());
	}
	
	@Test
	void testRepositorySaveableFromDtoConvert() {
		String transId = transRepo.save(generateTestTransactionDto().convert()).getTransactionId();
		Optional<Transaction> newTransaction = transRepo.findById(transId);
		assertTrue(newTransaction.isPresent());
		assertEquals(sampleStripeId, newTransaction.get().getStripeId());
		assertEquals(sampleSubtotal, newTransaction.get().getSubtotal());
		assertEquals(sampleTax, newTransaction.get().getTax());
		assertEquals(sampleFee, newTransaction.get().getFee());
		assertEquals(sampleDiscount, newTransaction.get().getDiscount());
		assertEquals(sampleTip, newTransaction.get().getTip());
		assertEquals(sampleTotal, newTransaction.get().getTotal());
		assertEquals(samplePaymentTime, newTransaction.get().getPaymentTime());
		
		Order generatedOrder = getTestOrder();
		generatedOrder.setOrderId(newTransaction.get().getOrder().getOrderId());
	}
}
