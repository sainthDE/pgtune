package de.sainth.pgtune

import de.sainth.pgtune.config.*
import io.kotlintest.specs.DescribeSpec
import io.kotlintest.shouldBe
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.annotation.MicronautTest

@MicronautTest
class ConfigurationControllerTest(@Client("/") private val client: RxHttpClient) : DescribeSpec() {

    init {
        describe("ConfigurationControllerTest") {
            it("when /configuration with a valid systemConfiguration is called then the valid configuration will be returned")
            val response = client.toBlocking()
                    .exchange(HttpRequest.POST("/configuration",
                            SystemConfiguration(PostgresVersion.V9_4,
                                    OperatingSystem.Linux,
                                    DbApplication.WEB,
                                    Memory(4, SizeUnit.GB),
                                    4.toShort(),
                                    400,
                                    DataStorage.SSD)), String::class.java)

            response.status shouldBe HttpStatus.OK
            response.body() shouldBe "# PostgreSQL-Version: 9.4\n" +
                    "# OS: Linux\n" +
                    "# Application: WEB\n" +
                    "# Memory: 4GB\n" +
                    "# CPUs: 4\n" +
                    "# Connections: 400\n" +
                    "# Storage: SSD\n" +
                    "\n" +
                    "max_connections = 400\n" +
                    "shared_buffers = 1GB\n" +
                    "effective_cache_size = 3GB\n" +
                    "maintenance_work_mem = 256MB\n" +
                    "checkpoint_completion_target = 0.7\n" +
                    "wal_buffers = 16MB\n" +
                    "default_statistics_target = 100\n" +
                    "random_page_cost = 1.1\n" +
                    "effective_io_concurrency = 200\n" +
                    "work_mem = 2621KB\n" +
                    "checkpoint_segments = 32\n"
        }

    }

}