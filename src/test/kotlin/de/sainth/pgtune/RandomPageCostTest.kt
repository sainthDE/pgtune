package de.sainth.pgtune

import io.kotlintest.data.forall
import io.kotlintest.matchers.doubles.plusOrMinus
import io.kotlintest.specs.DescribeSpec
import io.kotlintest.shouldBe
import io.kotlintest.tables.row
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.annotation.MicronautTest
import io.mockk.every
import io.mockk.mockk

@MicronautTest
class RandomPageCostTest(@Client("/") private val client: RxHttpClient) : DescribeSpec() {

    init {
        describe("RandomPageCostTest") {
            val systemConfiguration = mockk<SystemConfiguration>(relaxed = true)
            forall(
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
