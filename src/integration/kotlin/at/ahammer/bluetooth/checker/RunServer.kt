package at.ahammer.bluetooth.checker

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking
import net.lingala.zip4j.core.ZipFile
import java.io.File
import java.util.concurrent.TimeUnit

object RunServer {

    private var pid: Int = -1

    fun startServer() = runBlocking {
        println("starting server...")
        val extractedDist = extractDist()
        val startCommand = createStartCommand(extractedDist)
        val job = launch(CommonPool) {
            val process: Process = Runtime.getRuntime().exec(startCommand)
            process.waitFor(3, TimeUnit.SECONDS)
            val f = process.javaClass.getDeclaredField("pid")
            f.isAccessible = true
            pid = f.getInt(process)
        }
        job.join()
        println("server started with pid: ${pid}")
    }

    fun stopServer() = runBlocking {
        println("stopping server...")
        Runtime.getRuntime().exec("kill -9 ${pid}")
        println("server stopped")
    }

    private fun extractDist(): File {
        val extractedDist = File("build/distributions").listFiles().first { it.isZip() }
        val zipFile = ZipFile(extractedDist)
        zipFile.extractAll("build/distributions")
        return extractedDist
    }

    private fun createStartCommand(extractedDist: File): String {
        val classpathLibs = File("build/distributions/${extractedDist.nameWithoutExtension()}/lib").listFiles().map { it.absoluteFile }.joinToString(":")
        val javaCommand = "java -Dserver.port=8180 -classpath $classpathLibs at.ahammer.bluetooth.checker.MainKt"
        println(javaCommand)
        return javaCommand
    }

    private fun File.isZip(): Boolean {
        return this.name.endsWith(".zip")
    }

    private fun File.nameWithoutExtension(): String {
        val lastIndexOfDot = this.name.lastIndexOf('.')
        return this.name.substring(0, lastIndexOfDot)
    }
}