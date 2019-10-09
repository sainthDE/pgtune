package de.sainth.pgtune.config

class MinWalSize(systemConfiguration: SystemConfiguration) : PgConfigurationParameter("min_wal_size") {
    val minWalSize: Memory = when (systemConfiguration.dbApplication) {
        DbApplication.WEB -> Memory(1, SizeUnit.GB)
        DbApplication.OLTP -> Memory(2, SizeUnit.GB)
        DbApplication.DATA_WAREHOUSE -> Memory(4, SizeUnit.GB)
        DbApplication.DESKTOP -> Memory(100, SizeUnit.MB)
        DbApplication.MIXED -> Memory(1, SizeUnit.GB)
    }

    init {
        if(systemConfiguration.dbVersion == PostgresVersion.V9_4) {
            throw IllegalArgumentException("$name is not supported by PostgreSQL version ${systemConfiguration.dbVersion.version}")
        }
    }

    override fun getParameterString(): String {
        return "$minWalSize"
    }

}
