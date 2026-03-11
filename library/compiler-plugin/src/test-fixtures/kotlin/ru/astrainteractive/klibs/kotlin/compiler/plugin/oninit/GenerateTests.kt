package ru.astrainteractive.klibs.kotlin.compiler.plugin.oninit

import org.jetbrains.kotlin.generators.dsl.junit5.generateTestGroupSuiteWithJUnit5
import ru.astrainteractive.klibs.kotlin.compiler.plugin.oninit.runners.AbstractJvmBoxTest
import ru.astrainteractive.klibs.kotlin.compiler.plugin.oninit.runners.AbstractJvmDiagnosticTest

fun main() {
    generateTestGroupSuiteWithJUnit5 {
        testGroup(
            testDataRoot = "compiler-plugin/src/testData/res",
            testsRoot = "compiler-plugin/src/test-gen/java",
            init = {
                testClass<AbstractJvmDiagnosticTest> {
                    model("diagnostics")
                }

                testClass<AbstractJvmBoxTest> {
                    model("box")
                }
            }
        )
    }
}
