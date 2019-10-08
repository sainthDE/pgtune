package de.sainth.pgtune

import io.kotlintest.matchers.doubles.plusOrMinus
import io.kotlintest.specs.DescribeSpec
import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.annotation.MicronautTest
import io.mockk.every
import io.mockk.mockk

@MicronautTest
class CheckPointCompletionTargetTest(@Client("/") private val client: RxHttpClient) : DescribeSpec() {

    init {
        describe("CheckPointCompletionTargetTest") {
            val systemConfiguration = mockk<SystemConfiguration>(relaxed = true)
            it("when dbApplication == WEB then checkpointCompletionTarget is 0.7") {
                every { systemConfiguration.dbApplication } returns DbApplication.WEB
                CheckPointCompletionTarget(systemConfiguration).checkpointCompletionTarget shouldBe (0.7 plusOrMinus 1e-10)
            }
            it("when dbApplication == OLTP then checkpointCompletionTarget is 0.9") {
                every { systemConfiguration.dbApplication } returns DbApplication.OLTP
                CheckPointCompletionTarget(systemConfiguration).checkpointCompletionTarget shouldBe (0.9 plusOrMinus 1e-10)
            }
            it("when dbApplication == DATA_WAREHOUSE then checkpointCompletionTarget is 0.9") {
                every { systemConfiguration.dbApplication } returns DbApplication.DATA_WAREHOUSE
                CheckPointCompletionTarget(systemConfiguration).checkpointCompletionTarget shouldBe (0.9 plusOrMinus 1e-10)
            }
            it("when dbApplication == DESKTOP then checkpointCompletionTarget is 0.5") {
                every { systemConfiguration.dbApplication } returns DbApplication.DESKTOP
                CheckPointCompletionTarget(systemConfiguration).checkpointCompletionTarget shouldBe (0.5 plusOrMinus 1e-10)
            }
            it("when dbApplication == MIXED then checkpointCompletionTarget is 0.9") {
                every { systemConfiguration.dbApplication } returns DbApplication.MIXED
                CheckPointCompletionTarget(systemConfiguration).checkpointCompletionTarget shouldBe (0.9 plusOrMinus 1e-10)
            }
        }
    }

}