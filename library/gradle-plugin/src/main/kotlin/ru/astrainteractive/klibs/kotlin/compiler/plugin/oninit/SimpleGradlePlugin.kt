package ru.astrainteractive.klibs.kotlin.compiler.plugin.oninit

import org.gradle.api.Project
import org.gradle.api.provider.Provider
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilation
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilerPluginSupportPlugin
import org.jetbrains.kotlin.gradle.plugin.SubpluginArtifact
import org.jetbrains.kotlin.gradle.plugin.SubpluginOption
import ru.astrainteractive.klibs.kotlin.compiler.plugin.oninit.BuildConfig.ANNOTATIONS_LIBRARY_COORDINATES

@Suppress("unused") // Used via reflection.
class SimpleGradlePlugin : KotlinCompilerPluginSupportPlugin {
    override fun apply(target: Project) {
        target.extensions.create(
            "simplePlugin",
            SimpleGradleExtension::class.java
        )
    }

    override fun isApplicable(kotlinCompilation: KotlinCompilation<*>): Boolean = true

    override fun getCompilerPluginId(): String = BuildConfig.KOTLIN_PLUGIN_ID

    override fun getPluginArtifact(): SubpluginArtifact = SubpluginArtifact(
        groupId = BuildConfig.KOTLIN_PLUGIN_GROUP,
        artifactId = BuildConfig.KOTLIN_PLUGIN_NAME,
        version = BuildConfig.KOTLIN_PLUGIN_VERSION,
    )

    override fun applyToCompilation(
        kotlinCompilation: KotlinCompilation<*>
    ): Provider<List<SubpluginOption>> {
        val project = kotlinCompilation.target.project

        kotlinCompilation.defaultSourceSet.dependencies { implementation(ANNOTATIONS_LIBRARY_COORDINATES) }
        if (kotlinCompilation.defaultSourceSet.name == "commonMain") {
            project.dependencies.add("commonMainImplementation", ANNOTATIONS_LIBRARY_COORDINATES)
        }

        return project.provider {
            emptyList()
        }
    }
}
