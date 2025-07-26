-- Orders today and recent days
INSERT INTO orders (customer_id, order_date, total_amount) VALUES (1, CURRENT_DATE, 150.00);
INSERT INTO orders (customer_id, order_date, total_amount) VALUES (2, CURRENT_DATE, 320.00);
INSERT INTO orders (customer_id, order_date, total_amount) VALUES (3, CURRENT_DATE - INTERVAL '1 day', 250.00);
INSERT INTO orders (customer_id, order_date, total_amount) VALUES (1, CURRENT_DATE - INTERVAL '2 day', 100.00);
INSERT INTO orders (customer_id, order_date, total_amount) VALUES (2, CURRENT_DATE - INTERVAL '5 day', 400.00);
INSERT INTO orders (customer_id, order_date, total_amount) VALUES (3, CURRENT_DATE - INTERVAL '10 day', 180.00);

-- Orders in last calendar month (dynamically set)
INSERT INTO orders (customer_id, order_date, total_amount)
VALUES (1, (DATE_TRUNC('month', CURRENT_DATE) - INTERVAL '1 month') + INTERVAL '2 day', 180.00);

INSERT INTO orders (customer_id, order_date, total_amount)
VALUES (2, (DATE_TRUNC('month', CURRENT_DATE) - INTERVAL '1 month') + INTERVAL '5 day', 240.00);

INSERT INTO orders (customer_id, order_date, total_amount)
VALUES (3, (DATE_TRUNC('month', CURRENT_DATE) - INTERVAL '1 month') + INTERVAL '7 day', 300.00);
