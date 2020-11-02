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
class MaintenanceWorkMemTest() : DescribeSpec() {

    init {
        describe("MaintenanceWorkMemTest") {
            it("when dbApplication == DATA_WAREHOUSE then maintenanceWorkMem = ram / 8") {
                val systemConfiguration = mockk<SystemConfiguration>(relaxed = true)
                every { systemConfiguration.dbApplication } returns DbApplication.DATA_WAREHOUSE
                every { systemConfiguration.ram } returns Memory(8, SizeUnit.GB)
                MaintenanceWorkMem(systemConfiguration).maintenanceWorkMem shouldBe Memory(1, SizeUnit.GB)
            }
            it("when dbApplication != DATA_WAREHOUSE then maintenanceWorkMem = ram / 16") {
                val systemConfiguration = mockk<SystemConfiguration>(relaxed = true)
                val ram = Memory(8, SizeUnit.GB)
                val result = ram.divide(16)
                every { systemConfiguration.ram } returns ram
                forall(row(DbApplication.WEB, result),
                        row(DbApplication.OLTP, result),
                        row(DbApplication.DESKTOP, result),
                        row(DbApplication.MIXED, result)
                ) { app, mem: Memory ->
                    every { systemConfiguration.dbApplication } returns app
                    MaintenanceWorkMem(systemConfiguration).maintenanceWorkMem shouldBe mem
                }
            }
            it("when osType != Windows then 2GB is maximum maintenanceWorkMem") {
                val systemConfiguration = mockk<SystemConfiguration>(relaxed = true)
                val ram = Memory(2, SizeUnit.GB)
                every { systemConfiguration.ram } returns Memory(512, SizeUnit.GB)
                forall(
                        row(OperatingSystem.Linux, ram),
                        row(OperatingSystem.MacOsX, ram)
                ) { os, mem ->
                    every { systemConfiguration.osType } returns os
                    MaintenanceWorkMem(systemConfiguration).maintenanceWorkMem shouldBe mem
                }
            }
            it("when osType == Windows then 2GB-1MB is maximum maintenanceWorkMem") {
                val systemConfiguration = mockk<SystemConfiguration>(relaxed = true)
                every { systemConfiguration.osType } returns OperatingSystem.Windows
                every { systemConfiguration.ram } returns Memory(512, SizeUnit.GB)
                MaintenanceWorkMem(systemConfiguration).maintenanceWorkMem shouldBe Memory(2, SizeUnit.GB).minus(Memory(1, SizeUnit.MB))
            }
        }

    }

}