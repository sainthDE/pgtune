package de.sainth.pgtune

class MaintenanceWorkMem(systemConfiguration: SystemConfiguration) : PgConfigurationParameter("maintenance_work_mem") {
    val maintenanceWorkMem: Memory

    init {
        val mem = when (systemConfiguration.dbApplication) {
            DbApplication.DATA_WAREHOUSE -> systemConfiguration.ram.divide(8)
            else -> systemConfiguration.ram.divide(16)
        }
        // Cap maintenance RAM at 2GB on servers with lots of memory
        maintenanceWorkMem = if (mem.greaterThan(Memory(2, SizeUnit.GB))) {
            if (systemConfiguration.osType == OperatingSystem.Windows)
            // 2048MB (2 GB) will raise error at Windows, so we need remove 1 MB from it
                Memory(2, SizeUnit.GB).minus(Memory(1, SizeUnit.MB))
            else
                Memory(2, SizeUnit.GB)
        } else
            mem
    }

    override fun getParameterString(): String {
        return "$maintenanceWorkMem"
    }
}
