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

        override fun getConfiguration(): String = "${maxConnections.toPgConfigurationLine()}\n" +
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

        override fun getConfiguration(): String = "${maxConnections.toPgConfigurationLine()}\n" +
                "${sharedBuffers.toPgConfigurationLine()}\n" +
                "${effectiveCacheSize.toPgConfigurationLine()}\n" +
                "${maintenanceWorkMem.toPgConfigurationLine()}\n" +
                "${checkPointCompletionTarget.toPgConfigurationLine()}\n" +
                "${walBuffers.toPgConfigurationLine()}\n" +
                "${defaultStatisticsTarget.toPgConfigurationLine()}\n" +
                "${randomPageCost.toPgConfigurationLine()}\n" +
                "${effectiveIoConcurrency.toPgConfigurationLine()}\n" +
                "${workMem.toPgConfigurationLine()}\n" +
                "${minWalSize.toPgConfigurationLine()}\n" +
                "${maxWalSize.toPgConfigurationLine()}\n" +
                "${maxWorkerProcesses.toPgConfigurationLine()}\n"

    }

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

        override fun getConfiguration(): String = "${maxConnections.toPgConfigurationLine()}\n" +
                "${sharedBuffers.toPgConfigurationLine()}\n" +
                "${effectiveCacheSize.toPgConfigurationLine()}\n" +
                "${maintenanceWorkMem.toPgConfigurationLine()}\n" +
                "${checkPointCompletionTarget.toPgConfigurationLine()}\n" +
                "${walBuffers.toPgConfigurationLine()}\n" +
                "${defaultStatisticsTarget.toPgConfigurationLine()}\n" +
                "${randomPageCost.toPgConfigurationLine()}\n" +
                "${effectiveIoConcurrency.toPgConfigurationLine()}\n" +
                "${workMem.toPgConfigurationLine()}\n" +
                "${minWalSize.toPgConfigurationLine()}\n" +
                "${maxWalSize.toPgConfigurationLine()}\n" +
                "${maxWorkerProcesses.toPgConfigurationLine()}\n" +
                "${maxParallelWorkersPerGather.toPgConfigurationLine()}\n"
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

        override fun getConfiguration(): String = "${maxConnections.toPgConfigurationLine()}\n" +
                "${sharedBuffers.toPgConfigurationLine()}\n" +
                "${effectiveCacheSize.toPgConfigurationLine()}\n" +
                "${maintenanceWorkMem.toPgConfigurationLine()}\n" +
                "${checkPointCompletionTarget.toPgConfigurationLine()}\n" +
                "${walBuffers.toPgConfigurationLine()}\n" +
                "${defaultStatisticsTarget.toPgConfigurationLine()}\n" +
                "${randomPageCost.toPgConfigurationLine()}\n" +
                "${effectiveIoConcurrency.toPgConfigurationLine()}\n" +
                "${workMem.toPgConfigurationLine()}\n" +
                "${minWalSize.toPgConfigurationLine()}\n" +
                "${maxWalSize.toPgConfigurationLine()}\n" +
                "${maxWorkerProcesses.toPgConfigurationLine()}\n" +
                "${maxParallelWorkersPerGather.toPgConfigurationLine()}\n"
    }

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

        override fun getConfiguration(): String = "${maxConnections.toPgConfigurationLine()}\n" +
                "${sharedBuffers.toPgConfigurationLine()}\n" +
                "${effectiveCacheSize.toPgConfigurationLine()}\n" +
                "${maintenanceWorkMem.toPgConfigurationLine()}\n" +
                "${checkPointCompletionTarget.toPgConfigurationLine()}\n" +
                "${walBuffers.toPgConfigurationLine()}\n" +
                "${defaultStatisticsTarget.toPgConfigurationLine()}\n" +
                "${randomPageCost.toPgConfigurationLine()}\n" +
                "${effectiveIoConcurrency.toPgConfigurationLine()}\n" +
                "${workMem.toPgConfigurationLine()}\n" +
                "${minWalSize.toPgConfigurationLine()}\n" +
                "${maxWalSize.toPgConfigurationLine()}\n" +
                "${maxWorkerProcesses.toPgConfigurationLine()}\n" +
                "${maxParallelWorkersPerGather.toPgConfigurationLine()}\n" +
                "${maxParallelWorkers.toPgConfigurationLine()}\n"
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

        override fun getConfiguration(): String = "${maxConnections.toPgConfigurationLine()}\n" +
                "${sharedBuffers.toPgConfigurationLine()}\n" +
                "${effectiveCacheSize.toPgConfigurationLine()}\n" +
                "${maintenanceWorkMem.toPgConfigurationLine()}\n" +
                "${checkPointCompletionTarget.toPgConfigurationLine()}\n" +
                "${walBuffers.toPgConfigurationLine()}\n" +
                "${defaultStatisticsTarget.toPgConfigurationLine()}\n" +
                "${randomPageCost.toPgConfigurationLine()}\n" +
                "${effectiveIoConcurrency.toPgConfigurationLine()}\n" +
                "${workMem.toPgConfigurationLine()}\n" +
                "${minWalSize.toPgConfigurationLine()}\n" +
                "${maxWalSize.toPgConfigurationLine()}\n" +
                "${maxWorkerProcesses.toPgConfigurationLine()}\n" +
                "${maxParallelWorkersPerGather.toPgConfigurationLine()}\n" +
                "${maxParallelWorkers.toPgConfigurationLine()}\n"
    }
}