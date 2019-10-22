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
class MaxParallelWorkersTest(@Client("/") private val client: RxHttpClient) : DescribeSpec() {

    init {
        describe("MaxParallelWorkersTest") {
            it("when dbVersion < V10 then IllegalArgumentException is thrown") {
                val systemConfiguration = mockk<SystemConfiguration>(relaxed = true)
                listOf(PostgresVersion.V9_4, PostgresVersion.V9_5, PostgresVersion.V9_6)
                        .forEach { dbVersion ->
                            every { systemConfiguration.dbVersion } returns dbVersion
                            shouldThrow<IllegalArgumentException> {
                                MaxParallelWorkers(systemConfiguration)
                            }
                        }

            }
            it("when dbVersion >= V10 and cpus != null then maxParallelWorkers = cpus") {
                val systemConfiguration = mockk<SystemConfiguration>()
                forall(
                        row(PostgresVersion.V10, 3),
                        row(PostgresVersion.V11, 4),
                        row(PostgresVersion.V12, 5)
                ) { dbVersion, cpus ->
                    every { systemConfiguration.dbVersion } returns dbVersion
                    every { systemConfiguration.cores } returns cpus.toShort()
                    MaxParallelWorkers(systemConfiguration).maxParallelWorkers shouldBe cpus
                }
            }
            it("when dbVersion >= V10 and cpus == null then maxParallelWorkers = 8") {
                val systemConfiguration = mockk<SystemConfiguration>()
                forall(
                        row(PostgresVersion.V10, 8),
                        row(PostgresVersion.V11, 8),
                        row(PostgresVersion.V12, 8)
                ) { dbVersion, cpus ->
                    every { systemConfiguration.dbVersion } returns dbVersion
                    every { systemConfiguration.cores } returns null
                    MaxParallelWorkers(systemConfiguration).maxParallelWorkers shouldBe cpus
                }
            }
        }

    }

}