package de.sainth.pgtune

import de.sainth.pgadjust.SystemConfiguration

class MaxConnections(systemConfiguration: SystemConfiguration) : PgConfigurationParameter("max_connections") {
    val maxConnections: Int = systemConfiguration.connections
            ?: systemConfiguration.dbApplication.maxConnections

    override fun getParameterString(): String {
        return "$maxConnections"
    }
}