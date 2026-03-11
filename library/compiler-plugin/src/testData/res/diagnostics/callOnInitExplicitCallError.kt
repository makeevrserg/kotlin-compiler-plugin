// RUN_PIPELINE_TILL: FRONTEND

import ru.astrainteractive.klibs.compilerplugin.callinit.CallOnInit

class MyClass {
    @CallOnInit
    fun setup() {}

    @CallOnInit
    fun initialize() {}

    fun regularFun() {}

    fun caller() {
        // Explicit calls to @CallOnInit functions should be an error
        <!EXPLICIT_CALL_TO_CALL_ON_INIT_FUNCTION!>setup<!>()
        <!EXPLICIT_CALL_TO_CALL_ON_INIT_FUNCTION!>initialize<!>()

        // Regular functions are fine
        regularFun()
    }

    init {
        // Even in init blocks, explicit calls should be an error
        <!EXPLICIT_CALL_TO_CALL_ON_INIT_FUNCTION!>setup<!>()
    }
}

fun topLevel(obj: MyClass) {
    // Calling from outside the class should also be an error
    obj.<!EXPLICIT_CALL_TO_CALL_ON_INIT_FUNCTION!>setup<!>()
    obj.regularFun()
}
