CREATE TABLE IF NOT EXISTS brands (
    brand_id BIGINT NOT NULL PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS products (
    product_id BIGINT NOT NULL PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS prices (
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    brand_id BIGINT NOT NULL,
    start_date TIMESTAMP NOT NULL,
    end_date TIMESTAMP NOT NULL,
    price_list INT NOT NULL,
    product_id BIGINT NOT NULL,
    priority INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    curr VARCHAR(3) NOT NULL,
    CONSTRAINT fk_brand FOREIGN KEY (brand_id) REFERENCES brands(brand_id),
    CONSTRAINT fk_product FOREIGN KEY (product_id) REFERENCES products(product_id)
);