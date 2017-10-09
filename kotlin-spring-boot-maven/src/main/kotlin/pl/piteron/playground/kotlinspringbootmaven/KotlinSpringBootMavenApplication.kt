package pl.piteron.playground.kotlinspringbootmaven

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class KotlinSpringBootMavenApplication

fun main(args: Array<String>) {
    SpringApplication.run(KotlinSpringBootMavenApplication::class.java, *args)
}
