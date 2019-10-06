package de.sainth.pgtune

import de.sainth.pgadjust.DbApplication
import de.sainth.pgadjust.Memory
import de.sainth.pgadjust.SystemConfiguration

class EffectiveCacheSize(systemConfiguration: SystemConfiguration) : PgConfigurationParameter("effective_cache_size") {
    private val effectiveCacheSize: Memory = when (systemConfiguration.dbApplication) {
        DbApplication.DESKTOP -> systemConfiguration.ram.divide(4)
        else -> systemConfiguration.ram.multiply(3).divide(4)
    }

    override fun getParameterString(): String {
        return "$effectiveCacheSize"
    }
}
