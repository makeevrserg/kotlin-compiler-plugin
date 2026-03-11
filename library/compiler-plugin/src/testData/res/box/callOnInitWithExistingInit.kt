// WITH_STDLIB

import ru.astrainteractive.klibs.compilerplugin.callinit.CallOnInit

var callLog = mutableListOf<String>()

class MyClass {
    init {
        callLog.add("init-block")
    }

    @CallOnInit
    fun setup() {
        callLog.add("setup")
    }
}

fun box(): String {
    callLog.clear()
    MyClass()

    if (callLog.size != 2) return "Fail: expected 2 calls, got ${callLog.size}: $callLog"
    // User-written init block should execute FIRST
    if (callLog[0] != "init-block") return "Fail: expected 'init-block' first, got '${callLog[0]}'"
    // @CallOnInit function should execute AFTER the init block
    if (callLog[1] != "setup") return "Fail: expected 'setup' second, got '${callLog[1]}'"

    return "OK"
}
