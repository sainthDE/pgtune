package de.sainth.pgtune

import io.kotlintest.specs.DescribeSpec
import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow
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
            every { systemConfiguration.dbVersion } returnsMany dbVersions
            dbVersions.forEach {
                it("when dbApplication == WEB then minWalSize is 2GB") {
                    every { systemConfiguration.dbApplication } returns DbApplication.WEB
                    MaxWalSize(systemConfiguration).maxWalSize.shouldBe(Memory(2, SizeUnit.GB))
                }
                it("when dbApplication == OLTP then minWalSize is 4GB") {
                    every { systemConfiguration.dbApplication } returns DbApplication.OLTP
                    MaxWalSize(systemConfiguration).maxWalSize.shouldBe(Memory(4, SizeUnit.GB))
                }
                it("when dbApplication == DATA_WAREHOUSE then minWalSize is 8GB") {
                    every { systemConfiguration.dbApplication } returns DbApplication.DATA_WAREHOUSE
                    MaxWalSize(systemConfiguration).maxWalSize.shouldBe(Memory(8, SizeUnit.GB))
                }
                it("when dbApplication == DESKTOP then minWalSize is 1GB") {
                    every { systemConfiguration.dbApplication } returns DbApplication.DESKTOP
                    MaxWalSize(systemConfiguration).maxWalSize.shouldBe(Memory(1, SizeUnit.GB))
                }
                it("when dbApplication == MIXED then minWalSize is 2GB") {
                    every { systemConfiguration.dbApplication } returns DbApplication.MIXED
                    MaxWalSize(systemConfiguration).maxWalSize.shouldBe(Memory(2, SizeUnit.GB))
                }
            }
        }


    }

}