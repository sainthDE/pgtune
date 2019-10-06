package de.sainth.pgtune

import de.sainth.pgadjust.DbApplication
import de.sainth.pgadjust.PostgresVersion
import de.sainth.pgadjust.SystemConfiguration
import java.lang.IllegalArgumentException

class CheckPointSegments(systemConfiguration: SystemConfiguration) : PgConfigurationParameter("checkpoint_segments") {
    private val checkPointSegments: Int = when (systemConfiguration.dbApplication) {
        DbApplication.WEB -> 32
        DbApplication.OLTP -> 64
        DbApplication.DATA_WAREHOUSE -> 128
        DbApplication.DESKTOP -> 3
        DbApplication.MIXED -> 32
    }

    init {
        if(systemConfiguration.dbVersion != PostgresVersion.V9_4) {
            throw IllegalArgumentException("PostgreSQL version ${systemConfiguration.dbVersion.version} no longer supports $name")
        }
    }

    override fun getParameterString(): String {
        return "$checkPointSegments"
    }

}
