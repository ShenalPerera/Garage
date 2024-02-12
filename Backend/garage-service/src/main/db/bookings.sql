DROP TABLE IF EXISTS booking;
CREATE TABLE booking (
                          booking_id INT PRIMARY KEY AUTO_INCREMENT,
                          user_id INT NOT NULL,
                          vehicle_type VARCHAR(255) NOT NULL,
                          schedule_id INT NOT NULL,
                          booking_date DATE NOT NULL,
                          booking_time TIME NOT NULL,
                          status ENUM('pending', 'confirmed', 'completed', 'canceled') NOT NULL DEFAULT 'pending',

                          FOREIGN KEY (schedule_id) REFERENCES schedule(id)
);
