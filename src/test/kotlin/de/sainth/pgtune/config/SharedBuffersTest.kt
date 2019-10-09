package de.sainth.pgtune.config

import io.kotlintest.data.forall
import io.kotlintest.shouldBe
import io.kotlintest.specs.DescribeSpec
import io.kotlintest.tables.row
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.annotation.MicronautTest
import io.mockk.every
import io.mockk.mockk

@MicronautTest
class SharedBuffersTest(@Client("/") private val client: RxHttpClient) : DescribeSpec() {

    init {
        describe("SharedBuffersTest") {
            it("when dbApplication == DESKTOP then sharedBuffers = ram / 16") {
                val systemConfiguration = mockk<SystemConfiguration>(relaxed = true)
                every { systemConfiguration.dbApplication } returns DbApplication.DESKTOP
                every { systemConfiguration.ram } returns Memory(16, SizeUnit.GB)
                SharedBuffers(systemConfiguration).sharedBuffers shouldBe Memory(1, SizeUnit.GB)
            }
            it("when dbApplication != DESKTOP then sharedBuffers = ram / 4") {
                val systemConfiguration = mockk<SystemConfiguration>(relaxed = true)
                val ram = Memory(16, SizeUnit.GB)
                val result = ram.divide(4)
                every { systemConfiguration.ram } returns ram
                forall(
                        row(DbApplication.WEB, result),
                        row(DbApplication.OLTP, result),
                        row(DbApplication.DATA_WAREHOUSE, result),
                        row(DbApplication.MIXED, result)
                ) { app, mem: Memory ->
                    every { systemConfiguration.dbApplication } returns app
                    SharedBuffers(systemConfiguration).sharedBuffers shouldBe mem
                }
            }
            it("when osType == Windows then 512 MB is maximum of sharedBuffers") {
                val systemConfiguration = mockk<SystemConfiguration>(relaxed = true)
                every { systemConfiguration.dbApplication } returns DbApplication.DESKTOP
                every { systemConfiguration.osType } returns OperatingSystem.Windows
                every { systemConfiguration.ram } returns Memory(16, SizeUnit.GB)
                SharedBuffers(systemConfiguration).sharedBuffers shouldBe Memory(512, SizeUnit.MB)
            }
            it("when dbApplication == WEB, osType == Windows and ram == 1 GB then sharedBuffers should be 256MB ") {
                val systemConfiguration = mockk<SystemConfiguration>(relaxed = true)
                every { systemConfiguration.dbApplication } returns DbApplication.WEB
                every { systemConfiguration.osType } returns OperatingSystem.Windows
                every { systemConfiguration.ram } returns Memory(1, SizeUnit.GB)
                SharedBuffers(systemConfiguration).sharedBuffers shouldBe Memory(256, SizeUnit.MB)
            }
        }
    }

}