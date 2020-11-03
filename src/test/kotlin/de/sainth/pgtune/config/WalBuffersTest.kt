package de.sainth.pgtune.config


import io.kotest.core.spec.style.DescribeSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe
import io.micronaut.test.extensions.kotest.annotation.MicronautTest
import io.mockk.every
import io.mockk.mockk

@MicronautTest
class WalBuffersTest : DescribeSpec() {

    init {
        describe("WalBuffersTest") {
            forAll(
                    row(Memory(1, SizeUnit.MB), Memory(32, SizeUnit.KB)),
                    row(Memory(1, SizeUnit.TB), Memory(16, SizeUnit.MB)),
                    row(Memory(100, SizeUnit.MB), Memory(3, SizeUnit.MB))
            ) { sharedBuffer, walBuffer: Memory ->
                val sharedBuffers = mockk<SharedBuffers>()
                every { sharedBuffers.sharedBuffers } returns sharedBuffer
                WalBuffers(sharedBuffers).walBuffers shouldBe walBuffer
            }
        }

    }

}