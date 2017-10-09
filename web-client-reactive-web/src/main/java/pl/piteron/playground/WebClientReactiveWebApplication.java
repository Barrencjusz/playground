package pl.piteron.playground;

import java.util.concurrent.CompletableFuture;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@SpringBootApplication
@RestController
@Slf4j
@EnableAsync
@RequiredArgsConstructor
public class WebClientReactiveWebApplication {

	private static final String URL1 = "http://localhost:8080/first/?req={req}";
	private static final String URL2 = "http://localhost:8080/second/?req={req}";

	public static void main(String[] args) {
		SpringApplication.run(WebClientReactiveWebApplication.class, args);
	}

	@Service
	public static class MyService {

		@Async
		CompletableFuture<String> work(String req) {
			return CompletableFuture.completedFuture(req + "/asyncWork");
		}
	}

	private final MyService myService;

	private WebClient webClient = WebClient.create();

	@GetMapping("/rest")
	public Mono<String> rest(int idx) {
		log.info("rest");
		Mono<ClientResponse> resMono =
				webClient.get()
						.uri(URL1, idx)
						.exchange();
		return resMono
				.flatMap(clientResponse -> clientResponse.bodyToMono(String.class))
				.doOnNext(log::info)
				.flatMap(res1 -> webClient.get().uri(URL2, res1).exchange())
				.flatMap(c -> c.bodyToMono(String.class))
				.doOnNext(log::info)
				.flatMap(res2 -> Mono.fromCompletionStage(myService.work(res2)))
				.doOnNext(log::info);
	}

	@GetMapping("first")
	public Mono<Integer> first(int req) {
		return Mono.just(req + 1);
	}

	@GetMapping("second")
	public Mono<Integer> second(int req) {
		return Mono.just(req + 10);
	}
}