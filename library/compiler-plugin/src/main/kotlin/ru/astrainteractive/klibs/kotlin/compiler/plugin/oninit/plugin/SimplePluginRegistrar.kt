package ru.astrainteractive.klibs.kotlin.compiler.plugin.oninit.plugin

import org.jetbrains.kotlin.fir.extensions.FirExtensionRegistrar
import ru.astrainteractive.klibs.kotlin.compiler.plugin.oninit.fir.CallOnInitCheckersExtension

class SimplePluginRegistrar : FirExtensionRegistrar() {
    override fun ExtensionRegistrarContext.configurePlugin() {
        +::CallOnInitCheckersExtension
    }
}
