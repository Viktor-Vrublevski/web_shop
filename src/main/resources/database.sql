
/*Table: users*/
CREATE TABLE users(
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY ,
  login VARCHAR(100) NOT NULL ,
  password VARCHAR(255) NOT NULL
)
ENGINE = InnoDB;

/*Table: roles*/
CREATE TABLE roles(
   id INT NOT NULL AUTO_INCREMENT PRIMARY KEY ,
   name VARCHAR(30) NOT NULL
)
ENGINE = InnoDB;

CREATE TABLE user_role(
    user_id INT NOT NULL ,
    role_id INT NOT NULL ,

    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (role_id) REFERENCES roles(id)
)
ENGINE = InnoDB;

CREATE TABLE products(
     id INT NOT NULL AUTO_INCREMENT PRIMARY KEY ,
     product_name VARCHAR(200) NOT NULL ,
     price DOUBLE NOT NULL ,
     quantity INT NOT NULL ,
     description TEXT NOT NULL
)
ENGINE = InnoDB;

CREATE TABLE user_products(
     user_id INT NOT NULL,
     product_id INT NOT NULL,
     FOREIGN KEY (user_id) REFERENCES users(id),
     FOREIGN KEY (product_id) REFERENCES products(id)
)
ENGINE = InnoDB;

INSERT INTO roles(id, name) VALUES
(1,'ROLE_USER'),
(2,'ROLE_ADMIN');