package org.isa.garage;

import org.isa.garage.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GarageApplicationTests {
	@Autowired
	private UserService userService;
	@Test
	void contextLoads() {
	}


}
