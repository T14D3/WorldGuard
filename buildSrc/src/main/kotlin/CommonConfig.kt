import org.cadixdev.gradle.licenser.LicenseExtension
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.repositories
import org.gradle.kotlin.dsl.the

fun Project.applyCommonConfiguration() {
    group = rootProject.group
    version = rootProject.version

    repositories {
        mavenCentral()
        maven { url = uri("https://maven.enginehub.org/repo/") }
        maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots/") }
    }

    configurations.all {
        resolutionStrategy {
            cacheChangingModulesFor(5, "MINUTES")
        }
    }

    plugins.withId("java") {
        the<JavaPluginExtension>().toolchain {
            languageVersion.set(JavaLanguageVersion.of(21))
        }
    }

    apply(plugin = "org.cadixdev.licenser")
    configure<LicenseExtension> {
        header(rootProject.file("HEADER.txt"))
        include("**/*.java")
        include("**/*.kt")
    }
}
