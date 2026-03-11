package ru.astrainteractive.klibs.compilerplugin.callinit

/**
 * Marks a member function to be automatically called during class initialization (inside the `init {}` block).
 *
 * Functions annotated with `@CallOnInit` must not be called explicitly — the compiler plugin
 * will report an error if they are. They are invoked automatically when the class is instantiated,
 * after any user-written `init {}` block code.
 *
 * Example:
 * ```
 * class MyService {
 *     @CallOnInit
 *     fun setup() {
 *         println("Automatically called on init!")
 *     }
 * }
 * ```
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.BINARY)
@MustBeDocumented
public annotation class CallOnInit