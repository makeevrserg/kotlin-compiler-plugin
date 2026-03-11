// WITH_STDLIB

var called = false

class MyClass {
    fun setup() {
        called = true
    }
}

fun box(): String {
    called = false
    MyClass()

    // Without @CallOnInit, the function should NOT be called automatically
    if (called) return "Fail: setup() should not have been called without @CallOnInit"

    return "OK"
}
