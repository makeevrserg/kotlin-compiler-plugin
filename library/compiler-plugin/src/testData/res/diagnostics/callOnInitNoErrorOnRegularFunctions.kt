// RUN_PIPELINE_TILL: FRONTEND

import ru.astrainteractive.klibs.compilerplugin.callinit.CallOnInit

class NoAnnotations {
    fun setup() {}
    fun init() {}

    fun caller() {
        // No @CallOnInit annotation — no errors
        setup()
        init()
    }
}

class MixedAnnotations {
    @CallOnInit
    fun annotatedFun() {}

    fun notAnnotatedFun() {}

    fun caller() {
        <!EXPLICIT_CALL_TO_CALL_ON_INIT_FUNCTION!>annotatedFun<!>()
        notAnnotatedFun() // no error
    }
}
