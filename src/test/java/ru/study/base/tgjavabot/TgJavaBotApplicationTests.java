package ru.study.base.tgjavabot;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TgJavaBotApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void concatTestShouldReturnTheSameString() {
		String str1 = "Hello World";
		String str2 = "Hello World";
		assertEquals("Hello World", str2);
	}
}
