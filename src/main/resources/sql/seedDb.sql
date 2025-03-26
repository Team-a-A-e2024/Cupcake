-- CREATE USERS
INSERT INTO users (email, password, role, credit)
VALUES
	('admin@olsker.dk', 'Cupc4k3!', 'admin', 999999999),
	('cph-ab632@cphbusiness.dk', 'Test1', null, 0),
	('cph-ea178@cphbusiness.dk', 'Test2', null, 0),
	('cph-fb157@cphbusiness.dk', 'Test3', null, 0),
	('cph-ta241@cphbusiness.dk', 'Test4', null, 0);

-- CREATE TOPPINGS
INSERT INTO toppings (topping, price)
VALUES
	('Chocolate', 5.00),
	('Blueberry', 5.00),
	('Raspberry', 5.00),
	('Crispy', 6.00),
	('Strawberry', 6.00),
	('Rum/Raisin', 7.00),
	('Orange', 8.00),
	('Lemon', 8.00),
	('Blue cheese', 9.00);

-- CREATE BOTTOMS
INSERT INTO bottoms (bottom, price)
VALUES 
	('Chocolate', 5.00),
	('Vanilla', 5.00),
	('Nutmeg', 5.00),
	('Pistacio', 6.00),
	('Almond', 7.00);

-- CREATE ORDERS
INSERT INTO orders (user_id, topping, bottom, amount, is_processed)
VALUES
	(1, 'Chocolate', 'Chocolate', 1, false),
	(1, 'Blueberry', 'Vanilla', 1, true),
	(2, 'Raspberry', 'Nutmeg', 1, false),
	(2, 'Crispy', 'Pistacio', 1, true),
	(3, 'Strawberry', 'Almond', 1, false),
	(3, 'Rum/Raisin', 'Chocolate', 1, true),
	(4, 'Orange', 'Vanilla', 1, false),
	(4, 'Lemon', 'Nutmeg', 1, true),
	(5, 'Blue cheese', 'Pistacio', 1, false),
	(5, 'Chocolate', 'Almond', 1, true);
