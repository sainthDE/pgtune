package de.sainth.pgtune

class MaxParallelWorkersPerGather(systemConfiguration: SystemConfiguration) : PgConfigurationParameter("max_parallel_workers_per_gather") {
    val maxParallelWorkersPerGather: Int = systemConfiguration.cpus?.div(2) ?: 2

    init {
        require(!(systemConfiguration.dbVersion == PostgresVersion.V9_4 ||
                systemConfiguration.dbVersion == PostgresVersion.V9_5)) { "$name is not supported by PostgreSQL version ${systemConfiguration.dbVersion.version}" }
    }

    override fun getParameterString(): String {
        return "$maxParallelWorkersPerGather"
    }
}
