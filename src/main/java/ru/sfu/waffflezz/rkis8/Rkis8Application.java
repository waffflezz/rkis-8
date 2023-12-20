package ru.sfu.waffflezz.rkis8;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.sfu.waffflezz.rkis8.messaging.MessageReceiver;

/**
 * Класс, являющейся точкой входа в программу
 */
@SpringBootApplication
public class Rkis8Application {
	/**
	 * Главный метод, запускается java
	 *
	 * @param args аргументы, которые пробрасываются в приложения через командную строку при запуске
	 */
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(Rkis8Application.class, args);
		MessageReceiver messageReceiver = context.getBean(MessageReceiver.class);
		messageReceiver.startReceivingMessages();
	}
}
