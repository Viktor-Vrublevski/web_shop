
/*Table: users*/
CREATE TABLE users(
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY ,
  login VARCHAR(100) NOT
      NULL ,
  password VARCHAR(255) NOT NULL
)
ENGINE = InnoDB;

/*Table: roles*/
CREATE TABLE roles(
   id INT NOT NULL AUTO_INCREMENT PRIMARY KEY ,
   name VARCHAR(30) NOT NULL
)
ENGINE = InnoDB;

INSERT INTO roles(id, name) VALUES
(1,'ROLE_USER'),
(2,'ROLE_ADMIN');


CREATE TABLE user_role(
    user_id INT NOT NULL ,
    role_id INT NOT NULL ,

    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (role_id) REFERENCES roles(id)
)
ENGINE = InnoDB;

CREATE TABLE papers(
     id INT NOT NULL AUTO_INCREMENT PRIMARY KEY ,
     paper_name VARCHAR(200) NOT NULL ,
     price DOUBLE NOT NULL ,
     quantity INT NOT NULL ,
     description TEXT NOT NULL,
     url_image VARCHAR(200)
)
ENGINE = InnoDB;


CREATE TABLE pens(
   id INT NOT NULL AUTO_INCREMENT PRIMARY KEY ,
   pen_name VARCHAR(200) NOT NULL ,
   price DOUBLE NOT NULL ,
   quantity INT NOT NULL ,
   description TEXT NOT NULL ,
    url_image VARCHAR(200)
)
ENGINE = InnoDB;


CREATE TABLE holes(
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY ,
    hole_name VARCHAR(200) NOT NULL ,
    price DOUBLE NOT NULL ,
    quantity INT NOT NULL ,
    description TEXT NOT NULL ,
    url_image VARCHAR(200)
)
    ENGINE = InnoDB;

 CREATE TABLE calculators(
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY ,
    calculator_name VARCHAR(200) NOT NULL ,
    price DOUBLE NOT NULL ,
    quantity INT NOT NULL ,
    description TEXT NOT NULL ,
    url_image VARCHAR(200)
 )
 ENGINE = InnoDB;

CREATE TABLE staplers(
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY ,
    stapler_name VARCHAR(200) NOT NULL ,
    price DOUBLE NOT NULL ,
    quantity INT NOT NULL ,
    description TEXT NOT NULL ,
    url_image VARCHAR(200)
 )
 ENGINE = InnoDB;

CREATE TABLE trays(
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY ,
    trays_name VARCHAR(200) NOT NULL ,
    price DOUBLE NOT NULL ,
    quantity INT NOT NULL ,
    description TEXT NOT NULL ,
    url_image VARCHAR(200)
 )
 ENGINE = InnoDB;

CREATE TABLE householders(
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY ,
    holder_name VARCHAR(200) NOT NULL ,
    price DOUBLE NOT NULL ,
    quantity INT NOT NULL ,
    description TEXT NOT NULL ,
    url_image VARCHAR(200)
 )
 ENGINE = InnoDB;

 CREATE TABLE folders(
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY ,
    folder_name VARCHAR(200) NOT NULL ,
    price DOUBLE NOT NULL ,
    quantity INT NOT NULL ,
    description TEXT NOT NULL ,
    url_image VARCHAR(200)
 )
 ENGINE = InnoDB;