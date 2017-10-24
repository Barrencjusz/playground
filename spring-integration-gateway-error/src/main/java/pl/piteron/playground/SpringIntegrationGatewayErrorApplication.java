package pl.piteron.playground;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.integration.annotation.Transformer;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.support.GenericMessage;
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
			if(ThreadLocalRandom.current().nextBoolean()) {
				throw new RuntimeException("surprise!");
			}
			TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextInt(4000));
			return myParam + myParam + myParam;
		}

		@MessageExceptionHandler
		@Transformer(inputChannel = "errorFunctionChannel")
		public String transform(GenericMessage<MessagingException> message) {
			return "olaboga! " + message.getPayload().getLocalizedMessage()+ " " + message.getPayload().getFailedMessage().getPayload().toString();
		}
	}
}
