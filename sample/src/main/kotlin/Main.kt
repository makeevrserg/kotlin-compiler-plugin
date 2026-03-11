import ru.astrainteractive.klibs.compilerplugin.callinit.CallOnInit

class SomeClass {
    init {
        println("Original init block")
    }

    @CallOnInit
    fun myStartupFun() {
        println("myStartupFun called via @CallOnInit!")
    }

    @CallOnInit
    fun anotherStartupFun() {
        println("anotherStartupFun called via @CallOnInit!")
    }

    fun regularFun() {
        println("This should NOT be called automatically")
    }
}

class SimpleClass {
    @CallOnInit
    fun setup() {
        println("SimpleClass.setup called via @CallOnInit (no explicit init block)!")
    }
}

fun main() {
    println("--- Creating SomeClass ---")
    val obj = SomeClass()

    println()
    println("--- Creating SimpleClass ---")
    val simple = SimpleClass()

    println()
    println("--- Calling regularFun manually ---")
    obj.regularFun()
}
