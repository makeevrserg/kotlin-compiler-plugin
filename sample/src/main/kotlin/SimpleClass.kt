import ru.astrainteractive.klibs.compilerplugin.callinit.CallOnInit

class SimpleClass {
    @CallOnInit
    fun setup() {
        println(".SimpleClass.setup called via @CallOnInit (no explicit init block)!")
    }
}
