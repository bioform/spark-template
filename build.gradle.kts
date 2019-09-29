import org.jetbrains.kotlin.gradle.plugin.KotlinPluginWrapper
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    val kotlinVersion = "1.3.21"

    java
    application
    idea
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.noarg") version kotlinVersion
    kotlin("plugin.allopen") version kotlinVersion
    kotlin("plugin.jpa") version kotlinVersion
    kotlin("kapt") version kotlinVersion
}

allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
}

application {
    mainClassName = "info.krasnoff.bulletin.MainKt"
}

group = "info.krasnoff.bulletin.board"
version = "1.0-SNAPSHOT"
val requeryVersion = "1.5.1"

val kotlinLoggingVer = "1.6.25"
val logbackVer = "1.2.3"
val jAnsiVer = "1.17.1"

repositories {
    jcenter()
}

idea {
    module {
        val kaptMain = file("build/generated/source/kapt/main")
        sourceDirs.add(file(kaptMain))
        generatedSourceDirs.add(file(kaptMain))
    }
}

kapt {
    generateStubs = true
}

dependencies {
    compile(kotlin("stdlib-jdk8"))
    compile( "com.sparkjava", "spark-core", "2.7.2")
    compile( "javax.annotation", "javax.annotation-api", "1.3.2")

    compile("io.requery", "requery", requeryVersion)
    compile("io.requery", "requery-kotlin", requeryVersion)
    kapt("io.requery", "requery-processor", requeryVersion)

    compile("io.github.microutils:kotlin-logging:$kotlinLoggingVer")
    compile("ch.qos.logback:logback-classic:$logbackVer")
    compile("org.fusesource.jansi:jansi:$jAnsiVer")

    // https://mvnrepository.com/artifact/com.google.api-client/google-api-client
    compile( "com.google.api-client:google-api-client:1.30.4")

    compile("com.google.code.gson:gson:2.8.5")


    testCompile("junit", "junit", "4.12")
    // https://mvnrepository.com/artifact/ch.qos.logback/logback-classic
    testCompile("ch.qos.logback", "logback-classic", "1.2.3")

    // Database Drivers
    /*
    compile("org.hibernate:hibernate-core:${ext["hibernate"]}") {
        exclude(mapOf("group" to "javax.enterprise")) // there is no need for CDI-API nor @Inject stuff
    }
    */

    compile("com.h2database:h2:${ext["h2"]}")

}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}
tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

//------------------------------------------------------------------------------------------------------------------------
//    Tasks - Application
//------------------------------------------------------------------------------------------------------------------------

tasks.create<GradleBuild>("bootFullApplication") {
    group = "info.krasnoff.bulletin.board"
    tasks = mutableListOf(":client:npmUpdate", ":client:clean", ":client:buildClientToSpark", ":run")
}