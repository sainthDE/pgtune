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
class CheckPointSegmentsTest(@Client("/") private val client: RxHttpClient) : DescribeSpec() {

    init {
        describe("CheckPointSegmentsTest") {
            it("when dbVersion != V9_4 then IllegalArgumentException is thrown") {
                val systemConfiguration = mockk<SystemConfiguration>(relaxed = true)
                val dbVersions = listOf(PostgresVersion.V9_5, PostgresVersion.V9_6, PostgresVersion.V10, PostgresVersion.V11)
                every { systemConfiguration.dbVersion } returnsMany dbVersions
                dbVersions.forEach {
                    shouldThrow<IllegalArgumentException> {
                        CheckPointSegments(systemConfiguration)
                    }
                }
            }
        }
        describe("when dbVersion == V9_4 the correct static values are present") {
            val systemConfiguration = mockk<SystemConfiguration>(relaxed = true)
            every { systemConfiguration.dbVersion } returns PostgresVersion.V9_4
            it("when dbApplication == WEB then checkPointSegments are 32") {
                every { systemConfiguration.dbApplication } returns DbApplication.WEB
                CheckPointSegments(systemConfiguration).checkPointSegments shouldBe 32
            }
            it("when dbApplication == OLTP then checkPointSegments are 64") {
                every { systemConfiguration.dbApplication } returns DbApplication.OLTP
                CheckPointSegments(systemConfiguration).checkPointSegments shouldBe 64
            }
            it("when dbApplication == DATA_WAREHOUSE then checkPointSegments are 128") {
                every { systemConfiguration.dbApplication } returns DbApplication.DATA_WAREHOUSE
                CheckPointSegments(systemConfiguration).checkPointSegments shouldBe 128
            }
            it("when dbApplication == DESKTOP then checkPointSegments are 3") {
                every { systemConfiguration.dbApplication } returns DbApplication.DESKTOP
                CheckPointSegments(systemConfiguration).checkPointSegments shouldBe 3
            }
            it("when dbApplication == MIXED then checkPointSegments are 32") {
                every { systemConfiguration.dbApplication } returns DbApplication.MIXED
                CheckPointSegments(systemConfiguration).checkPointSegments shouldBe 32
            }

        }
    }

}