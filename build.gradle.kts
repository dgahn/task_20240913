plugins {
    jacoco
    val kotlinVersion = "2.0.20"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion
    kotlin("plugin.jpa") version kotlinVersion
    id("org.jmailen.kotlinter") version "4.4.1"
    id("org.springframework.boot") version "3.3.3"
    id("io.spring.dependency-management") version "1.1.6"
}

group = "me.dgahn"
version = "1.0-SNAPSHOT"

apply(plugin = "jacoco")

tasks {
    withType<Test> {
        useJUnitPlatform()
        testLogging {
            events("PASSED", "FAILED", "SKIPPED")
        }
        dependsOn(formatKotlin)
    }
    java {
        sourceCompatibility = JavaVersion.VERSION_22
        targetCompatibility = JavaVersion.VERSION_22
    }
    compileKotlin {
        kotlinOptions {
            jvmTarget = "22"
        }
    }
    compileTestKotlin {
        kotlinOptions {
            jvmTarget = "22"
        }
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // for kotlin
    implementation(kotlin("stdlib"))

    // for web
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    // for docs
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.6.0")

    // for db
    runtimeOnly("com.h2database:h2")

    // for log
    implementation("io.github.microutils:kotlin-logging-jvm:3.0.5")

    // for test
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
        exclude(module = "mockito-core")
    }

    // for assertions
    testImplementation("io.kotest:kotest-assertions-core:5.8.1")

    // for mock library
    testImplementation("io.mockk:mockk:1.13.9")
    testImplementation("com.ninja-squad:springmockk:4.0.2")

    // for mockmvc
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
}

jacoco {
    toolVersion = "0.8.12"
}
