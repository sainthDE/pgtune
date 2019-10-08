package de.sainth.pgtune

import io.kotlintest.shouldBe
import io.kotlintest.specs.DescribeSpec
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.annotation.MicronautTest
import io.mockk.every
import io.mockk.mockk

@MicronautTest
class MaxConnectionsTest(@Client("/") private val client: RxHttpClient) : DescribeSpec() {

    init {
        describe("MaxConnectionsTest") {
            it("systemConfiguration.connections should be used by default") {
                val systemConfiguration = mockk<SystemConfiguration>()
                every { systemConfiguration.connections } returns 500
                MaxConnections(systemConfiguration).maxConnections shouldBe 500
            }
        }
        describe("if systemConfiguration.connections is null systemConfiguration.dbApplication.maxConnections will be used") {
            val webConfiguration = mockk<SystemConfiguration>()
            val oltpConfiguration = mockk<SystemConfiguration>()
            val dataWarehouseConfiguration = mockk<SystemConfiguration>()
            val desktopConfiguration = mockk<SystemConfiguration>()
            val mixedConfiguration = mockk<SystemConfiguration>()
            every { webConfiguration.connections } returns null
            every { oltpConfiguration.connections } returns null
            every { dataWarehouseConfiguration.connections } returns null
            every { desktopConfiguration.connections } returns null
            every { mixedConfiguration.connections } returns null
            every { webConfiguration.dbApplication } returns DbApplication.WEB
            every { oltpConfiguration.dbApplication } returns DbApplication.OLTP
            every { dataWarehouseConfiguration.dbApplication } returns DbApplication.DATA_WAREHOUSE
            every { desktopConfiguration.dbApplication } returns DbApplication.DESKTOP
            every { mixedConfiguration.dbApplication } returns DbApplication.MIXED
            mapOf(
                    webConfiguration to DbApplication.WEB,
                    oltpConfiguration to DbApplication.OLTP,
                    dataWarehouseConfiguration to DbApplication.DATA_WAREHOUSE,
                    desktopConfiguration to DbApplication.DESKTOP,
                    mixedConfiguration to DbApplication.MIXED
            ).forEach { (input, expected) ->
                it("${input.dbApplication.maxConnections} == ${expected.maxConnections}") {
                    MaxConnections(input).maxConnections shouldBe expected.maxConnections
                }
            }
        }

    }

}