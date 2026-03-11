plugins {
    kotlin("jvm") version "2.3.0"
    id("ru.astrainteractive.klibs.kotlin.compiler.plugin.oninit")
    application
}

application {
    mainClass.set("MainKt")
}
