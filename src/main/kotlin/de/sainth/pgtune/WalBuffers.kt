package de.sainth.pgtune

class WalBuffers(sharedBuffers: SharedBuffers) : PgConfigurationParameter("wal_buffers") {
    val walBuffers: Memory

    init {
// Follow auto-tuning guideline for wal_buffers added in 9.1, where it's
// set to 3% of shared_buffers up to a maximum of 16MB.
        var walBuffersValue = sharedBuffers.sharedBuffers.multiply(3).divide(100)
        val maxWalBuffer = Memory(16, SizeUnit.MB)
        if (walBuffersValue.greaterThan(maxWalBuffer)) {
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
