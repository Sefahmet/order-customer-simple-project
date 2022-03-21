-- Table Schemas
DROP TABLE IF EXISTS orders_products;


DROP TABLE IF EXISTS orders ;
DROP TABLE IF EXISTS address;
DROP TABLE IF EXISTS customers;

DROP TABLE IF EXISTS products;

DROP TABLE IF EXISTS deneme;

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";


CREATE TABLE customers
(
    id           uuid UNIQUE DEFAULT uuid_generate_v4() primary key ,
    first_name   VARCHAR(25) NOT NULL,
    last_name    VARCHAR(25) NOT NULL,
    email        VARCHAR(25) UNIQUE NOT NULL,
    phone_number VARCHAR(10) NOT NULL,
    created_date DATE ,
    updated_date DATE
);

CREATE TABLE address
(
    id           uuid UNIQUE DEFAULT uuid_generate_v4() primary key ,
    address_line VARCHAR NOT NULL ,
    city         VARCHAR NOT NULL ,
    country      VARCHAR NOT NULL ,
    city_code    int,
    customer_id  uuid,
    FOREIGN KEY (customer_id) References customers(id)
);

CREATE TABLE orders
(
    id           uuid UNIQUE DEFAULT uuid_generate_v4() primary key ,
    quantity     INT NOT NULL,
    price        FLOAT NOT NULL,
    status       VARCHAR NOT NULL ,
    created_date DATE ,
    updated_date DATE,
    customer_id  uuid,
    address_id   uuid,
    FOREIGN KEY (customer_id) References customers(id),
    FOREIGN KEY (address_id) REFERENCES address(id)
);
CREATE TABLE products
(
    id         uuid UNIQUE DEFAULT uuid_generate_v4() PRIMARY KEY ,
    image_url VARCHAR,
    name VARCHAR NOT NULL,
    stock INT NOT NULL
);
CREATE TABLE orders_products
(
    orders_id   uuid REFERENCES orders(id),
    products_id uuid REFERENCES products(id),
    primary key (orders_id,products_id)
);
INSERT INTO customers(id,first_name, last_name, email, phone_number,created_date,updated_date)
VALUES ('b03ac8f7-c531-4a35-b4e6-59dd822d8882','Sefa', 'Altundal','sefaaltunda@gmail.com','5314992211',DATE'2022-03-18 15:13:04',DATE'2022-03-18 15:13:04'),
       ('6892ce53-5450-4bf3-b8ee-9190245c5ea0','Kürşat', 'Berkay','krst@gmail.com','5314992212',DATE'2022-03-18 15:13:04',DATE'2022-03-18 15:13:04'),
       ('a92d23a7-7e94-456e-9c10-190c7cdfcd8c','Faik', 'Sevim','fako@gmail.com','5314992213',DATE'2022-03-18 15:13:04',DATE'2022-03-18 15:13:04'),
       ('1d4d5c03-f343-43fc-ba0f-a2a206dd7edf','Gürhan','Çifci','grhngmail.com','5314992214',DATE'2022-03-18 15:13:04',DATE'2022-03-18 15:13:04');

INSERT INTO address(id,address_line, city, country, city_code,customer_id)
VALUES ('d09c45f2-8d8b-437a-8697-306a6ed668d7','Başak mah. ali soylu cad. k-4 d-11','İstanbul','Türkiye',34480,'b03ac8f7-c531-4a35-b4e6-59dd822d8882');

INSERT INTO orders(id,quantity,price,status,created_date,updated_date,customer_id,address_id)
VALUES ('95c394c0-aee3-4014-9501-d2df0bf4fbf3',20,1000,'delivered',DATE'2022-03-18 15:13:04',DATE '2022-03-18 15:13:04','b03ac8f7-c531-4a35-b4e6-59dd822d8882','d09c45f2-8d8b-437a-8697-306a6ed668d7'),
        ('51577d56-4f57-492b-90c5-976fe09a466a',20,1000,'preparing',DATE'2022-03-18 15:13:04',DATE'2022-03-18 15:13:04','b03ac8f7-c531-4a35-b4e6-59dd822d8882','d09c45f2-8d8b-437a-8697-306a6ed668d7'),
        ('0a4d525a-2298-4e74-998a-2799b8270054',20,1000,'canceled',DATE'2022-03-18 15:13:04',DATE'2022-03-18 15:13:04','b03ac8f7-c531-4a35-b4e6-59dd822d8882','d09c45f2-8d8b-437a-8697-306a6ed668d7'),
        ('bed9affa-35fb-4dc3-94db-8a34a77366f7',20,1000,'deleted',DATE'2022-03-18 15:13:04',DATE'2022-03-18 15:13:04','b03ac8f7-c531-4a35-b4e6-59dd822d8882','d09c45f2-8d8b-437a-8697-306a6ed668d7'),
        ('17d5c459-acf2-4745-ac5c-74ee2b06059b',20,1000,'deleted',DATE'2022-03-18 15:13:04',DATE'2022-03-18 15:13:04','b03ac8f7-c531-4a35-b4e6-59dd822d8882','d09c45f2-8d8b-437a-8697-306a6ed668d7');

INSERT INTO products(id,name,stock)
VALUES ('95568121-f031-4996-9c8e-d44af2738f89','iphone',1000),
       ('bbfc6b5c-078b-45f9-bd7d-e1888b2aec33','ipad',1000),
       ('f28a544a-be2d-467e-a378-5dcd20aceac4','ipod',1000),
       ('1c199161-97bd-40d0-89ed-bd43b8c5a21d' ,'imac',1000),
       ('908dc642-b483-4016-bb9b-498deae6b696','macbook',1000);

INSERT INTO orders_products(orders_id, products_id)
VALUES('95c394c0-aee3-4014-9501-d2df0bf4fbf3','95568121-f031-4996-9c8e-d44af2738f89'),
      ('51577d56-4f57-492b-90c5-976fe09a466a','bbfc6b5c-078b-45f9-bd7d-e1888b2aec33'),
      ('0a4d525a-2298-4e74-998a-2799b8270054','f28a544a-be2d-467e-a378-5dcd20aceac4'),
      ('bed9affa-35fb-4dc3-94db-8a34a77366f7','1c199161-97bd-40d0-89ed-bd43b8c5a21d' ),
      ('17d5c459-acf2-4745-ac5c-74ee2b06059b','908dc642-b483-4016-bb9b-498deae6b696');

