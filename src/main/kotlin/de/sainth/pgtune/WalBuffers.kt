package de.sainth.pgtune

import de.sainth.pgadjust.Memory
import de.sainth.pgadjust.SizeUnit
import de.sainth.pgadjust.SystemConfiguration

class WalBuffers(systemConfiguration: SystemConfiguration, sharedBuffers: SharedBuffers) : PgConfigurationParameter("wal_buffers") {
    private val walBuffers: Memory

    init {
// Follow auto-tuning guideline for wal_buffers added in 9.1, where it's
// set to 3% of shared_buffers up to a maximum of 16MB.
        var walBuffersValue = sharedBuffers.sharedBuffers.divide(100).multiply(3)
        val maxWalBuffer = Memory(16, SizeUnit.MB)
        if (maxWalBuffer.greaterThan(walBuffersValue)) {
            walBuffersValue = maxWalBuffer
        }
        // It's nice of wal_buffers is an even 16MB if it's near that number. Since
        // that is a common case on Windows, where shared_buffers is clipped to 512MB,
        // round upwards in that situation
        val nearValue = Memory(14, SizeUnit.MB)
        if (walBuffersValue.greaterThan(nearValue)) {
            walBuffersValue = maxWalBuffer
        }
        // if less, than 32 kb, than set it to minimum
        val minValue = Memory(32, SizeUnit.KB)
        if (minValue.greaterThan(walBuffersValue)) {
            walBuffersValue = minValue
        }
        walBuffers = walBuffersValue
    }

    override fun getParameterString(): String {
        return "$walBuffers"
    }
}
