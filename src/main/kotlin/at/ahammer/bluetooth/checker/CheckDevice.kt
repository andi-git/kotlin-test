package at.ahammer.bluetooth.checker

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.KodeinAware
import com.github.salomonbrys.kodein.instance
import com.google.gson.Gson
import mu.KotlinLogging
import org.jetbrains.ktor.application.ApplicationCall

class CheckDevice(override val kodein: Kodein) : KodeinAware {

    private val logger = KotlinLogging.logger {}
    private val jsonRepsonse: JsonRespond = instance()
    private val manufacturers = hashSetOf("opel", "audi", "ford")
    private val deviceAudioVideoHandsfree = 1032

    suspend fun get(call: ApplicationCall) {
        logger.info("device class: ${call.getDeviceClass().or(0)}")
        logger.info("device name: ${call.getDeviceName()}")
        jsonRepsonse.respond(call, isCar(call.getDeviceName(), call.getDeviceClass()))
    }

    fun isCar(deviceName: String, deviceClass: Int): CheckDeviceResult {
        return when {
            deviceClass == deviceAudioVideoHandsfree -> CheckDeviceResult(true)
            manufacturers.any{deviceName.toLowerCase().contains(it)} -> CheckDeviceResult(true)
            else -> CheckDeviceResult(false)
        }
    }

}

fun ApplicationCall.getDeviceClass(): Int {
    return this.request.queryParameters["deviceClass"].orEmpty().toInt()
}

fun ApplicationCall.getDeviceName(): String {
    return this.request.queryParameters["deviceName"].orEmpty()
}

data class CheckDeviceResult(val isCar: Boolean) : JsonDataClass {
    override fun toJsonString(): String {
        return Gson().toJson(this)
    }
}

interface JsonDataClass {
    fun toJsonString(): String
}