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
class RandomPageCostTest : DescribeSpec() {

    init {
        describe("RandomPageCostTest") {
            val systemConfiguration = mockk<SystemConfiguration>(relaxed = true)
            forAll(
                    row(DataStorage.HDD, 4.0),
                    row(DataStorage.SSD, 1.1),
                    row(DataStorage.SAN, 1.1)
            ) { storage, expected ->
                every { systemConfiguration.dataStorage } returns storage
                RandomPageCost(systemConfiguration).randomPageCost shouldBe (expected plusOrMinus 1E-10)
            }
        }
    }

}
