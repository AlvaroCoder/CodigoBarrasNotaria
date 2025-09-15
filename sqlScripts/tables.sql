CREATE SCHEMA notaria_secret_proyect;

CREATE TABLE client(
	id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50),
    password VARCHAR(250)
);

CREATE TABLE admin(
	id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50),
    password VARCHAR(250)
);

CREATE TABLE usb(
	id VARCHAR(250) PRIMARY KEY,
    clientId INT,
    creationDate DATETIME,
    lastModifiedDate DATETIME,
    pdfPassword VARCHAR(250),
	FOREIGN KEY (clientId) REFERENCES client(id)
);



CREATE TABLE records(
	id VARCHAR(250) PRIMARY KEY,
    name VARCHAR(250),
    description VARCHAR(500),
    clientId INT,
    usbId VARCHAR(250),
    FOREIGN KEY(clientId) REFERENCES client(id),
    FOREIGN KEY(usbId) REFERENCES usb(id)
);

CREATE TABLE pages(
	id VARCHAR(250),
    serialNumber VARCHAR(250),
    recordId VARCHAR(250),
    path VARCHAR(250)
);
