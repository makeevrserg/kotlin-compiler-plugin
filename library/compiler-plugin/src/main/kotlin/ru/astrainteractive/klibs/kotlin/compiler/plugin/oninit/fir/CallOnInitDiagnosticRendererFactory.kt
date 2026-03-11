package ru.astrainteractive.klibs.kotlin.compiler.plugin.oninit.fir

import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactoryToRendererMap
import org.jetbrains.kotlin.diagnostics.rendering.BaseDiagnosticRendererFactory

object CallOnInitDiagnosticRendererFactory : BaseDiagnosticRendererFactory() {
    override val MAP by KtDiagnosticFactoryToRendererMap("CallOnInit") { map ->
        map.put(
            factory = CallOnInitErrors.EXPLICIT_CALL_TO_CALL_ON_INIT_FUNCTION,
            message = "Functions annotated with @CallOnInit should not be called explicitly." +
                    " They are automatically called during class initialization."
        )
    }
}
