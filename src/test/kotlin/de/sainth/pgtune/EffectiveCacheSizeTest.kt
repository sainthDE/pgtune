package de.sainth.pgtune

import io.kotlintest.specs.DescribeSpec
import io.kotlintest.shouldBe
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
                EffectiveCacheSize(systemConfiguration).effectiveCacheSize.equals(Memory(4, SizeUnit.GB)).shouldBe(true)
            }
            it("when dbApplication != DESKTOP then effectiveCacheSize = 75% of ram") {
                val systemConfiguration = mockk<SystemConfiguration>(relaxed = true)
                val applications = listOf(DbApplication.WEB, DbApplication.OLTP, DbApplication.DATA_WAREHOUSE, DbApplication.MIXED)
                every { systemConfiguration.dbApplication } returnsMany applications
                every { systemConfiguration.ram } returns Memory(16, SizeUnit.GB)
                applications.forEach {
                    EffectiveCacheSize(systemConfiguration).effectiveCacheSize.equals(Memory(12, SizeUnit.GB)).shouldBe(true)
                }
            }
        }
    }

}