package at.ahammer.bluetooth.checker

import com.natpryce.konfig.*
import com.natpryce.konfig.ConfigurationProperties.Companion.systemProperties

object server : PropertyGroup() {
    val port by intType
}

val config = systemProperties() overriding
        EnvironmentVariables() overriding
        ConfigurationProperties.fromResource("defaults.properties")