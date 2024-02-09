DROP TABLE IF EXISTS service;

CREATE TABLE  service(
    id INT PRIMARY KEY auto_increment,
    service_name VARCHAR(25) NOT NULL ,
    duration INT NOT NULL
);