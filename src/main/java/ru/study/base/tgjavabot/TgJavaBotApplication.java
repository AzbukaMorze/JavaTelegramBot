package ru.study.base.tgjavabot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableTransactionManagement
public class TgJavaBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(TgJavaBotApplication.class, args);
	}

}
