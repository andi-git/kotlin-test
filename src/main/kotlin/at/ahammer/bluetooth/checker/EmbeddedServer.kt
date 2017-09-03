package at.ahammer.bluetooth.checker

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.KodeinAware
import com.github.salomonbrys.kodein.instance
import org.jetbrains.ktor.host.embeddedServer
import org.jetbrains.ktor.netty.Netty
import org.jetbrains.ktor.routing.get
import org.jetbrains.ktor.routing.routing

class EmbeddedServer(override val kodein: Kodein) : KodeinAware {

    val health: Health = instance()
    val checkDevice: CheckDevice = instance()

    fun run() {
        embeddedServer(Netty, config[server.port]) {
            routing {
                get("/health") {
                    health.get(call);
                }
                get("/") {
                    checkDevice.get(call);
                }
            }
        }.start(wait = true)
    }
}