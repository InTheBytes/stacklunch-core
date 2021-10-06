INSERT INTO authorization (token, expiration_date)
VALUES ('logged-out-token', '2021-01-01 01:00:00');


INSERT INTO role (role_id, name)
VALUES (1, 'admin'), (2, 'customer'), (3, 'restaurant'), (4, 'driver');


INSERT INTO user (user_id, user_role, username, password, email, phone, first_name, last_name, active)
VALUES 
('admin-id-123', 1, 'test-admin', 'admin-pass', 'admin@email.test', '(111) 111-1111', 'Admin', 'Test', 1),
('customer-id-123', 2, 'test-customer', 'customer-pass', 'customer@email.test', '(222) 222-2222', 'Customer', 'Test', 0),
('manager-id-123', 3, 'test-manager', 'manager-pass', 'manager@email.test', '(333) 333-3333', 'Manager', 'Test', 1),
('driver-id-123', 4, 'test-driver', 'driver-pass', 'driver@email.test', '(444) 444-4444', 'Driver', 'Test', 0); 


INSERT INTO confirmation (token_id, user_id, confirmation_token, created_date, is_confirmed)
VALUES 
('manager-confirmation-token-id', 'manager-id-123', 'confirmation-token-123', NOW(), 1),
('customer-confirmation-token-id', 'customer-id-123', 'confirmation-token-456', '2021-01-01 01:00:00', 0);


INSERT INTO driver (driver_id, user_id, vehicle_id, financial_id, status)
VALUES ('driver-table-id-123', 'driver-id-123', 'vehicle-id-123', 'financial-id-123', 1);


INSERT INTO location (location_id, street, unit, city, state, zip_code)
VALUES
('restaurant-location-id', 'Somewhere St.', '123', 'Somewhere', 'VA', 11111),
('destination-location-id', 'Nowhere Rd.', '456', 'Somewhere', 'VA', 22222);


INSERT INTO restaurant (restaurant_id, location_id, name, cuisine)
VALUES ('restaurant-id-123', 'restaurant-location-id', 'Test Restaurant', 'Test');


INSERT INTO `order` (order_id, user_id, restaurant_id, destination_id, status, window_start, window_end, special_instructions)
VALUES 
('order-id-123', 'customer-id-123', 'restaurant-id-123', 'destination-location-id', 1, '2021-01-01 01:00:00', '2021-01-01 02:00:00', 'Test the order table'),
('order-id-345', 'customer-id-123', 'restaurant-id-123', 'destination-location-id', 0, '2021-01-01 01:00:00', '2021-01-01 02:00:00', 'Test for adding to transaction table');


INSERT INTO delivery (delivery_id, order_id, driver_id, start_time, pickup_time, deliver_time)
VALUES ('delivery-id-123', 'order-id-123', 'driver-table-id-123', '2021-01-01 01:05:00', '2021-01-01 01:30:00', '2021-01-01 01:45:00');

    
INSERT INTO food (food_id, restaurant_id, name, price, description)
VALUES ('food-id-123', 'restaurant-id-123', 'Test Burger', 3.50, 'A burger for testing'),
('food-id-456', 'restaurant-id-123', 'Test Fries', 1.99, 'Some fries for testing'),
('food-id-789', 'restaurant-id-123', 'Test Shake', 5.00, 'Tasty shake for testy tests');

    
INSERT INTO manager (user_id, restaurant_id)
VALUES ('manager-id-123', 'restaurant-id-123');


INSERT INTO order_food (order_id, food_id, quantity)
VALUES ('order-id-123', 'food-id-123', 1), ('order-id-123', 'food-id-456', 2);


INSERT INTO password_change (confirmation_token, user_id, created_time)
VALUES ('password-change-token', 'admin-id-123', '2021-01-01 01:05:00');

    
INSERT INTO transaction (transaction_id, stripe_id, order_id, subtotal, tax, fee, discount, tip, total, payment_time)
VALUES ('transaction-id-123', 'stripe-id-123', 'order-id-123', 6.38, 0.22, 1.40, 0.50, 1.50, 10.00, '2021-01-01 01:10:00');