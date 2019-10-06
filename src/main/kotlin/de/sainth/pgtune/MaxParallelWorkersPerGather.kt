package de.sainth.pgtune

import de.sainth.pgadjust.PostgresVersion
import de.sainth.pgadjust.SystemConfiguration
import java.lang.IllegalArgumentException

class MaxParallelWorkersPerGather(systemConfiguration: SystemConfiguration) : PgConfigurationParameter("max_parallel_workers_per_gather") {
    val maxParallelWorkersPerGather: Int = systemConfiguration.cpus?.div(2) ?: 2

    init {
        if(systemConfiguration.dbVersion == PostgresVersion.V9_4 ||
                systemConfiguration.dbVersion == PostgresVersion.V9_5) {
            throw IllegalArgumentException("$name is not supported by PostgreSQL version ${systemConfiguration.dbVersion.version}")
        }
    }

    override fun getParameterString(): String {
        return "$maxParallelWorkersPerGather"
    }
}
