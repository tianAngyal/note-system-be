package com.angyal.app;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "jwt.secret=testSecretKeyForJwtThatIsAtLeast32BytesLong")
class AppApplicationTests {

	@Test
	void contextLoads() {
	}

}
