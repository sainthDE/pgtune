package de.sainth.pgtune

import de.sainth.pgadjust.PostgresVersion
import de.sainth.pgadjust.SystemConfiguration
import java.lang.IllegalArgumentException

class MaxParallelWorkers(systemConfiguration: SystemConfiguration) : PgConfigurationParameter("max_parallel_workers") {
    private val maxParallelWorkersPerGather: Int = systemConfiguration.cpus?.toInt() ?: 8

    init {
        if(systemConfiguration.dbVersion == PostgresVersion.V9_4 ||
                systemConfiguration.dbVersion == PostgresVersion.V9_5||
                systemConfiguration.dbVersion == PostgresVersion.V9_6) {
            throw IllegalArgumentException("$name is not supported by PostgreSQL version ${systemConfiguration.dbVersion.version}")
        }
    }

    override fun getParameterString(): String {
        return "$maxParallelWorkersPerGather"
    }
}
