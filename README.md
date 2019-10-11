# pgtune

Pgtune - tuning PostgreSQL config by your hardware. Based upon [pgtune](https://github.com/le0pard/pgtune).  

Unlike [pgtune](https://github.com/le0pard/pgtune) this project provides an REST interface and so can be used for automated setups.

## Usage 

This project is deployed at [https://pgtune.sainth.de/api/](https://pgtune.sainth.de/api/) and the OpenAPI specificaction can be reached at [https://pgtune.sainth.de/api/swagger/pgtune-1.0.yml](https://pgtune.sainth.de/api/swagger/pgtune-1.0.yml).

To use pgtune send the input parameters as JSON object, for example:

```bash
curl -d '{"dbVersion": "V11", "osType": "Linux", "dbApplication": "WEB", "ram": { "memory" : 4, "unit" : "GB"}, "dataStorage": "SSD"}' -H "Content-Type: application/json" -X POST https://pgtune.sainth.de/api/configuration
```

The used formulas can be found in the [wiki](https://github.com/sainth-/pgtune/wiki/Formulas).

## Getting Started

1. Checkout
2. Build/Assemble
3. Start

```bash
git clone https://github.com/sainth-/pgtune.git
cd pgtune
./gradlew assemble
java -jar ./build/libs/pgtune-1.0-all.jar
```

pgtune runs typically on port 8080. To change this add the following lines to `src/main/resources/application.yml`:

```yaml
micronaut:
  server:
    port: 8081
```

Or to change this during runtime add these lines in an files e.g. `config.yml` and add these `config.yml` as start parameter: `java -Dmicronaut.config.files=config.yml -jar pgtune.jar`

### IDE Setup

The instructions for the IDE setup can be found in the [official micronaut documentation](https://docs.micronaut.io/latest/guide/index.html#ideSetup). 

## Built With

* [Micronaut](https://docs.micronaut.io/1.2.3/guide/index.html) - The central framework
* [Gradle](https://gradle.org) - Build and Dependency Management
* [Kotlin](https://kotlinlang.org) - The programming language

## Contributing

Since pgtune is currently part of a project work of my studies, no external contributions can be accepted at the moment. But that will change in the future. 

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments

* [pgtune](https://github.com/le0pard/pgtune) by Alexey Vasiliev
* [pgtune](https://github.com/gregs1104/pgtune) by Gregory Smith
