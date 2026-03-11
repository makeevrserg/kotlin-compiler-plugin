package ru.astrainteractive.klibs.kotlin.compiler.plugin.oninit.ir

import org.jetbrains.kotlin.backend.common.extensions.IrGenerationExtension
import org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment

class SimpleIrGenerationExtension : IrGenerationExtension {
    override fun generate(
        moduleFragment: IrModuleFragment,
        pluginContext: IrPluginContext
    ) {
        // Apply CallOnInit transformer to inject init-block calls
        moduleFragment.transform(
            transformer = CallOnInitIrTransformer(pluginContext),
            data = null
        )
    }
}
