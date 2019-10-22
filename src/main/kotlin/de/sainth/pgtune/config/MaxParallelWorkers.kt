package de.sainth.pgtune.config

class MaxParallelWorkers(systemConfiguration: SystemConfiguration) : PgConfigurationParameter("max_parallel_workers") {
    val maxParallelWorkers: Int = systemConfiguration.cores?.toInt() ?: 8

    init {
        require(!(systemConfiguration.dbVersion == PostgresVersion.V9_4 ||
                systemConfiguration.dbVersion == PostgresVersion.V9_5 ||
                systemConfiguration.dbVersion == PostgresVersion.V9_6)) { "$name is not supported by PostgreSQL version ${systemConfiguration.dbVersion.version}" }
    }

    override fun getParameterString(): String {
        return "$maxParallelWorkers"
    }
}
