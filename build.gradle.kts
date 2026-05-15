group = "hexlet.code"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

plugins {
    id("application")
    id("checkstyle")
    id("se.patrikerdes.use-latest-versions") version "0.2.18"
    id("com.github.ben-manes.versions") version "0.49.0"
    id("com.gradleup.shadow") version "8.3.10"
    id("io.freefair.lombok") version "8.10"
    id("org.sonarqube") version "4.0.0.2929"
    id("jacoco")
    id("org.springframework.boot") version "3.5.14"
    id("io.spring.dependency-management") version "1.1.7"
    id("gg.jte.gradle") version "3.2.1"
    id("io.sentry.jvm.gradle") version "5.6.0"
}

dependencies {
    annotationProcessor("org.mapstruct:mapstruct-processor:1.6.3")
    implementation("org.mapstruct:mapstruct:1.6.3")

    // Source: https://mvnrepository.com/artifact/io.sentry/sentry
    implementation("io.sentry:sentry:8.38.0")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.8")
    implementation("org.openapitools:jackson-databind-nullable:0.2.10")
    implementation("org.springframework.boot:spring-boot-configuration-processor")
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    // implementation("org.springframework.boot:spring-boot-starter-actuator")

    implementation("com.h2database:h2")
    implementation("org.postgresql:postgresql")

    // для отладки
    implementation("org.springframework.boot:spring-boot-devtools")

    testImplementation("net.datafaker:datafaker:2.4.3")
    testImplementation("net.javacrumbs.json-unit:json-unit-assertj:4.0.0")
    testImplementation("org.instancio:instancio-junit:5.0.2")
    testImplementation("org.junit.jupiter:junit-jupiter:5.12.0")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation(platform("org.junit:junit-bom:5.12.0"))
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

application {
    mainClass.set("hexlet.code.AppApplication")
}

jte {
    precompile()
}

configure<gg.jte.gradle.JteExtension> {
    sourceDirectory.set(project.layout.projectDirectory.dir("src/main/resources/templates").asFile.toPath())
    generate()
}

checkstyle {
    toolVersion = "10.9.3"

    configFile = file("config/checkstyle/checkstyle.xml")

    isIgnoreFailures = true
    maxWarnings = 0
    maxErrors = 0
}

jacoco {
    toolVersion = "0.8.12"
}

sonar {
    properties {
        property("sonar.projectName", "Hexlet.Java.m5k.TaskManager")
        property("sonar.projectKey", "AnrDaemon_java-project-99")
        property("sonar.organization", "anrdaemon")
        property("sonar.host.url", "https://sonarcloud.io")
        property("sonar.exclusions", "**/gg/jte/generated/**")
        property("sonar.coverage.exclusions", "**/gg/jte/generated/**")
    }
}

testing {
    suites {
        // Configure the built-in test suite
        val test by getting(JvmTestSuite::class) {
            // Use JUnit Jupiter test framework
            useJUnitJupiter("5.9.3")

            targets.all {
                testTask.configure {
                    finalizedBy(tasks.jacocoTestReport)
                }
            }
        }
    }
}

tasks.jar {
    manifest {
        attributes(
            "Main-Class" to application.mainClass.get(),
            "Implementation-Title" to "Task manager course work",
            "Implementation-Version" to project.version
        )
    }
}

tasks.withType<JavaCompile>().configureEach {
    dependsOn("generateJte")
}

tasks.matching { it.name == "generateEffectiveLombokConfig" }.configureEach {
    dependsOn("generateJte")
}

tasks.withType<JavaCompile> {
    options.compilerArgs.addAll(arrayOf(
        "-Aproject=${project.group}/${project.name}"
    ))
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required.set(true)
        csv.required.set(false)
    }
}

tasks.register("install") {
    dependsOn("installDist")
}

tasks.named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar") {
    from("jte-classes") {
        include("**/*.class")
    }
    mergeServiceFiles()
}
