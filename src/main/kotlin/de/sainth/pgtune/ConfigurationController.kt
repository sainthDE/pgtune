package de.sainth.pgtune

import de.sainth.pgtune.config.PostgresConfiguration
import de.sainth.pgtune.config.SystemConfiguration
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import javax.validation.Valid
import javax.validation.constraints.NotNull

@Controller("/configuration")
open class ConfigurationController {

    @Post(produces = [MediaType.TEXT_PLAIN])
    open fun generateConfiguration(@NotNull @Valid @Body systemConfiguration: SystemConfiguration): String =
            "$systemConfiguration\n\n" +
                    PostgresConfiguration(systemConfiguration).getConfiguration()
}