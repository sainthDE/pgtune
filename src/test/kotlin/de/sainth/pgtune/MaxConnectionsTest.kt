package de.sainth.pgtune

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
            val systemConfiguration = mockk<SystemConfiguration>()
            every { systemConfiguration.connections } returns null
            forall(
                    row(DbApplication.WEB, DbApplication.WEB.maxConnections),
                    row(DbApplication.OLTP, DbApplication.OLTP.maxConnections),
                    row(DbApplication.DATA_WAREHOUSE, DbApplication.DATA_WAREHOUSE.maxConnections),
                    row(DbApplication.DESKTOP, DbApplication.DESKTOP.maxConnections),
                    row(DbApplication.MIXED, DbApplication.MIXED.maxConnections)
            ) { dbApp, maxConnections ->
                every { systemConfiguration.dbApplication } returns dbApp
                MaxConnections(systemConfiguration).maxConnections shouldBe maxConnections
            }
        }

    }

}