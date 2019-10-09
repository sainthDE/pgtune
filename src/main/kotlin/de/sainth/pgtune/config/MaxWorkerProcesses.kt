package de.sainth.pgtune.config

class MaxWorkerProcesses(systemConfiguration: SystemConfiguration) : PgConfigurationParameter("max_worker_processes") {
    val maxWorkerProcesses: Int = systemConfiguration.cpus?.toInt() ?: 8

    init {
        require(systemConfiguration.dbVersion != PostgresVersion.V9_4) { "$name is not supported by PostgreSQL version ${systemConfiguration.dbVersion.version}" }
    }

    override fun getParameterString(): String {
        return "$maxWorkerProcesses"
    }
}
