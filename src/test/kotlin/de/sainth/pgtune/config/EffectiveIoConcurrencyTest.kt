package de.sainth.pgtune.config

import io.kotlintest.data.forall
import io.kotlintest.shouldBe
import io.kotlintest.specs.DescribeSpec
import io.kotlintest.tables.row
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.annotation.MicronautTest
import io.mockk.every
import io.mockk.mockk

@MicronautTest
class EffectiveIoConcurrencyTest() : DescribeSpec() {

    init {
        describe("EffectiveIoConcurrencyTest") {
            val systemConfiguration = mockk<SystemConfiguration>(relaxed = true)
            forall(
                    row(DataStorage.HDD, 2),
                    row(DataStorage.SSD, 200),
                    row(DataStorage.SAN, 300)
            ) { storage, expected ->
                every { systemConfiguration.dataStorage } returns storage
                EffectiveIoConcurrency(systemConfiguration).effectiveIoConcurrency shouldBe expected
            }
        }
    }

}