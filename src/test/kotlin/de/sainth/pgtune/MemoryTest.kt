package de.sainth.pgtune

import io.kotlintest.data.forall
import io.kotlintest.shouldBe
import io.kotlintest.specs.DescribeSpec
import io.kotlintest.tables.row
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.annotation.MicronautTest

@MicronautTest
class MemoryTest(@Client("/") private val client: RxHttpClient) : DescribeSpec() {
    init {
        describe("Memory division test") {
            forall(
                    row(Pair(Memory(1, SizeUnit.TB), 4), Memory(256, SizeUnit.GB)),
                    row(Pair(Memory(1, SizeUnit.KB), 2), Memory(512, SizeUnit.B)),
                    row(Pair(Memory(16, SizeUnit.GB), 16), Memory(1, SizeUnit.GB)),
                    row(Pair(Memory(1, SizeUnit.TB), 1024), Memory(1, SizeUnit.GB)),
                    row(Pair(Memory(16, SizeUnit.MB), 4), Memory(4, SizeUnit.MB))
            ) { input, expected ->
                input.first.divide(input.second) shouldBe expected
            }
        }

        describe("Memory greater than") {
            it("test that 1TB > 1GB") {
                Memory(1, SizeUnit.TB).greaterThan(Memory(1, SizeUnit.GB)) shouldBe true
            }

            it("test that NOT 1B > 1GB") {
                Memory(1, SizeUnit.B).greaterThan(Memory(1, SizeUnit.GB)) shouldBe false
            }

            it("test that 1TB + 1B > 1TB") {
                Memory(SizeUnit.TB.bytes + 1, SizeUnit.B).greaterThan(Memory(1, SizeUnit.TB)) shouldBe true
            }

            it("test that NOT 1GB > 1024MB") {
                Memory(1, SizeUnit.GB).greaterThan(Memory(1024, SizeUnit.MB)) shouldBe false
            }
        }
    }
}