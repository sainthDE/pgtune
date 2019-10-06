package de.sainth.pgtune

import de.sainth.pgadjust.*
import java.lang.IllegalArgumentException

class MaxWalSize(systemConfiguration: SystemConfiguration) : PgConfigurationParameter("max_wal_size") {
    private val maxWalSize: Memory = when (systemConfiguration.dbApplication) {
        DbApplication.WEB -> Memory(2, SizeUnit.GB)
        DbApplication.OLTP -> Memory(4, SizeUnit.GB)
        DbApplication.DATA_WAREHOUSE -> Memory(8, SizeUnit.GB)
        DbApplication.DESKTOP -> Memory(1, SizeUnit.GB)
        DbApplication.MIXED -> Memory(2, SizeUnit.GB)
    }

    init {
        if(systemConfiguration.dbVersion == PostgresVersion.V9_4) {
            throw IllegalArgumentException("$name is not supported by PostgreSQL version ${systemConfiguration.dbVersion.version}")
        }
    }

    override fun getParameterString(): String {
        return "$maxWalSize"
    }

}
