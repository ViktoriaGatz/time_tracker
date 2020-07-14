package ru.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.example.been.AppContext;
import ru.example.entity.task.Task;
import ru.example.entity.user.User;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

@SpringBootApplication
public class Application {

	private static final Logger log = Logger.getLogger(Application.class.getName());

	public static void main(String[] args) {
		ApplicationContext context = new AnnotationConfigApplicationContext(AppContext.class);

		SpringApplication.run(Application.class, args);
		log.info("Start application");

//		User user = context.getBean("userBean", User.class);
//		user.setId(7L);
//		user.setName("Viki");
//		System.out.println(user.getId() + " " + user.getName());
//
//		user.setTask(new Task("Homework"));
//		user.getTask().startTime();
//
//		try {
//			Thread.sleep(5000L);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//
//		user.getTask().stopTime();
//
//		user.getTask().startTime();
//
//		try {
//			Thread.sleep(5000L);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//
//		user.getTask().stopTime();


	}
}
