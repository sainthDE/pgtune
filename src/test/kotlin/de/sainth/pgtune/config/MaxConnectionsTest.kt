package de.sainth.pgtune.config


import io.kotest.core.spec.style.DescribeSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe
import io.micronaut.test.extensions.kotest.annotation.MicronautTest
import io.mockk.every
import io.mockk.mockk

@MicronautTest
class MaxConnectionsTest : DescribeSpec() {

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
            forAll(
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