package pl.piteron.playground;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.integration.annotation.Transformer;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@SpringBootApplication
@ImportResource("classpath*:applicationContext.xml")
public class SpringIntegrationGatewayErrorApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringIntegrationGatewayErrorApplication.class, args);
	}

	@Slf4j
	@Component
	static class FirstService {

		@Transformer(inputChannel = "functionChannel")
		public String transform(@Payload("myParam[0]") String myParam) throws InterruptedException {
			TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextInt(5000));
			return myParam + myParam + myParam;
		}

		@Transformer(inputChannel = "loggingChannel")
		public Message transform(Message message) {
			log.info(message.toString());
			return message;
		}
	}
}
