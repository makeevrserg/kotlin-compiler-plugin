plugins {
    alias(libs.plugins.kotlin.jvm)
    id("ru.astrainteractive.klibs.kotlin.compiler.plugin.oninit")
    application
}

application {
    mainClass.set("MainKt")
}
