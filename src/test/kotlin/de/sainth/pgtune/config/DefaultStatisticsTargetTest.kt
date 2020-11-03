package de.sainth.pgtune.config


import io.kotest.core.spec.style.DescribeSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe
import io.micronaut.test.extensions.kotest.annotation.MicronautTest
import io.mockk.every
import io.mockk.mockk

@MicronautTest
class DefaultStatisticsTargetTest : DescribeSpec() {

    init {
        describe("DefaultStatisticsTargetTest") {
            val systemConfiguration = mockk<SystemConfiguration>(relaxed = true)
            forAll(
                    row(DbApplication.WEB, 100),
                    row(DbApplication.DATA_WAREHOUSE, 500),
                    row(DbApplication.OLTP, 100),
                    row(DbApplication.DESKTOP, 100),
                    row(DbApplication.MIXED, 100)
            ) { app, expected ->
                every { systemConfiguration.dbApplication } returns app
                DefaultStatisticsTarget(systemConfiguration).defaultStatisticsTarget shouldBe expected
            }
        }
    }

}
