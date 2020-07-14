package ru.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.example.been.AppContext;

@SpringBootApplication
public class Application {

	private static final Logger log = Logger.getLogger(Application.class.getName());

	public static void main(String[] args) {
		ApplicationContext context = new AnnotationConfigApplicationContext(AppContext.class);

		SpringApplication.run(Application.class, args);
		log.info("Start application");

	}
}
