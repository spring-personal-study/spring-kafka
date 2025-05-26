plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.4.4"
    id("io.spring.dependency-management") version "1.1.7"
    id("com.google.protobuf") version "0.9.4"
}

group = "study.mq"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
    maven {
        url = uri("https://packages.confluent.io/maven/")
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.apache.kafka:kafka-streams")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.springframework.kafka:spring-kafka")
    implementation("org.apache.kafka:kafka-clients")
    // Kotlin protobuf runtime
    implementation("com.google.protobuf:protobuf-kotlin:3.25.1")
    //implementation("com.google.protobuf:protobuf-java:4.28.2")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    // protobuf
    implementation("io.confluent:kafka-protobuf-serializer:7.5.1")
    // test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("org.springframework.kafka:spring-kafka-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.bootBuildImage {
    builder = "paketobuildpacks/builder-jammy-base:latest"
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.25.1"
    }

    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                maybeCreate("java").apply {
                    outputSubDir = "generated"
                }
            }
        }
    }
}

sourceSets {
    main {
        java {
            srcDirs("src/generated/main/kotlin")
        }
    }
}
