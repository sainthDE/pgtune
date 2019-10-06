package de.sainth.pgtune

import de.sainth.pgadjust.DbApplication
import de.sainth.pgadjust.SystemConfiguration

class DefaultStatisticsTarget(systemConfiguration: SystemConfiguration) : PgConfigurationParameter("default_statistics_target") {
    private val defaultStatisticsTarget: Int = when(systemConfiguration.dbApplication) {
        DbApplication.DATA_WAREHOUSE -> 500
        else -> 100
    }

    override fun getParameterString(): String {
        return "$defaultStatisticsTarget"
    }
}
