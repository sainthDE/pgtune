package de.sainth.pgtune

import io.kotlintest.specs.DescribeSpec
import io.kotlintest.shouldBe
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.annotation.MicronautTest
import io.mockk.every
import io.mockk.mockk

@MicronautTest
class MaintenanceWorkMemTest(@Client("/") private val client: RxHttpClient) : DescribeSpec() {

    init {
        describe("MaintenanceWorkMemTest") {
            it("when dbApplication == DATA_WAREHOUSE then maintenanceWorkMem = ram / 8") {
                val systemConfiguration = mockk<SystemConfiguration>(relaxed = true)
                every { systemConfiguration.dbApplication } returns DbApplication.DATA_WAREHOUSE
                every { systemConfiguration.ram } returns Memory(8, SizeUnit.GB)
                (MaintenanceWorkMem(systemConfiguration).maintenanceWorkMem == Memory(1, SizeUnit.GB)).shouldBe(true)
            }
            it("when dbApplication != DATA_WAREHOUSE then maintenanceWorkMem = ram / 16") {
                val systemConfiguration = mockk<SystemConfiguration>(relaxed = true)
                val applications = listOf(DbApplication.WEB, DbApplication.OLTP, DbApplication.DESKTOP, DbApplication.MIXED)
                every { systemConfiguration.dbApplication } returnsMany applications
                every { systemConfiguration.ram } returns Memory(8, SizeUnit.GB)
                applications.forEach {
                    (MaintenanceWorkMem(systemConfiguration).maintenanceWorkMem == Memory(512, SizeUnit.MB)).shouldBe(true)
                }
            }
            it("when osType != Windows then 2GB is maximum maintenanceWorkMem") {
                val systemConfiguration = mockk<SystemConfiguration>(relaxed = true)
                val operatingSystems = listOf(OperatingSystem.Linux, OperatingSystem.MacOsX)
                every { systemConfiguration.osType } returnsMany operatingSystems
                every { systemConfiguration.ram } returns Memory(512, SizeUnit.GB)
                operatingSystems.forEach {
                    (MaintenanceWorkMem(systemConfiguration).maintenanceWorkMem == Memory(2, SizeUnit.GB)).shouldBe(true)
                }
            }
        }

    }

}