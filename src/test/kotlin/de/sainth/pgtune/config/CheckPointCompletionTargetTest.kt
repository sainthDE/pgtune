package de.sainth.pgtune.config

import io.kotlintest.data.forall
import io.kotlintest.matchers.doubles.plusOrMinus
import io.kotlintest.shouldBe
import io.kotlintest.specs.DescribeSpec
import io.kotlintest.tables.row
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.annotation.MicronautTest
import io.mockk.every
import io.mockk.mockk

@MicronautTest
class CheckPointCompletionTargetTest() : DescribeSpec() {

    init {
        describe("CheckPointCompletionTargetTest") {
            val systemConfiguration = mockk<SystemConfiguration>(relaxed = true)
            forall(
                    row(DbApplication.WEB, 0.7 plusOrMinus 1e-10),
                    row(DbApplication.OLTP, 0.9 plusOrMinus 1e-10),
                    row(DbApplication.DATA_WAREHOUSE, 0.9 plusOrMinus 1e-10),
                    row(DbApplication.DESKTOP, 0.5 plusOrMinus 1e-10),
                    row(DbApplication.MIXED, 0.9 plusOrMinus 1e-10)
            ) { app, completionTarget ->
                every { systemConfiguration.dbApplication } returns app
                CheckPointCompletionTarget(systemConfiguration).checkpointCompletionTarget shouldBe completionTarget
            }
        }
    }

}