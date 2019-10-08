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

@MicronautTest
class MaxWalSizeTest(@Client("/") private val client: RxHttpClient) : DescribeSpec() {

    init {
        describe("MaxWalSizeTest") {
            it("when dbVersion < V9_5 then IllegalArgumentException is thrown") {
                val systemConfiguration = mockk<SystemConfiguration>(relaxed = true)
                every { systemConfiguration.dbVersion } returns PostgresVersion.V9_4
                shouldThrow<IllegalArgumentException> {
                    MaxWalSize(systemConfiguration)
                }
            }
        }
        describe("when dbVersion >= V9_5 the correct static values are present") {
            val systemConfiguration = mockk<SystemConfiguration>(relaxed = true)
            val dbVersions = listOf(PostgresVersion.V9_5, PostgresVersion.V9_6, PostgresVersion.V10, PostgresVersion.V11)
            dbVersions.forEach { dbVersion ->
                every { systemConfiguration.dbVersion } returns dbVersion
                forall(
                        row(DbApplication.WEB, Memory(2, SizeUnit.GB)),
                        row(DbApplication.OLTP, Memory(4, SizeUnit.GB)),
                        row(DbApplication.DATA_WAREHOUSE, Memory(8, SizeUnit.GB)),
                        row(DbApplication.DESKTOP, Memory(1, SizeUnit.GB)),
                        row(DbApplication.MIXED, Memory(2, SizeUnit.GB))

                ) { app, mem ->
                    every { systemConfiguration.dbApplication } returns app
                    MaxWalSize(systemConfiguration).maxWalSize shouldBe mem
                }
            }
        }


    }

}