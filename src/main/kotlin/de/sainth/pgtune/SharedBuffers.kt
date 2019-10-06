package de.sainth.pgtune

import de.sainth.pgadjust.*

class SharedBuffers(systemConfiguration: SystemConfiguration) : PgConfigurationParameter("shared_buffers") {
    val sharedBuffers: Memory

    init {
        val applyCap = { buf: Memory ->
            val winMax = Memory(512, SizeUnit.MB)
            if (systemConfiguration.osType == OperatingSystem.Windows && buf.greaterThan(winMax)) winMax
            else buf
        }
        sharedBuffers = when (systemConfiguration.dbApplication) {
            DbApplication.DESKTOP -> applyCap(systemConfiguration.ram.divide(16))
            else -> applyCap(systemConfiguration.ram.divide(4))
        }
    }

    override fun getParameterString(): String {
        return "$sharedBuffers"
    }
}
