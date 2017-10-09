package pl.piteron.playground

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@SpringBootApplication
class KotlinSpringBootApplication {

    @Bean
    fun commandLineRunner(
        someComponent: SomeComponent,
        exampleProperties: ExampleProperties
    ) = CommandLineRunner {
        println("hello!!!")
        someComponent.saying()
        println("${exampleProperties.a} ${exampleProperties.b}")
    }

    @Bean
    fun say(): String = "yolo"

    @Component
    class SomeComponent(private val say: String) {

        fun saying() = println(say)
    }

    @Component
    @ConfigurationProperties("example")
    data class ExampleProperties(
            var a: String,
        var b: String
    )
}

fun main(args: Array<String>) {
    SpringApplication.run(KotlinSpringBootApplication::class.java, *args)
}


