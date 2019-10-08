package de.sainth.pgtune

import io.kotlintest.data.forall
import io.kotlintest.specs.DescribeSpec
import io.kotlintest.shouldBe
import io.kotlintest.tables.row
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.annotation.MicronautTest
import io.mockk.every
import io.mockk.mockk

@MicronautTest
class EffectiveCacheSizeTest(@Client("/") private val client: RxHttpClient) : DescribeSpec() {

    init {
        describe("EffectiveCacheSizeTest") {
            it("when dbApplication == DESKTOP then effectiveCacheSize = 25% of ram") {
                val systemConfiguration = mockk<SystemConfiguration>(relaxed = true)
                every { systemConfiguration.dbApplication } returns DbApplication.DESKTOP
                every { systemConfiguration.ram } returns Memory(16, SizeUnit.GB)
                (EffectiveCacheSize(systemConfiguration).effectiveCacheSize == Memory(4, SizeUnit.GB)) shouldBe true
            }
            it("when dbApplication != DESKTOP then effectiveCacheSize = 75% of ram") {
                val systemConfiguration = mockk<SystemConfiguration>(relaxed = true)
                val ram = Memory(16, SizeUnit.GB)
                val result = ram.multiply(3).divide(4)
                every { systemConfiguration.ram } returns ram
                forall(
                        row(DbApplication.WEB, result),
                        row(DbApplication.OLTP, result),
                        row(DbApplication.DATA_WAREHOUSE, result),
                        row(DbApplication.MIXED, result)
                ) { app, mem: Memory ->
                    every { systemConfiguration.dbApplication } returns app
                    (EffectiveCacheSize(systemConfiguration).effectiveCacheSize == mem) shouldBe true
                }
            }
        }
    }

}