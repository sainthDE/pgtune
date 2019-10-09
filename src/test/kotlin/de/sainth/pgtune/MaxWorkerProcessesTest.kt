package de.sainth.pgtune

import io.kotlintest.data.forall
import io.kotlintest.specs.DescribeSpec
import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow
import io.kotlintest.tables.row
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.annotation.MicronautTest
import io.mockk.every
import io.mockk.mockk
import java.lang.IllegalArgumentException

@MicronautTest
class MaxWorkerProcessesTest(@Client("/") private val client: RxHttpClient) : DescribeSpec() {

    init {
        describe("MaxWorkerProcessesTest") {
            it("when dbVersion < V9_5 then IllegalArgumentException is thrown") {
                val systemConfiguration = mockk<SystemConfiguration>(relaxed = true)
                every { systemConfiguration.dbVersion } returns PostgresVersion.V9_4
                shouldThrow<IllegalArgumentException> {
                    MaxWorkerProcesses(systemConfiguration)
                }
            }
            it("when dbVersion >= V9_5 and cpus != null then maxWorkerProcesses = cpus") {
                val systemConfiguration = mockk<SystemConfiguration>()
                forall(
                        row(PostgresVersion.V9_5, 1),
                        row(PostgresVersion.V9_6, 2),
                        row(PostgresVersion.V10, 3),
                        row(PostgresVersion.V11, 4),
                        row(PostgresVersion.V12, 5)
                ) { dbVersion, cpus ->
                    every { systemConfiguration.dbVersion } returns dbVersion
                    every { systemConfiguration.cpus } returns cpus.toShort()
                    MaxWorkerProcesses(systemConfiguration).maxWorkerProcesses shouldBe cpus
                }
            }
            it("when dbVersion >= V9_5 and cpus == null then maxWorkerProcesses = 8") {
                val systemConfiguration = mockk<SystemConfiguration>()
                forall(
                        row(PostgresVersion.V9_5, 8),
                        row(PostgresVersion.V9_6, 8),
                        row(PostgresVersion.V10, 8),
                        row(PostgresVersion.V11, 8),
                        row(PostgresVersion.V12, 8)
                ) { dbVersion, cpus ->
                    every { systemConfiguration.dbVersion } returns dbVersion
                    every { systemConfiguration.cpus } returns null
                    MaxWorkerProcesses(systemConfiguration).maxWorkerProcesses shouldBe cpus
                }
            }
        }
    }

}