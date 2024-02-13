CREATE TABLE schedule (
    id INT AUTO_INCREMENT PRIMARY KEY ,
    start_time TIME NOT NULL ,
    end_time TIME NOT NULL ,
    date DATE NOT NULL ,
    service_id INT NOT NULL ,
    max_capacity INT NOT NULL ,
    current_capacity INT NOT NULL
);