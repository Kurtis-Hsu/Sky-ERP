import org.apache.tools.ant.filters.ReplaceTokens
import org.gradle.kotlin.dsl.filter

plugins {
    java
    kotlin("jvm") version "2.1.10"
    id("org.springframework.boot") version "3.4.3"
    id("io.spring.dependency-management") version "1.1.7"
}

val runtimeEnvironment = properties["com.vireosci.sky.runtime-environment"]

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
    sourceSets.main {
        // 排除非指定环境的 application.yml 配置文件
        resources.exclude {
            val name = it.file.name
            name.startsWith("application-") && name.endsWith(".yml") && name != "application-$runtimeEnvironment.yml"
        }
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // Spring Framework
    implementation("org.springframework.boot", "spring-boot-starter-web")
    implementation("org.springframework.boot", "spring-boot-starter-security")
    implementation("org.springframework.boot", "spring-boot-starter-data-redis")
    implementation("org.springframework.boot", "spring-boot-starter-data-jpa")
    implementation("org.springframework.boot", "spring-boot-starter-validation")
    testImplementation("org.springframework.boot", "spring-boot-starter-test")
    compileOnly("org.springframework.boot", "spring-boot-configuration-processor")

    // PostgreSQL
    runtimeOnly("org.postgresql", "postgresql")

    // Jasypt encryption/decryption tool
    implementation("com.github.ulisesbocchio", "jasypt-spring-boot-starter", "3.0.5")

    // Jose4j
    implementation("org.bitbucket.b_c", "jose4j", "0.9.3")

    // JUnit Test
    testRuntimeOnly("org.junit.platform", "junit-platform-launcher")
}

springBoot.mainClass = "com.vireosci.sky.App"

tasks.processResources {
    filesMatching("config/**/*.yml") {
        filter<ReplaceTokens>(
            "tokens" to mapOf(
                "running-environment" to runtimeEnvironment,
                "project-name" to project.name,
                "project-version" to version
            )
        )
    }
    filesMatching("banner.txt") { filter<ReplaceTokens>("tokens" to mapOf("project-version" to version)) }
}

tasks.withType<Test> {
    useJUnitPlatform()
    jvmArgs("-javaagent:${classpath.find { it.name.contains("byte-buddy-agent") }?.absolutePath}")
}
