package de.sainth.pgtune.config


import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe
import io.micronaut.test.extensions.kotest.annotation.MicronautTest
import io.mockk.every
import io.mockk.mockk

@MicronautTest
class MaxWorkerProcessesTest : DescribeSpec() {

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
                forAll(
                        row(PostgresVersion.V9_5, 1),
                        row(PostgresVersion.V9_6, 2),
                        row(PostgresVersion.V10, 3),
                        row(PostgresVersion.V11, 4),
                        row(PostgresVersion.V12, 5),
                        row(PostgresVersion.V13, 5)
                ) { dbVersion, cpus ->
                    every { systemConfiguration.dbVersion } returns dbVersion
                    every { systemConfiguration.cores } returns cpus.toShort()
                    MaxWorkerProcesses(systemConfiguration).maxWorkerProcesses shouldBe cpus
                }
            }
            it("when dbVersion >= V9_5 and cpus == null then maxWorkerProcesses = 8") {
                val systemConfiguration = mockk<SystemConfiguration>()
                forAll(
                        row(PostgresVersion.V9_5, 8),
                        row(PostgresVersion.V9_6, 8),
                        row(PostgresVersion.V10, 8),
                        row(PostgresVersion.V11, 8),
                        row(PostgresVersion.V12, 8),
                        row(PostgresVersion.V13, 8)
                ) { dbVersion, cpus ->
                    every { systemConfiguration.dbVersion } returns dbVersion
                    every { systemConfiguration.cores } returns null
                    MaxWorkerProcesses(systemConfiguration).maxWorkerProcesses shouldBe cpus
                }
            }
        }
    }

}