package at.ahammer.bluetooth.checker

import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import com.nhaarman.expect.expect
import com.nhaarman.expect.fail
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Test

class HealthIT : AbstractIT() {

    @Test
    fun testITHealth() = runBlocking {
        println("test integration health")
        val (_, response, result) = "http://localhost:8180/health".httpGet().responseString() // blocking
        when (result) {
            is Result.Failure -> {
                println("fail: $result")
                fail(response.httpStatusCode.toString())
            }
            is Result.Success -> {
                println("success: $result")
                expect(response.httpStatusCode).toBe(200)
            }
        }
    }
}