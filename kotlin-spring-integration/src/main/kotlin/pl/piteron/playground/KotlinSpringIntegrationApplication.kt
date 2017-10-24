package pl.piteron.playground

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class KotlinSpringIntegrationApplication {

//    @Bean
//    fun flow(): IntegrationFlow =
//            IntegrationFlows.from(Files.inboundAdapter(File("")), {it.poller(Pollers.fixedDelay(1000))})
//                    .handle(Files.)
//                    .get()
//
//    fun a() {
//
//    }
}

fun main(args: Array<String>) {
    SpringApplication.run(KotlinSpringIntegrationApplication::class.java, *args)
}
