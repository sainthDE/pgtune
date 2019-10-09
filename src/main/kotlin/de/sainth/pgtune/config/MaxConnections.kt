package de.sainth.pgtune.config

class MaxConnections(systemConfiguration: SystemConfiguration) : PgConfigurationParameter("max_connections") {
    val maxConnections: Int = systemConfiguration.connections
            ?: systemConfiguration.dbApplication.maxConnections

    override fun getParameterString(): String {
        return "$maxConnections"
    }
}