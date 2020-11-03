package de.sainth.pgtune.config

import io.kotlintest.data.forall
import io.kotlintest.shouldBe
import io.kotlintest.specs.DescribeSpec
import io.kotlintest.tables.row
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import io.mockk.every
import io.mockk.mockk

@MicronautTest
class WorkMemTest() : DescribeSpec() {

    init {
//      workMemValue =  (ram - shared_buffers) / (3 * max_connections) / max_parallel_workers_per_gather
        describe("WorkMemTest") {
            it("minimum of workMem is 64KB") {
                val systemConfiguration = mockk<SystemConfiguration>()
                val sharedBuffers = mockk<SharedBuffers>()
                val maxConnections = mockk<MaxConnections>()
                every { systemConfiguration.ram } returns Memory(200, SizeUnit.MB)
                every { sharedBuffers.sharedBuffers } returns Memory(50, SizeUnit.MB)
                every { maxConnections.maxConnections } returns 1000
                DbApplication.values().forEach {
                    dbApplication ->
                    every { systemConfiguration.dbApplication } returns dbApplication
                    WorkMem(systemConfiguration, sharedBuffers, maxConnections).workMem shouldBe Memory(64, SizeUnit.KB)
                }
            }
            it("workMem formulas work as expected without maxParallelWorkersPerGather") {
                val ramValue = Memory(2, SizeUnit.GB)
                val sharedBuffersValue = ramValue.divide(4)
                val maxConnectionsValue = 200
                val workMemValue = ramValue.minus(sharedBuffersValue).divide(3 * maxConnectionsValue)
                val systemConfiguration = mockk<SystemConfiguration>()
                val sharedBuffers = mockk<SharedBuffers>()
                val maxConnections = mockk<MaxConnections>()
                every { systemConfiguration.ram } returns ramValue
                every { sharedBuffers.sharedBuffers } returns sharedBuffersValue
                every { maxConnections.maxConnections } returns maxConnectionsValue
                forall(
                        row(DbApplication.WEB, workMemValue),
                        row(DbApplication.OLTP, workMemValue),
                        row(DbApplication.DATA_WAREHOUSE, workMemValue.divide(2)),
                        row(DbApplication.DESKTOP, workMemValue.divide(6)),
                        row(DbApplication.MIXED, workMemValue.divide(2))
                ) { dbApplication, expected ->
                    every { systemConfiguration.dbApplication } returns dbApplication
                    WorkMem(systemConfiguration, sharedBuffers, maxConnections).workMem shouldBe expected
                }
            }
            it("workMem formulas work as expected with maxParallelWorkersPerGather") {
                val ramValue = Memory(2, SizeUnit.GB)
                val sharedBuffersValue = ramValue.divide(4)
                val maxConnectionsValue = 100
                val maxParallelWorkersPerGatherValue = 8
                val workMemValue = ramValue.minus(sharedBuffersValue).divide(3 * maxConnectionsValue).divide(maxParallelWorkersPerGatherValue)
                val systemConfiguration = mockk<SystemConfiguration>()
                val sharedBuffers = mockk<SharedBuffers>()
                val maxConnections = mockk<MaxConnections>()
                val maxParallelWorkersPerGather = mockk<MaxParallelWorkersPerGather>()
                every { systemConfiguration.ram } returns ramValue
                every { sharedBuffers.sharedBuffers } returns sharedBuffersValue
                every { maxConnections.maxConnections } returns maxConnectionsValue
                every { maxParallelWorkersPerGather.maxParallelWorkersPerGather } returns maxParallelWorkersPerGatherValue
                forall(
                        row(DbApplication.WEB, workMemValue),
                        row(DbApplication.OLTP, workMemValue),
                        row(DbApplication.DATA_WAREHOUSE, workMemValue.divide(2)),
                        row(DbApplication.DESKTOP, workMemValue.divide(6)),
                        row(DbApplication.MIXED, workMemValue.divide(2))
                ) { dbApplication, expected ->
                    every { systemConfiguration.dbApplication } returns dbApplication
                    WorkMem(systemConfiguration, sharedBuffers, maxConnections, maxParallelWorkersPerGather).workMem shouldBe expected
                }
            }
        }
    }

}