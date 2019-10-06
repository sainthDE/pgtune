package de.sainth.pgtune

abstract class PgConfigurationParameter(val name: String) {

    abstract fun getParameterString(): String

    fun toPgConfigurationLine(): String {
        return "$name = ${getParameterString()}"
    }
}