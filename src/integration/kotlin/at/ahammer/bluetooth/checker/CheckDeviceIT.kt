package at.ahammer.bluetooth.checker

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import com.google.gson.Gson
import com.nhaarman.expect.expect
import com.nhaarman.expect.fail
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Test

class CheckDeviceIT : AbstractIT() {

    @Test
    fun testITCheckDevice() = runBlocking {
        checkDevice("ford", 1032, true)
        checkDevice("opel zafira", 0, true)
        checkDevice("xyz", 1032, true)
        checkDevice("xyz", 0, false)
    }

    private fun checkDevice(deviceName: String, deviceClass: Int, expectedIsCar: Boolean) {
        val (_, response, result) = "http://localhost:8180/?deviceClass=$deviceClass&deviceName=$deviceName".httpGet().responseObject(ServerResult.Deserializer())
        when (result) {
            is Result.Failure -> { fail(response.httpStatusCode.toString()) }
            is Result.Success -> {
                expect(response.httpStatusCode).toBe(200)
                expect(result.value.isCar).toBe(expectedIsCar)
            }
        }
    }
}

data class ServerResult(val isCar: Boolean){

    class Deserializer : ResponseDeserializable<ServerResult> {
        override fun deserialize(content: String): ServerResult = Gson().fromJson(content, ServerResult::class.java)
    }
}
