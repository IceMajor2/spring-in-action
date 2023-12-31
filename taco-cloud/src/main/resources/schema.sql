CREATE TABLE IF NOT EXISTS taco_order (
    id IDENTITY,
    delivery_name VARCHAR(50) NOT NULL,
    delivery_street VARCHAR(50) NOT NULL,
    delivery_city VARCHAR(50) NOT NULL,
    delivery_state VARCHAR(50) NOT NULL,
    delivery_ZIP VARCHAR(10) NOT NULL,
    cc_number VARCHAR(16) NOT NULL,
    cc_expiration VARCHAR(5) NOT NULL,
    cc_cvv VARCHAR(3) NOT NULL,
    placed_at TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS taco (
    id IDENTITY,
    name VARCHAR(50),
    taco_order BIGINT,
    taco_order_key BIGINT,
    created_at TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS ingredient_taco (
    ingredient VARCHAR(4) NOT NULL,
    name VARCHAR(25) NOT NULL,
    type VARCHAR(10) NOT NULL,
    taco BIGINT NOT NULL,
    PRIMARY KEY (taco, ingredient)
);

CREATE TABLE IF NOT EXISTS ingredient (
    id VARCHAR(4) UNIQUE NOT NULL,
    name VARCHAR(25) NOT NULL,
    type VARCHAR(10) NOT NULL
);

CREATE TABLE IF NOT EXISTS _user (
    id IDENTITY,
    username VARCHAR(32) NOT NULL,
    password VARCHAR(255) NOT NULL,
    fullname VARCHAR(64) NOT NULL,
    street VARCHAR(64) NOT NULL,
    city VARCHAR(32) NOT NULL,
    state VARCHAR(64) NOT NULL,
    zip VARCHAR(10) NOT NULL,
    phone_number INT NOT NULL
);

ALTER TABLE taco
    ADD FOREIGN KEY (taco_order) REFERENCES taco_order(id);