package ru.astrainteractive.klibs.kotlin.compiler.plugin.oninit.ir

import org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
import org.jetbrains.kotlin.ir.IrStatement
import org.jetbrains.kotlin.ir.UNDEFINED_OFFSET
import org.jetbrains.kotlin.ir.declarations.IrAnonymousInitializer
import org.jetbrains.kotlin.ir.declarations.IrClass
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin
import org.jetbrains.kotlin.ir.expressions.impl.IrCallImpl
import org.jetbrains.kotlin.ir.expressions.impl.IrGetValueImpl
import org.jetbrains.kotlin.ir.symbols.impl.IrAnonymousInitializerSymbolImpl
import org.jetbrains.kotlin.ir.util.functions
import org.jetbrains.kotlin.ir.util.hasAnnotation
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
import org.jetbrains.kotlin.name.FqName

/**
 * IR transformer that finds functions annotated with @CallOnInit in a class
 * and injects calls to those functions inside the class's init {} block.
 *
 * For example, given:
 * ```
 * class SomeClass {
 *     @CallOnInit
 *     fun myStartupFun() { println("Hello!") }
 * }
 * ```
 *
 * The transformer will produce the equivalent of:
 * ```
 * class SomeClass {
 *     init {
 *         myStartupFun()
 *     }
 *     @CallOnInit
 *     fun myStartupFun() { println("Hello!") }
 * }
 * ```
 */
class CallOnInitIrTransformer(
    private val pluginContext: IrPluginContext,
) : IrElementTransformerVoid() {

    companion object {
        private val CALL_ON_INIT_FQ_NAME = FqName("ru.astrainteractive.klibs.compilerplugin.callinit.CallOnInit")
    }

    override fun visitClass(declaration: IrClass): IrStatement {
        // Transform children first (handles nested classes)
        declaration.transformChildrenVoid()

        // Find all member functions annotated with @CallOnInit
        val callOnInitFunctions = declaration.functions.filter { function ->
            function.hasAnnotation(CALL_ON_INIT_FQ_NAME)
        }.toList()

        if (callOnInitFunctions.isEmpty()) return declaration

        // Find existing anonymous initializer or create a new one
        val anonymousInitializer = declaration.declarations
            .filterIsInstance<IrAnonymousInitializer>()
            .firstOrNull()

        val initBody = if (anonymousInitializer != null) {
            anonymousInitializer.body
        } else {
            val newInitializer = pluginContext.irFactory.createAnonymousInitializer(
                startOffset = UNDEFINED_OFFSET,
                endOffset = UNDEFINED_OFFSET,
                origin = IrDeclarationOrigin.DEFINED,
                symbol = IrAnonymousInitializerSymbolImpl(),
            )
            newInitializer.parent = declaration
            val body = pluginContext.irFactory.createBlockBody(UNDEFINED_OFFSET, UNDEFINED_OFFSET)
            newInitializer.body = body
            declaration.declarations.add(newInitializer)
            body
        }

        // Add calls to each @CallOnInit function at the end of the init block
        for (function in callOnInitFunctions) {
            val thisReceiver = declaration.thisReceiver ?: continue
            val call = IrCallImpl(
                startOffset = UNDEFINED_OFFSET,
                endOffset = UNDEFINED_OFFSET,
                type = pluginContext.irBuiltIns.unitType,
                symbol = function.symbol,
            )
            // Set the dispatch receiver to `this`
            call.dispatchReceiver = IrGetValueImpl(
                startOffset = UNDEFINED_OFFSET,
                endOffset = UNDEFINED_OFFSET,
                symbol = thisReceiver.symbol,
            )
            initBody.statements.add(call)
        }

        return declaration
    }
}
