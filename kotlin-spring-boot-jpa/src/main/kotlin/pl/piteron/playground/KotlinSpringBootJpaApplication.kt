package pl.piteron.playground

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import java.util.*
import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@SpringBootApplication
class KotlinSpringBootJpaApplication {

    @Bean
    fun runner(repository: Repository) = CommandLineRunner {
        repository.save(WeightDay(date = Date(), weight = 75f))
        repository.save(WeightDay(date = Date(), weight = 75f))
        repository.save(WeightDay(date = Date(), weight = 76f, eaten = listOf("candies", "sausage")))
        repository.save(WeightDay(date = Date(), weight = 77f, eaten = listOf("cake", "pasta")))
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(KotlinSpringBootJpaApplication::class.java, *args)
}

@RepositoryRestResource
interface Repository : PagingAndSortingRepository<WeightDay, Long>

@Entity
data class WeightDay(
        @Id @GeneratedValue var id: Long = 0,
        var date: Date = Date(),
        var weight: Float = 0f,
        @ElementCollection(targetClass = String::class) var eaten: List<String> = emptyList()
)
