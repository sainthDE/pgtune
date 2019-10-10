package de.sainth.pgtune.config

class WorkMem(systemConfiguration: SystemConfiguration,
              sharedBuffers: SharedBuffers,
              maxConnections: MaxConnections,
              maxParallelWorkersPerGather: MaxParallelWorkersPerGather? = null) : PgConfigurationParameter("work_mem") {
    val workMem: Memory

    init {

// work_mem is assigned any time a query calls for a sort, or a hash, or any other structure that needs a space allocation, which can happen multiple times per query. So you're better off assuming max_connections * 2 or max_connections * 3 is the amount of RAM that will actually use in reality. At the very least, you need to subtract shared_buffers from the amount you're distributing to connections in work_mem.
// The other thing to consider is that there's no reason to run on the edge of available memory. If you do that, there's a very high risk the out-of-memory killer will come along and start killing PostgreSQL backends. Always leave a buffer of some kind in case of spikes in memory usage. So your maximum amount of memory available in work_mem should be ((RAM - shared_buffers) / (max_connections * 3) / max_parallel_workers_per_gather).

        var workMemVal = systemConfiguration.ram.minus(sharedBuffers.sharedBuffers).divide(maxConnections.maxConnections * 3).divide(maxParallelWorkersPerGather?.maxParallelWorkersPerGather ?: 1)

        workMemVal = when(systemConfiguration.dbApplication) {
            DbApplication.DATA_WAREHOUSE -> workMemVal.divide(2)
            DbApplication.DESKTOP -> workMemVal.divide(6)
            DbApplication.MIXED -> workMemVal.divide(2)
            else -> workMemVal
        }

        val minMemory = Memory(64, SizeUnit.KB)
        workMem = if (minMemory.greaterThan(workMemVal)) minMemory else workMemVal
    }

    override fun getParameterString(): String {
        return workMem.toString(SizeUnit.KB)
    }
}