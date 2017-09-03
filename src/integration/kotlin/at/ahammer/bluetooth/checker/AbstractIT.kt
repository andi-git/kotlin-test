package at.ahammer.bluetooth.checker

import org.junit.After
import org.junit.Before

abstract class AbstractIT {

    @Before
    fun before() {
        if (!ITTestSuite.isSuiteActive()) {
            RunServer.startServer()
        }
    }

    @After
    fun after() {
        if (!ITTestSuite.isSuiteActive()) {
            RunServer.stopServer()
        }
    }
}