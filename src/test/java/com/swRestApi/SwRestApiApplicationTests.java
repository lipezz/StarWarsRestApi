package com.swRestApi;

import org.assertj.core.util.Arrays;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SwRestApiApplicationTests {

	@Test
	public void contextLoads() {
		SwRestApiApplication.main(Arrays.array());
	}

}
