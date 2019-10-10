package de.sainth.pgtune

import io.micronaut.runtime.Micronaut
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Contact
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.info.License

@OpenAPIDefinition(
    info = Info(
            title = "pgtune",
            version = "1.0",
            description = "A service to generate some optimized configuration parameters for PostgreSQL based on best practices.",
            license = License(name = "MIT", url = "https://opensource.org/licenses/MIT"),
            contact = Contact(url = "https://sainth.de", name = "Tobias Wink", email = "sainth@sainth.de")
    )
)
object Application {

    @JvmStatic
    fun main(args: Array<String>) {
        Micronaut.build()
                .packages("de.sainth.pgtune")
                .mainClass(Application.javaClass)
                .start()
    }
}