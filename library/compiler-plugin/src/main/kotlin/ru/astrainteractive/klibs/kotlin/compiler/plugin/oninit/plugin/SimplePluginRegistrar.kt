package ru.astrainteractive.klibs.kotlin.compiler.plugin.oninit.plugin

import ru.astrainteractive.klibs.kotlin.compiler.plugin.oninit.fir.CallOnInitCheckersExtension
import org.jetbrains.kotlin.fir.extensions.FirExtensionRegistrar

class SimplePluginRegistrar : FirExtensionRegistrar() {
    override fun ExtensionRegistrarContext.configurePlugin() {
        +::CallOnInitCheckersExtension
    }
}
