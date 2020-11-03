package de.sainth.pgtune.config


import io.kotest.core.spec.style.DescribeSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.doubles.plusOrMinus
import io.kotest.matchers.shouldBe
import io.micronaut.test.extensions.kotest.annotation.MicronautTest
import io.mockk.every
import io.mockk.mockk

@MicronautTest
class CheckPointCompletionTargetTest : DescribeSpec() {

    init {
        describe("CheckPointCompletionTargetTest") {
            val systemConfiguration = mockk<SystemConfiguration>(relaxed = true)
            forAll(
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