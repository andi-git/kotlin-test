package at.ahammer.bluetooth.checker

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.provider
import com.nhaarman.expect.expect
import com.nhaarman.mockito_kotlin.*
import kotlinx.coroutines.experimental.runBlocking
import org.assertj.core.api.KotlinAssertions.assertThat
import org.jetbrains.ktor.application.ApplicationCall
import org.jetbrains.ktor.request.ApplicationRequest
import org.jetbrains.ktor.util.ValuesMap
import org.junit.Test

class HealthTest {

    @Test
    fun testHealthResult() {
        assertThat(HealthResult("UP").toJsonString()).isEqualTo("{\"status\":\"UP\"}")
    }

    @Test
    fun testHealthCheck() = runBlocking<Unit> {
        // mock jsonRespond
        val jsonRespond = mock<JsonRespond> { on(it.justToTest(anyOrNull())).doReturn("mocked ;)") }
        val foo = mock<Foo> { on(it.bar()).doReturn("") }
        val kodein = Kodein {
            bind<Foo>() with provider { foo }
            bind<JsonRespond>() with provider { jsonRespond }
        }

        // mock content of static method ApplicationCall.getDeviceClass()
        val queryParameters = mock<ValuesMap> {
            on { get("deviceClass") } doReturn ("999")
        }
        val request = mock<ApplicationRequest> {
            on { it.queryParameters } doReturn (queryParameters);
        }
        val call = mock<ApplicationCall> {
            on { it.request } doReturn request;
        }

        Health(kodein).get(call)
        inOrder(foo).verify(foo, calls(1)).bar()
        inOrder(jsonRespond).verify(jsonRespond, calls(1)).justToTest(anyOrNull())
        inOrder(jsonRespond).verify(jsonRespond, calls(1)).respond(anyOrNull(), anyOrNull())
    }
}
