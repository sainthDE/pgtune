package de.sainth.pgtune

class EffectiveCacheSize(systemConfiguration: SystemConfiguration) : PgConfigurationParameter("effective_cache_size") {
    val effectiveCacheSize: Memory = when (systemConfiguration.dbApplication) {
        DbApplication.DESKTOP -> systemConfiguration.ram.divide(4)
        else -> systemConfiguration.ram.multiply(3).divide(4)
    }

    override fun getParameterString(): String {
        return "$effectiveCacheSize"
    }
}
