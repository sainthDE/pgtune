package de.sainth.pgtune.config

abstract class PgConfigurationParameter(val name: String) {

    abstract fun getParameterString(): String

    fun toPgConfigurationLine(): String {
        return "$name = ${getParameterString()}"
    }
}