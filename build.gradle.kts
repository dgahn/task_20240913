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
    id("com.epages.restdocs-api-spec") version "0.19.2"
    id("org.hidetake.swagger.generator") version "2.19.2"
    id("org.asciidoctor.jvm.convert") version "4.0.3"
}

group = "me.dgahn"
version = "1.0-SNAPSHOT"

apply(plugin = "jacoco")

val asciidoctorExtensions: Configuration by configurations.creating

val snippetsDir by extra { file("$buildDir/generated-snippets") }
val restdocsDir by extra { file("$buildDir/resources/main/static/docs") }
tasks {
    withType<Test> {
        useJUnitPlatform()
        testLogging {
            events("PASSED", "FAILED", "SKIPPED")
        }
        outputs.dir(snippetsDir)
        finalizedBy("jacocoTestReport")
        finalizedBy(asciidoctor)
        finalizedBy("copyOasToSrcResource")
        finalizedBy("copyOasToBuildResource")
        dependsOn(formatKotlin)
    }
    bootJar {
        dependsOn(asciidoctor)
        dependsOn("copyOasToBuildResource")
        from("${asciidoctor.get().outputDir}") {
            into("static/docs")
        }
    }

    register<Copy>("copyOasToBuildResource") {
        delete("src/main/resources/static/swagger-ui/openapi3.yaml")
        from("$buildDir/api-spec/openapi3.yaml")
        into("$buildDir/resources/main/static/swagger-ui/.")
        dependsOn("openapi3")
    }

    register<Copy>("copyOasToSrcResource") {
        delete("src/main/resources/static/swagger-ui/openapi3.yaml")
        from("$buildDir/api-spec/openapi3.yaml")
        into("src/main/resources/static/swagger-ui/.")
        dependsOn("openapi3")
    }

    register<Copy>("copyRestDocs") {
        delete("src/main/resources/static/docs/index.html")
        from("$buildDir/resources/main/static/docs/index.html")
        into("src/main/resources/static/docs/.")
    }

    asciidoctor {
        configurations(asciidoctorExtensions.name) // 위에서 작성한 configuration 적용
        inputs.dir(snippetsDir) // snippetsDir 를 입력으로 구성
        setOutputDir(restdocsDir) // restdocsDir 를 출력으로 구성
        attributes["project-version"] = project.version
        attributes["source-highlighter"] = "prettify"

        // source가 없으면 .adoc파일을 전부 html로 만들어버림
        // source 지정시 특정 adoc만 HTML로 만든다.
        sources {
            include("**/index.adoc")
        }

        attributes(
            mapOf(
                "backend" to "html5",
                "snippets" to file("build/generated-snippets")
            )
        )

        finalizedBy("copyRestDocs")
    }
    named("jacocoTestReport") {
        mustRunAfter(asciidoctor)
        mustRunAfter(":copyOasToBuildResource")
    }
    named("resolveMainClassName") {
        mustRunAfter(asciidoctor)
        mustRunAfter(":copyOasToBuildResource")
    }
    jar {
        dependsOn(asciidoctor)
        dependsOn("copyOasToBuildResource")
    }

    java {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    compileKotlin {
        kotlinOptions {
            jvmTarget = "21"
        }
    }
    compileTestKotlin {
        kotlinOptions {
            jvmTarget = "21"
        }
    }
}

openapi3 {
    setServer("/")
    title = "긴급 연락망 제공을 위한 API 서버"
    description = "긴급 연락망 제공을 위한 API 서버 문서"
    version = "0.0.1"
    format = "yaml"
    delete {
        file("src/main/resources/static/swagger-ui/openapi3.yaml")
    }
    copy {
        from("$buildDir/api-spec/openapi3.yaml")
        into("src/main/resources/static/swagger-ui/.")
    }
}

repositories {
    mavenCentral()
}

extra["springCloudVersion"] = "2022.0.2"

dependencies {
    // for kotlin
    implementation(kotlin("stdlib"))

    // for web
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

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

    // for restdocs
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
    testImplementation("com.epages:restdocs-api-spec-mockmvc:0.19.2")
}

jacoco {
    toolVersion = "0.8.12"
}

sourceSets {
    main {
        java {
            srcDirs("src/main/java", "src/generated/java")
        }
    }
}
