package de.sainth.pgadjust

import javax.validation.constraints.Min

data class Memory(@get:Min(1) val memory: Long,
                  val unit: SizeUnit) {
    fun greaterThan(other: Memory) : Boolean {
        return this.asBytes() > other.asBytes()
    }

    fun divide(divisor: Int): Memory {
        val mem = this.asBytes() / divisor;
        return optimizeUnit(mem)
    }

    fun multiply(multiplicator: Int): Memory {
        val mem = this.asBytes() * multiplicator;
        return optimizeUnit(mem)
    }

    fun minus(minuend : Memory) : Memory {
        return optimizeUnit(this.asBytes() - minuend.asBytes())
    }

    private fun asBytes(): Long {
        return memory * unit.bytes
    }

    private fun optimizeUnit(byteValue: Long): Memory {
        return when {
            byteValue >= SizeUnit.TB.bytes && byteValue % SizeUnit.TB.bytes == 0L-> Memory(byteValue / SizeUnit.TB.bytes, SizeUnit.TB)
            byteValue >= SizeUnit.GB.bytes && byteValue % SizeUnit.GB.bytes == 0L -> Memory(byteValue / SizeUnit.GB.bytes, SizeUnit.GB)
            byteValue >= SizeUnit.MB.bytes && byteValue % SizeUnit.MB.bytes == 0L -> Memory(byteValue / SizeUnit.MB.bytes, SizeUnit.MB)
            byteValue >= SizeUnit.KB.bytes && byteValue % SizeUnit.KB.bytes == 0L -> Memory(byteValue / SizeUnit.KB.bytes, SizeUnit.KB)
            else -> Memory(byteValue, SizeUnit.B)
        }
    }

    override fun toString(): String {
        return "$memory$unit"
    }
}