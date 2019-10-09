package de.sainth.pgtune

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
    open fun generateConfiguration(@NotNull @Valid @Body systemConfiguration: SystemConfiguration): String {
        return """
            ['max_connections', maxConnections],
            ['shared_buffers', this.formatValue(sharedBuffers)],
            ['effective_cache_size', this.formatValue(effectiveCacheSize)],
            ['maintenance_work_mem', this.formatValue(maintenanceWorkMem)],
            ['checkpoint_completion_target', checkpointCompletionTarget],
            ['wal_buffers', this.formatValue(walBuffers)],
            ['default_statistics_target', defaultStatisticsTarget],
            ['random_page_cost', randomPageCost],
            ['effective_io_concurrency', effectiveIoConcurrency],
            ['work_mem', this.formatValue(workMem)]
        """.trimIndent()

    }
}