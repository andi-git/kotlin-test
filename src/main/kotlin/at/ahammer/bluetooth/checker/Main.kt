package at.ahammer.bluetooth.checker

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.newInstance
import com.github.salomonbrys.kodein.provider

val kodein = Kodein {
    bind<Foo>() with provider { Foo() }
    bind<Health>() with provider { Health(this) }
    bind<CheckDevice>() with provider { CheckDevice(this) }
    bind<JsonRespond>() with provider { JsonRespond() }
}

fun main(args: Array<String>) {
    kodein.newInstance { EmbeddedServer(kodein) }.run()
}
