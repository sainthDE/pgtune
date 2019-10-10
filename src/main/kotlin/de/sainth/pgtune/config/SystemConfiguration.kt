package de.sainth.pgtune.config

import javax.validation.Valid
import javax.validation.constraints.Min


data class SystemConfiguration(val dbVersion: PostgresVersion,
                               val osType: OperatingSystem,
                               val dbApplication: DbApplication,
                               @get:Valid val ram: Memory,
                               @get:Min(1) val cpus: Short?,
                               @get:Min(1) val connections: Int?,
                               val dataStorage: DataStorage) {

    override fun toString(): String {
        return """
            # PostgreSQL-Version: ${dbVersion.version}
            # OS: $osType
            # Application: $dbApplication
            # Memory: $ram
            # CPUs: $cpus
            # Connections: $connections
            # Storage: $dataStorage
        """.trimIndent()
    }

}

enum class PostgresVersion(val version: String) {
    V9_4("9.4"),
    V9_5("9.5"),
    V9_6("9.6"),
    V10("10"),
    V11("11"),
    V12("12")
}

enum class OperatingSystem(val fancyName: String) {
    Linux("GNU Linux"),
    MacOsX("Mac OS X"),
    Windows("Microsoft Windows")
}

enum class DbApplication(val maxConnections: Int) {
    WEB(200),
    OLTP(300),
    DATA_WAREHOUSE(40),
    DESKTOP(20),
    MIXED(100)
}

enum class SizeUnit(val bytes: Long) {
    B(1),
    KB(1024),
    MB(1048576),
    GB(1073741824),
    TB(1099511627776);

    override fun toString(): String {
        return when (this) {
            B -> "B"
            KB -> "KB"
            MB -> "MB"
            GB -> "GB"
            TB -> "TB"
        }
    }
}

enum class DataStorage(val randomPageCost: Float) {
    HDD(4f),
    SSD(1.1f),
    SAN(1.1f)
}
