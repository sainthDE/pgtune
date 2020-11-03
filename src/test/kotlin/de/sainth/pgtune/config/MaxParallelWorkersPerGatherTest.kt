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
class MaxParallelWorkersPerGatherTest : DescribeSpec() {

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
                forAll(
                        row(PostgresVersion.V9_6, 4),
                        row(PostgresVersion.V10, 6),
                        row(PostgresVersion.V11, 8),
                        row(PostgresVersion.V12, 10),
                        row(PostgresVersion.V13, 10)
                ) { dbVersion, cpus ->
                    every { systemConfiguration.dbVersion } returns dbVersion
                    every { systemConfiguration.cores } returns cpus.toShort()
                    MaxParallelWorkersPerGather(systemConfiguration).maxParallelWorkersPerGather shouldBe (cpus / 2)
                }
            }
            it("when dbVersion >= V9_5 and cpus == null then maxParallelWorkersPerGather = 2") {
                val systemConfiguration = mockk<SystemConfiguration>()
                forAll(
                        row(PostgresVersion.V9_6, 2),
                        row(PostgresVersion.V10, 2),
                        row(PostgresVersion.V11, 2),
                        row(PostgresVersion.V12, 2),
                        row(PostgresVersion.V13, 2)
                ) { dbVersion, cpus ->
                    every { systemConfiguration.dbVersion } returns dbVersion
                    every { systemConfiguration.cores } returns null
                    MaxParallelWorkersPerGather(systemConfiguration).maxParallelWorkersPerGather shouldBe cpus
                }
            }
        }
    }

}