package ru.astrainteractive.klibs.kotlin.compiler.plugin.oninit.fir

import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactory0
import org.jetbrains.kotlin.diagnostics.Severity
import org.jetbrains.kotlin.diagnostics.SourceElementPositioningStrategies

object CallOnInitErrors {
    val EXPLICIT_CALL_TO_CALL_ON_INIT_FUNCTION = KtDiagnosticFactory0(
        name = "EXPLICIT_CALL_TO_CALL_ON_INIT_FUNCTION",
        severity = Severity.ERROR,
        defaultPositioningStrategy = SourceElementPositioningStrategies.REFERENCE_BY_QUALIFIED,
        psiType = Any::class,
        rendererFactory = CallOnInitDiagnosticRendererFactory,
    )
}
