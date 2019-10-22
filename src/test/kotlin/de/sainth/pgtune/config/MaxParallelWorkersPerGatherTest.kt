package de.sainth.pgtune.config

import io.kotlintest.data.forall
import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow
import io.kotlintest.specs.DescribeSpec
import io.kotlintest.tables.row
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.annotation.MicronautTest
import io.mockk.every
import io.mockk.mockk

@MicronautTest
class MaxParallelWorkersPerGatherTest(@Client("/") private val client: RxHttpClient) : DescribeSpec() {

    init {
        describe("MaxParallelWorkersPerGatherTest") {
            it("when dbVersion < V9_6 then IllegalArgumentException is thrown") {
                val systemConfiguration = mockk<SystemConfiguration>(relaxed = true)
                listOf(PostgresVersion.V9_4, PostgresVersion.V9_5)
                        .forEach { dbVersion ->
                            every { systemConfiguration.dbVersion } returns dbVersion
                            shouldThrow<IllegalArgumentException> {
                                MaxParallelWorkersPerGather(systemConfiguration)
                            }
                        }
            }
            it("when dbVersion >= V9_6 and cpus != null then maxParallelWorkersPerGather = cpus / 2") {
                val systemConfiguration = mockk<SystemConfiguration>()
                forall(
                        row(PostgresVersion.V9_6, 4),
                        row(PostgresVersion.V10, 6),
                        row(PostgresVersion.V11, 8),
                        row(PostgresVersion.V12, 10)
                ) { dbVersion, cpus ->
                    every { systemConfiguration.dbVersion } returns dbVersion
                    every { systemConfiguration.cores } returns cpus.toShort()
                    MaxParallelWorkersPerGather(systemConfiguration).maxParallelWorkersPerGather shouldBe (cpus / 2)
                }
            }
            it("when dbVersion >= V9_5 and cpus == null then maxParallelWorkersPerGather = 2") {
                val systemConfiguration = mockk<SystemConfiguration>()
                forall(
                        row(PostgresVersion.V9_6, 2),
                        row(PostgresVersion.V10, 2),
                        row(PostgresVersion.V11, 2),
                        row(PostgresVersion.V12, 2)
                ) { dbVersion, cpus ->
                    every { systemConfiguration.dbVersion } returns dbVersion
                    every { systemConfiguration.cores } returns null
                    MaxParallelWorkersPerGather(systemConfiguration).maxParallelWorkersPerGather shouldBe cpus
                }
            }
        }
    }

}