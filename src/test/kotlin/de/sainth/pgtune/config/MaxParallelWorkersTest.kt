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
class MaxParallelWorkersTest : DescribeSpec() {

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
                forAll(
                        row(PostgresVersion.V10, 3),
                        row(PostgresVersion.V11, 4),
                        row(PostgresVersion.V12, 5),
                        row(PostgresVersion.V13, 5)
                ) { dbVersion, cpus ->
                    every { systemConfiguration.dbVersion } returns dbVersion
                    every { systemConfiguration.cores } returns cpus.toShort()
                    MaxParallelWorkers(systemConfiguration).maxParallelWorkers shouldBe cpus
                }
            }
            it("when dbVersion >= V10 and cpus == null then maxParallelWorkers = 8") {
                val systemConfiguration = mockk<SystemConfiguration>()
                forAll(
                        row(PostgresVersion.V10, 8),
                        row(PostgresVersion.V11, 8),
                        row(PostgresVersion.V12, 8),
                        row(PostgresVersion.V13, 8)
                ) { dbVersion, cpus ->
                    every { systemConfiguration.dbVersion } returns dbVersion
                    every { systemConfiguration.cores } returns null
                    MaxParallelWorkers(systemConfiguration).maxParallelWorkers shouldBe cpus
                }
            }
        }

    }

}