CREATE SCHEMA notaria_secret_proyect;

CREATE TABLE client(
	id INT AUTO_INCREMENT PRIMARY KEY,
    dni CHAR(8),
    password VARCHAR(50),
    email VARCHAR(100),
    first_name VARCHAR(50),
    last_name VARCHAR(50)
);

CREATE TABLE admin(
	id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50),
    password VARCHAR(250),
    email VARCHAR(100)
);

CREATE TABLE province(
	id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50)
);

CREATE TABLE municipality(
	id CHAR(4) PRIMARY KEY,
    name VARCHAR(100),
	province_id INT
);

CREATE TABLE usb(
	id INT AUTO_INCREMENT PRIMARY KEY,
    description VARCHAR(250),
    client_id INT,
    creation_date DATETIME,
    last_modified_date DATETIME,
    pdf_password VARCHAR(250),
	FOREIGN KEY (client_id) REFERENCES client(id)
);

CREATE TABLE file(
	id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50),
    description VARCHAR(250),
    id_usb INT,
    creation_date DATETIME,
    last_modified_date DATETIME,
    FOREIGN KEY(id_usb) REFERENCES usb(id)
);

CREATE TABLE section(
	id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50),
    file_id INT,
    creation_date DATETIME,
    last_modified_date DATETIME,
    FOREIGN KEY(file_id) REFERENCES file(id)
);

CREATE TABLE page(
	id INT AUTO_INCREMENT PRIMARY KEY,
    serial_number VARCHAR(20),
    path VARCHAR(250),
    section_id INT,
    FOREIGN KEY(section_id) REFERENCES section(id)
);
