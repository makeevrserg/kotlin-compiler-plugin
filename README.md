## What is it?

A Kotlin Compiler Plugin that automatically calls annotated member functions during class initialization.

Plugin provides a `@CallOnInit` annotation. Any member function marked with it will be **automatically invoked inside the class `init {}` block** at compile time. The plugin also **prevents explicit calls** to`@CallOnInit` functions, avoiding accidental double invocations.

Built on Kotlin 2.3 FIR + IR compiler APIs. Supports Kotlin Multiplatform.

## Features

- **Auto-init injection** — annotated functions are called in `init {}` automatically via IR transformation
- **Compile-time safety** — explicit calls to `@CallOnInit` functions produce a compiler error
- **Ordering guarantee** — user-written `init {}` code runs first, then `@CallOnInit` functions (in declaration order)
- **Multiplatform** — annotations module supports all KMP targets (JVM, JS, Native, Wasm)
- **Zero runtime overhead** — the plugin works entirely at compile time

## Sample Usage

### Basic

```kotlin
import ru.astrainteractive.klibs.compilerplugin.callinit.CallOnInit

class SimpleClass {
    @CallOnInit
    fun setup() {
        println("Automatically called on init!")
    }
}

fun main() {
    SimpleClass() // prints: "Automatically called on init!"
}
```

### Multiple functions + existing init block

```kotlin
import ru.astrainteractive.klibs.compilerplugin.callinit.CallOnInit

class SomeClass {
    init {
        println("Original init block")  // runs first
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
        println("This is NOT called automatically")
    }
}

fun main() {
    SomeClass()
    // Output:
    //   Original init block
    //   myStartupFun called via @CallOnInit!
    //   anotherStartupFun called via @CallOnInit!
}
```

### Compile-time error on explicit calls

```kotlin
class MyClass {
    @CallOnInit
    fun setup() {
    }

    fun caller() {
        setup() // ERROR: Explicit call to @CallOnInit function is not allowed
    }
}
```

## How It Works

1. **FIR phase** — `CallOnInitCallChecker` scans function calls and reports compile errors if any `@CallOnInit`-annotated function is called explicitly
2. **IR phase** — `CallOnInitIrTransformer` visits each class, finds `@CallOnInit` functions, and injects `this.fn()` calls at the end of the `init {}` block (creating one if it doesn't exist)

