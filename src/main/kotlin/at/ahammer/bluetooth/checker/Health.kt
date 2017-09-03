package at.ahammer.bluetooth.checker

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.KodeinAware
import com.github.salomonbrys.kodein.instance
import com.google.gson.Gson
import mu.KotlinLogging
import org.jetbrains.ktor.application.ApplicationCall
import org.jetbrains.ktor.http.ContentType
import org.jetbrains.ktor.response.respondText

class Health(override val kodein: Kodein) : KodeinAware {

    private val logger = KotlinLogging.logger {}
    private val foo: Foo = instance()
    private val jsonResponse: JsonRespond = instance()

    suspend fun get(call: ApplicationCall) {
        logger.info("test foo: ${foo.bar()}")
        logger.info("test jsonResponse: ${jsonResponse.justToTest("test it")}")
        jsonResponse.respond(call, HealthResult("UP"))
    }

}

open class JsonRespond {

    suspend fun respond(call: ApplicationCall, dataClass: JsonDataClass) {
        call.respondText(dataClass.toJsonString(), ContentType.Application.Json)
    }

    fun justToTest(string: String?): String {
        return "input: $string"
    }
}

data class HealthResult(val status: String) : JsonDataClass {
    override fun toJsonString(): String {
        return Gson().toJson(this)
    }
}