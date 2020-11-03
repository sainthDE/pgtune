package de.sainth.pgtune.config

import io.kotlintest.matchers.types.shouldBeTypeOf
import io.kotlintest.specs.DescribeSpec
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import io.mockk.every
import io.mockk.mockk

@MicronautTest
class PostgresConfigurationTest() : DescribeSpec() {

    init {
        describe("the resulting class depends on given dbVersion") {
            val systemConfiguration = mockk<SystemConfiguration>(relaxed = true)
            every { systemConfiguration.dbVersion } returns PostgresVersion.V9_4
            PostgresConfiguration(systemConfiguration).shouldBeTypeOf<PostgresConfiguration.PostgresConfiguration9_4>()
            every { systemConfiguration.dbVersion } returns PostgresVersion.V9_5
            PostgresConfiguration(systemConfiguration).shouldBeTypeOf<PostgresConfiguration.PostgresConfiguration9_5>()
            every { systemConfiguration.dbVersion } returns PostgresVersion.V9_6
            PostgresConfiguration(systemConfiguration).shouldBeTypeOf<PostgresConfiguration.PostgresConfiguration9_6>()
            every { systemConfiguration.dbVersion } returns PostgresVersion.V10
            PostgresConfiguration(systemConfiguration).shouldBeTypeOf<PostgresConfiguration.PostgresConfiguration10>()
            every { systemConfiguration.dbVersion } returns PostgresVersion.V11
            PostgresConfiguration(systemConfiguration).shouldBeTypeOf<PostgresConfiguration.PostgresConfiguration11>()
            every { systemConfiguration.dbVersion } returns PostgresVersion.V12
            PostgresConfiguration(systemConfiguration).shouldBeTypeOf<PostgresConfiguration.PostgresConfiguration12>()
            every { systemConfiguration.dbVersion } returns PostgresVersion.V13
            PostgresConfiguration(systemConfiguration).shouldBeTypeOf<PostgresConfiguration.PostgresConfiguration13>()
        }
    }

}