package de.sainth.pgtune

import de.sainth.pgadjust.Memory
import de.sainth.pgadjust.SizeUnit
import io.kotlintest.matchers.string.shouldStartWith
import io.kotlintest.shouldBe
import io.kotlintest.specs.DescribeSpec
import io.kotlintest.specs.FunSpec
import io.kotlintest.specs.Test
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.annotation.MicronautTest

@MicronautTest
class MemoryTest(@Client("/") private val client: RxHttpClient) : DescribeSpec() {
    init {
        describe("Memory division test") {
            mapOf(
                    Pair(Memory(1, SizeUnit.TB), 4) to Memory(256, SizeUnit.GB),
                    Pair(Memory(1, SizeUnit.KB), 2) to Memory(512, SizeUnit.B),
                    Pair(Memory(16, SizeUnit.GB), 16) to Memory(1, SizeUnit.GB),
                    Pair(Memory(1, SizeUnit.TB), 1024) to Memory(1, SizeUnit.GB),
                    Pair(Memory(16, SizeUnit.MB), 4) to Memory(4, SizeUnit.MB)
            ).forEach { (input, expected) ->
                it("${input.first} / ${input.second}= $expected") {
                    input.first.divide(input.second).shouldBe(expected)
                }
            }
        }

        describe("Memory greater than") {
            it("test that 1TB > 1GB") {
                Memory(1, SizeUnit.TB).greaterThan(Memory(1, SizeUnit.GB)).shouldBe(true)
            }

            it("test that NOT 1B > 1GB") {
                Memory(1, SizeUnit.B).greaterThan(Memory(1, SizeUnit.GB)).shouldBe(false)
            }

            it("test that 1TB + 1B > 1TB") {
                Memory(SizeUnit.TB.bytes + 1, SizeUnit.B).greaterThan(Memory(1, SizeUnit.TB)).shouldBe(true)
            }

            it("test that NOT 1GB > 1024MB") {
                Memory(1, SizeUnit.GB).greaterThan(Memory(1024, SizeUnit.MB)).shouldBe(false)
            }
        }
    }
}