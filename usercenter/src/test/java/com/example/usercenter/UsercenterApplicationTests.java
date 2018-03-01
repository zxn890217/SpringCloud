package com.example.usercenter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsercenterApplicationTests {

	@Test
	public void contextLoads() {
		String path = "/1/2/3/";
		String[] paths = path.split("/");
		System.out.print(paths);
	}

}
