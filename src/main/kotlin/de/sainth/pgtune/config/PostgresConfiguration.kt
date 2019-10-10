package de.sainth.pgtune.config

sealed class PostgresConfiguration {
    companion object {
        operator fun invoke(systemConfiguration: SystemConfiguration): PostgresConfiguration {
            return when (systemConfiguration.dbVersion) {
                PostgresVersion.V9_4 -> PostgresConfiguration9_4(systemConfiguration)
                PostgresVersion.V9_5 -> PostgresConfiguration9_5(systemConfiguration)
                PostgresVersion.V9_6 -> PostgresConfiguration9_6(systemConfiguration)
                PostgresVersion.V10 -> PostgresConfiguration10(systemConfiguration)
                PostgresVersion.V11 -> PostgresConfiguration11(systemConfiguration)
                PostgresVersion.V12 -> PostgresConfiguration12(systemConfiguration)
            }
        }
    }

    abstract fun getConfiguration(): String

    /*

     */
    class PostgresConfiguration9_4(systemConfiguration: SystemConfiguration) : PostgresConfiguration() {
        private val maxConnections = MaxConnections(systemConfiguration)
        private val sharedBuffers = SharedBuffers(systemConfiguration)
        private val effectiveCacheSize = EffectiveCacheSize(systemConfiguration)
        private val maintenanceWorkMem = MaintenanceWorkMem(systemConfiguration)
        private val checkPointCompletionTarget = CheckPointCompletionTarget(systemConfiguration)
        private val walBuffers = WalBuffers(sharedBuffers)
        private val defaultStatisticsTarget = DefaultStatisticsTarget(systemConfiguration)
        private val randomPageCost = RandomPageCost(systemConfiguration)
        private val effectiveIoConcurrency = EffectiveIoConcurrency(systemConfiguration)
        private val workMem = WorkMem(systemConfiguration, sharedBuffers, maxConnections)
        private val checkPointSegments = CheckPointSegments(systemConfiguration)

        override fun getConfiguration(): String {
            return "${maxConnections.toPgConfigurationLine()}\n" +
                   "${sharedBuffers.toPgConfigurationLine()}\n" +
                   "${effectiveCacheSize.toPgConfigurationLine()}\n" +
                   "${maintenanceWorkMem.toPgConfigurationLine()}\n" +
                   "${checkPointCompletionTarget.toPgConfigurationLine()}\n" +
                   "${walBuffers.toPgConfigurationLine()}\n" +
                   "${defaultStatisticsTarget.toPgConfigurationLine()}\n" +
                   "${randomPageCost.toPgConfigurationLine()}\n" +
                   "${effectiveIoConcurrency.toPgConfigurationLine()}\n" +
                   "${workMem.toPgConfigurationLine()}\n" +
                   "${checkPointSegments.toPgConfigurationLine()}\n"
        }
    }

    /*
    # DB Version: 9.5
# OS Type: linux
# DB Type: web
# Total Memory (RAM): 4 GB
# CPUs num: 4
# Connections num: 400
# Data Storage: ssd

max_connections = 400
shared_buffers = 1GB
effective_cache_size = 3GB
maintenance_work_mem = 256MB
checkpoint_completion_target = 0.7
wal_buffers = 16MB
default_statistics_target = 100
random_page_cost = 1.1
effective_io_concurrency = 200
work_mem = 2621kB
min_wal_size = 1GB
max_wal_size = 2GB
max_worker_processes = 4
     */
    class PostgresConfiguration9_5(systemConfiguration: SystemConfiguration) : PostgresConfiguration() {
        private val maxConnections = MaxConnections(systemConfiguration)
        private val sharedBuffers = SharedBuffers(systemConfiguration)
        private val effectiveCacheSize = EffectiveCacheSize(systemConfiguration)
        private val maintenanceWorkMem = MaintenanceWorkMem(systemConfiguration)
        private val checkPointCompletionTarget = CheckPointCompletionTarget(systemConfiguration)
        private val walBuffers = WalBuffers(sharedBuffers)
        private val defaultStatisticsTarget = DefaultStatisticsTarget(systemConfiguration)
        private val randomPageCost = RandomPageCost(systemConfiguration)
        private val effectiveIoConcurrency = EffectiveIoConcurrency(systemConfiguration)
        private val workMem = WorkMem(systemConfiguration, sharedBuffers, maxConnections)
        private val minWalSize = MinWalSize(systemConfiguration)
        private val maxWalSize = MaxWalSize(systemConfiguration)
        private val maxWorkerProcesses = MaxWorkerProcesses(systemConfiguration)

        override fun getConfiguration(): String = """${maxConnections.toPgConfigurationLine()}
            ${sharedBuffers.toPgConfigurationLine()}
            ${effectiveCacheSize.toPgConfigurationLine()}
            ${maintenanceWorkMem.toPgConfigurationLine()}
            ${checkPointCompletionTarget.toPgConfigurationLine()}
            ${walBuffers.toPgConfigurationLine()}
            ${defaultStatisticsTarget.toPgConfigurationLine()}
            ${randomPageCost.toPgConfigurationLine()}
            ${effectiveIoConcurrency.toPgConfigurationLine()}
            ${workMem.toPgConfigurationLine()}
            ${minWalSize.toPgConfigurationLine()}
            ${maxWalSize.toPgConfigurationLine()}
            ${maxWorkerProcesses.toPgConfigurationLine()}
        """.trimIndent()

    }

    /*
    # DB Version: 9.6
# OS Type: linux
# DB Type: web
# Total Memory (RAM): 4 GB
# CPUs num: 4
# Connections num: 400
# Data Storage: ssd

max_connections = 400
shared_buffers = 1GB
effective_cache_size = 3GB
maintenance_work_mem = 256MB
checkpoint_completion_target = 0.7
wal_buffers = 16MB
default_statistics_target = 100
random_page_cost = 1.1
effective_io_concurrency = 200
work_mem = 1310kB
min_wal_size = 1GB
max_wal_size = 2GB
max_worker_processes = 4
max_parallel_workers_per_gather = 2
     */
    class PostgresConfiguration9_6(systemConfiguration: SystemConfiguration) : PostgresConfiguration() {
        private val maxConnections = MaxConnections(systemConfiguration)
        private val sharedBuffers = SharedBuffers(systemConfiguration)
        private val effectiveCacheSize = EffectiveCacheSize(systemConfiguration)
        private val maintenanceWorkMem = MaintenanceWorkMem(systemConfiguration)
        private val checkPointCompletionTarget = CheckPointCompletionTarget(systemConfiguration)
        private val walBuffers = WalBuffers(sharedBuffers)
        private val defaultStatisticsTarget = DefaultStatisticsTarget(systemConfiguration)
        private val randomPageCost = RandomPageCost(systemConfiguration)
        private val effectiveIoConcurrency = EffectiveIoConcurrency(systemConfiguration)
        private val workMem = WorkMem(systemConfiguration, sharedBuffers, maxConnections)
        private val minWalSize = MinWalSize(systemConfiguration)
        private val maxWalSize = MaxWalSize(systemConfiguration)
        private val maxWorkerProcesses = MaxWorkerProcesses(systemConfiguration)
        private val maxParallelWorkersPerGather = MaxParallelWorkersPerGather(systemConfiguration)

        override fun getConfiguration(): String = """${maxConnections.toPgConfigurationLine()}
            ${sharedBuffers.toPgConfigurationLine()}
            ${effectiveCacheSize.toPgConfigurationLine()}
            ${maintenanceWorkMem.toPgConfigurationLine()}
            ${checkPointCompletionTarget.toPgConfigurationLine()}
            ${walBuffers.toPgConfigurationLine()}
            ${defaultStatisticsTarget.toPgConfigurationLine()}
            ${randomPageCost.toPgConfigurationLine()}
            ${effectiveIoConcurrency.toPgConfigurationLine()}
            ${workMem.toPgConfigurationLine()}
            ${minWalSize.toPgConfigurationLine()}
            ${maxWalSize.toPgConfigurationLine()}
            ${maxWorkerProcesses.toPgConfigurationLine()}
            ${maxParallelWorkersPerGather.toPgConfigurationLine()}
        """.trimIndent()
    }

    class PostgresConfiguration10(systemConfiguration: SystemConfiguration) : PostgresConfiguration() {
        private val maxConnections = MaxConnections(systemConfiguration)
        private val sharedBuffers = SharedBuffers(systemConfiguration)
        private val effectiveCacheSize = EffectiveCacheSize(systemConfiguration)
        private val maintenanceWorkMem = MaintenanceWorkMem(systemConfiguration)
        private val checkPointCompletionTarget = CheckPointCompletionTarget(systemConfiguration)
        private val walBuffers = WalBuffers(sharedBuffers)
        private val defaultStatisticsTarget = DefaultStatisticsTarget(systemConfiguration)
        private val randomPageCost = RandomPageCost(systemConfiguration)
        private val effectiveIoConcurrency = EffectiveIoConcurrency(systemConfiguration)
        private val workMem = WorkMem(systemConfiguration, sharedBuffers, maxConnections)
        private val minWalSize = MinWalSize(systemConfiguration)
        private val maxWalSize = MaxWalSize(systemConfiguration)
        private val maxWorkerProcesses = MaxWorkerProcesses(systemConfiguration)
        private val maxParallelWorkersPerGather = MaxParallelWorkersPerGather(systemConfiguration)

        override fun getConfiguration(): String = """${maxConnections.toPgConfigurationLine()}
            ${sharedBuffers.toPgConfigurationLine()}
            ${effectiveCacheSize.toPgConfigurationLine()}
            ${maintenanceWorkMem.toPgConfigurationLine()}
            ${checkPointCompletionTarget.toPgConfigurationLine()}
            ${walBuffers.toPgConfigurationLine()}
            ${defaultStatisticsTarget.toPgConfigurationLine()}
            ${randomPageCost.toPgConfigurationLine()}
            ${effectiveIoConcurrency.toPgConfigurationLine()}
            ${workMem.toPgConfigurationLine()}
            ${minWalSize.toPgConfigurationLine()}
            ${maxWalSize.toPgConfigurationLine()}
            ${maxWorkerProcesses.toPgConfigurationLine()}
            ${maxParallelWorkersPerGather.toPgConfigurationLine()}
        """.trimIndent()
    }

    /*
    # DB Version: 11
# OS Type: linux
# DB Type: web
# Total Memory (RAM): 4 GB
# CPUs num: 4
# Connections num: 400
# Data Storage: ssd

max_connections = 400
shared_buffers = 1GB
effective_cache_size = 3GB
maintenance_work_mem = 256MB
checkpoint_completion_target = 0.7
wal_buffers = 16MB
default_statistics_target = 100
random_page_cost = 1.1
effective_io_concurrency = 200
work_mem = 1310kB
min_wal_size = 1GB
max_wal_size = 2GB
max_worker_processes = 4
max_parallel_workers_per_gather = 2
max_parallel_workers = 4
     */
    class PostgresConfiguration11(systemConfiguration: SystemConfiguration) : PostgresConfiguration() {
        private val maxConnections = MaxConnections(systemConfiguration)
        private val sharedBuffers = SharedBuffers(systemConfiguration)
        private val effectiveCacheSize = EffectiveCacheSize(systemConfiguration)
        private val maintenanceWorkMem = MaintenanceWorkMem(systemConfiguration)
        private val checkPointCompletionTarget = CheckPointCompletionTarget(systemConfiguration)
        private val walBuffers = WalBuffers(sharedBuffers)
        private val defaultStatisticsTarget = DefaultStatisticsTarget(systemConfiguration)
        private val randomPageCost = RandomPageCost(systemConfiguration)
        private val effectiveIoConcurrency = EffectiveIoConcurrency(systemConfiguration)
        private val workMem = WorkMem(systemConfiguration, sharedBuffers, maxConnections)
        private val minWalSize = MinWalSize(systemConfiguration)
        private val maxWalSize = MaxWalSize(systemConfiguration)
        private val maxWorkerProcesses = MaxWorkerProcesses(systemConfiguration)
        private val maxParallelWorkersPerGather = MaxParallelWorkersPerGather(systemConfiguration)
        private val maxParallelWorkers = MaxParallelWorkers(systemConfiguration)

        override fun getConfiguration(): String = """${maxConnections.toPgConfigurationLine()}
            ${sharedBuffers.toPgConfigurationLine()}
            ${effectiveCacheSize.toPgConfigurationLine()}
            ${maintenanceWorkMem.toPgConfigurationLine()}
            ${checkPointCompletionTarget.toPgConfigurationLine()}
            ${walBuffers.toPgConfigurationLine()}
            ${defaultStatisticsTarget.toPgConfigurationLine()}
            ${randomPageCost.toPgConfigurationLine()}
            ${effectiveIoConcurrency.toPgConfigurationLine()}
            ${workMem.toPgConfigurationLine()}
            ${minWalSize.toPgConfigurationLine()}
            ${maxWalSize.toPgConfigurationLine()}
            ${maxWorkerProcesses.toPgConfigurationLine()}
            ${maxParallelWorkersPerGather.toPgConfigurationLine()}
            ${maxParallelWorkers.toPgConfigurationLine()}
        """.trimIndent()
    }

    class PostgresConfiguration12(systemConfiguration: SystemConfiguration) : PostgresConfiguration() {
        private val maxConnections = MaxConnections(systemConfiguration)
        private val sharedBuffers = SharedBuffers(systemConfiguration)
        private val effectiveCacheSize = EffectiveCacheSize(systemConfiguration)
        private val maintenanceWorkMem = MaintenanceWorkMem(systemConfiguration)
        private val checkPointCompletionTarget = CheckPointCompletionTarget(systemConfiguration)
        private val walBuffers = WalBuffers(sharedBuffers)
        private val defaultStatisticsTarget = DefaultStatisticsTarget(systemConfiguration)
        private val randomPageCost = RandomPageCost(systemConfiguration)
        private val effectiveIoConcurrency = EffectiveIoConcurrency(systemConfiguration)
        private val workMem = WorkMem(systemConfiguration, sharedBuffers, maxConnections)
        private val minWalSize = MinWalSize(systemConfiguration)
        private val maxWalSize = MaxWalSize(systemConfiguration)
        private val maxWorkerProcesses = MaxWorkerProcesses(systemConfiguration)
        private val maxParallelWorkersPerGather = MaxParallelWorkersPerGather(systemConfiguration)
        private val maxParallelWorkers = MaxParallelWorkers(systemConfiguration)

        override fun getConfiguration(): String = """${maxConnections.toPgConfigurationLine()}
            ${sharedBuffers.toPgConfigurationLine()}
            ${effectiveCacheSize.toPgConfigurationLine()}
            ${maintenanceWorkMem.toPgConfigurationLine()}
            ${checkPointCompletionTarget.toPgConfigurationLine()}
            ${walBuffers.toPgConfigurationLine()}
            ${defaultStatisticsTarget.toPgConfigurationLine()}
            ${randomPageCost.toPgConfigurationLine()}
            ${effectiveIoConcurrency.toPgConfigurationLine()}
            ${workMem.toPgConfigurationLine()}
            ${minWalSize.toPgConfigurationLine()}
            ${maxWalSize.toPgConfigurationLine()}
            ${maxWorkerProcesses.toPgConfigurationLine()}
            ${maxParallelWorkersPerGather.toPgConfigurationLine()}
            ${maxParallelWorkers.toPgConfigurationLine()}
        """.trimIndent()
    }
}