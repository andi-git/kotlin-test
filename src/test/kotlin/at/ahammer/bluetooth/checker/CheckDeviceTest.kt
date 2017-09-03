package at.ahammer.bluetooth.checker

import com.github.salomonbrys.kodein.Kodein
import com.nhaarman.expect.expect
import com.nhaarman.mockito_kotlin.anyOrNull
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import org.junit.Test

class CheckDeviceTest {

    @Test
    fun testJsonResponse() {
        val jsonRespond = mock<JsonRespond> {
            on { justToTest(anyOrNull()) } doReturn "???"
        }
        expect(jsonRespond.justToTest("x")).toBe("???")
        expect(JsonRespond().justToTest("hi")).toBe("input: hi")
    }

    @Test
    fun testIsCar() {
        expect(CheckDevice(mock<Kodein>()).isCar("", 0).isCar).toBe(false)
        expect(CheckDevice(mock<Kodein>()).isCar("opel", 0).isCar).toBe(true)
        expect(CheckDevice(mock<Kodein>()).isCar("opel", 1032).isCar).toBe(true)
        expect(CheckDevice(mock<Kodein>()).isCar("", 1032).isCar).toBe(true)
        expect(CheckDevice(mock<Kodein>()).isCar("Opel Zafira", 0).isCar).toBe(true)
    }
}