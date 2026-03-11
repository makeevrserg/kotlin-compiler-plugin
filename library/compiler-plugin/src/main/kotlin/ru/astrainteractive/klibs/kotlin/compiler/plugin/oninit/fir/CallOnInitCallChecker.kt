package ru.astrainteractive.klibs.kotlin.compiler.plugin.oninit.fir

import org.jetbrains.kotlin.diagnostics.DiagnosticReporter
import org.jetbrains.kotlin.diagnostics.reportOn
import org.jetbrains.kotlin.fir.analysis.checkers.MppCheckerKind
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirFunctionCallChecker
import org.jetbrains.kotlin.fir.declarations.hasAnnotation
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall
import org.jetbrains.kotlin.fir.references.toResolvedFunctionSymbol
import org.jetbrains.kotlin.name.ClassId
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.name.Name

/**
 * FIR checker that reports a compile error when a function annotated with @CallOnInit
 * is called explicitly from user code.
 *
 * Since @CallOnInit functions are automatically injected into init blocks by the IR transformer,
 * explicit calls would result in duplicate invocations and are considered an error.
 */
object CallOnInitCallChecker : FirFunctionCallChecker(MppCheckerKind.Common) {

    private val CALL_ON_INIT_CLASS_ID = ClassId(
        packageFqName = FqName("ru.astrainteractive.klibs.compilerplugin.callinit"),
        topLevelName = Name.identifier("CallOnInit")
    )

    context(context: CheckerContext, reporter: DiagnosticReporter)
    override fun check(expression: FirFunctionCall) {
        val callee = expression.calleeReference.toResolvedFunctionSymbol() ?: return
        if (callee.hasAnnotation(CALL_ON_INIT_CLASS_ID, context.session)) {
            reporter.reportOn(
                source = expression.calleeReference.source,
                factory = CallOnInitErrors.EXPLICIT_CALL_TO_CALL_ON_INIT_FUNCTION,
            )
        }
    }
}
