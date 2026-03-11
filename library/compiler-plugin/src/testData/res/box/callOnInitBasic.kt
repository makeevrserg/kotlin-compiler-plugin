// WITH_STDLIB

import ru.astrainteractive.klibs.compilerplugin.callinit.CallOnInit

var callLog = mutableListOf<String>()

class MyClass {
    @CallOnInit
    fun setup() {
        callLog.add("setup")
    }

    @CallOnInit
    fun initialize() {
        callLog.add("initialize")
    }
}

fun box(): String {
    callLog.clear()
    MyClass()

    if (callLog.size != 2) return "Fail: expected 2 calls, got ${callLog.size}"
    if (callLog[0] != "setup") return "Fail: expected 'setup' first, got '${callLog[0]}'"
    if (callLog[1] != "initialize") return "Fail: expected 'initialize' second, got '${callLog[1]}'"

    return "OK"
}
