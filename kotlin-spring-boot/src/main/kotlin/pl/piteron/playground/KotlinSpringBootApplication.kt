package pl.piteron.playground

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class KotlinSpringBootApplication {

    @Bean
    fun commandLineRunner() = CommandLineRunner {
        println("hello!!!")
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(KotlinSpringBootApplication::class.java, *args)
}
