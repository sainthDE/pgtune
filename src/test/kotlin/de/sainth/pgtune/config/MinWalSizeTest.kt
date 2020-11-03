package de.sainth.pgtune.config

import io.kotlintest.data.forall
import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow
import io.kotlintest.specs.DescribeSpec
import io.kotlintest.tables.row
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import io.mockk.every
import io.mockk.mockk

@MicronautTest
class MinWalSizeTest() : DescribeSpec() {

    init {
        describe("MinWalSizeTest") {
            it("when dbVersion < V9_5 then IllegalArgumentException is thrown") {
                val systemConfiguration = mockk<SystemConfiguration>(relaxed = true)
                every { systemConfiguration.dbVersion } returns PostgresVersion.V9_4
                shouldThrow<IllegalArgumentException> {
                    MinWalSize(systemConfiguration)
                }
            }
        }
        describe("when dbVersion >= V9_5 the correct static values are present") {
            val systemConfiguration = mockk<SystemConfiguration>(relaxed = true)
            val dbVersions = listOf(PostgresVersion.V9_5, PostgresVersion.V9_6, PostgresVersion.V10, PostgresVersion.V11, PostgresVersion.V12, PostgresVersion.V13)
            dbVersions.forEach { dbVersion ->
                every { systemConfiguration.dbVersion } returns dbVersion
                forall(
                        row(DbApplication.WEB, Memory(1, SizeUnit.GB)),
                        row(DbApplication.OLTP, Memory(2, SizeUnit.GB)),
                        row(DbApplication.DATA_WAREHOUSE, Memory(4, SizeUnit.GB)),
                        row(DbApplication.DESKTOP, Memory(100, SizeUnit.MB)),
                        row(DbApplication.MIXED, Memory(1, SizeUnit.GB))

                ) { app, mem ->
                    every { systemConfiguration.dbApplication } returns app
                    MinWalSize(systemConfiguration).minWalSize shouldBe mem
                }

            }
        }


    }

}