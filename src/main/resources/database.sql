/*Table: users*/
CREATE TABLE users(
    id SERIAL PRIMARY KEY ,
    login    VARCHAR(100) NOT NULL,
    password VARCHAR(255) NOT NULL,
    name_organization VARCHAR(100),
    UNN INT,
    address VARCHAR(200),
    name_bank VARCHAR(90),
    address_bank VARCHAR(200),
    IBAN VARCHAR(40),
    BIC VARCHAR(20),
    number_tel INT
);


/*Table: roles*/
CREATE TABLE roles(
    id  SERIAL PRIMARY KEY,
    name VARCHAR(30) NOT NULL
);


INSERT INTO roles(id, name)
VALUES (1, 'ROLE_USER'),
       (2, 'ROLE_ADMIN');


CREATE TABLE user_role(
    user_id INT NOT NULL,
    role_id INT NOT NULL,

    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (role_id) REFERENCES roles (id)
);

CREATE TABLE orders (
    id SERIAL PRIMARY KEY ,
    date_order DATE NOT NULL ,
    USER_ID INT NOT NULL ,
    cost DOUBLE PRECISION NOT NULL ,
    payment_status BOOLEAN NOT NULL
);


CREATE TABLE papers(
    id  SERIAL PRIMARY KEY,
    paper_name  VARCHAR(200) NOT NULL,
    price  DOUBLE PRECISION NOT NULL ,
    quantity INT NOT NULL,
    description TEXT NOT NULL,
    url_image   VARCHAR(200)
);


CREATE TABLE pens(
    id SERIAL  PRIMARY KEY,
    pen_name    VARCHAR(200) NOT NULL,
    price       DOUBLE PRECISION NOT NULL,
    quantity    INT NOT NULL,
    description TEXT NOT NULL,
    url_image   VARCHAR(200)
);


CREATE TABLE holes(
    id  SERIAL PRIMARY KEY,
    hole_name   VARCHAR(200) NOT NULL,
    price       DOUBLE PRECISION NOT NULL,
    quantity    INT NOT NULL,
    description TEXT NOT NULL,
    url_image   VARCHAR(200)
);

CREATE TABLE calculators(
    id  SERIAL PRIMARY KEY,
    calculator_name VARCHAR(200) NOT NULL,
    price DOUBLE PRECISION NOT NULL,
    quantity  INT  NOT NULL,
    description  TEXT NOT NULL,
    url_image VARCHAR(200)
);

CREATE TABLE staplers(
    id  SERIAL PRIMARY KEY,
    stapler_name VARCHAR(200) NOT NULL,
    price  DOUBLE PRECISION NOT NULL,
    quantity  INT  NOT NULL,
    description  TEXT  NOT NULL,
    url_image    VARCHAR(200)
);

CREATE TABLE trays(
    id  SERIAL PRIMARY KEY,
    trays_name  VARCHAR(200) NOT NULL,
    price   DOUBLE PRECISION NOT NULL,
    quantity  INT NOT NULL,
    description TEXT  NOT NULL,
    url_image   VARCHAR(200)
);

CREATE TABLE householders(
    id  SERIAL PRIMARY KEY,
    holder_name VARCHAR(200) NOT NULL,
    price  DOUBLE PRECISION NOT NULL,
    quantity INT  NOT NULL,
    description TEXT  NOT NULL,
    url_image   VARCHAR(200)
);

CREATE TABLE folders(
    id  SERIAL PRIMARY KEY,
    folder_name VARCHAR(200) NOT NULL,
    price  DOUBLE PRECISION  NOT NULL,
    quantity INT  NOT NULL,
    description TEXT NOT NULL,
    url_image   VARCHAR(200)
);

