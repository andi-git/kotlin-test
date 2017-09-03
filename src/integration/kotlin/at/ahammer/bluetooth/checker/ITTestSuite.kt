package at.ahammer.bluetooth.checker

import kotlinx.coroutines.experimental.runBlocking
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.runner.RunWith
import org.junit.runners.Suite
import org.junit.runners.Suite.SuiteClasses

@RunWith(Suite::class)
@SuiteClasses(HealthIT::class, CheckDeviceIT::class)
object ITTestSuite {

    private var suiteActive: Boolean = false

    @BeforeClass
    @JvmStatic
    fun startServer() = runBlocking {
        suiteActive = true
        RunServer.startServer()
    }

    @AfterClass
    @JvmStatic
    fun stopServer() = runBlocking {
        RunServer.stopServer()
        suiteActive = false
    }

    fun isSuiteActive(): Boolean {
        return suiteActive
    }
}