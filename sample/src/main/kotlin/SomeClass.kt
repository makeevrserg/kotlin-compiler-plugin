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