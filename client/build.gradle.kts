import com.moowork.gradle.node.npm.NpmTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("com.moowork.node") version "1.2.0"
}

buildscript {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

//------------------------------------------------------------------------------------------------------------------------
//    Plugin configuration
//------------------------------------------------------------------------------------------------------------------------

node {
    version = "10.13.0"
    npmVersion = "6.1.0"
    download = true
    workDir = file("${rootDir}/client/node")
    nodeModulesDir = file("${rootDir}/client")
}



//------------------------------------------------------------------------------------------------------------------------
//    Tasks - NPM
//------------------------------------------------------------------------------------------------------------------------

task<Delete>("clean") {
    group = "build client"
    delete("${rootDir}/client/dist", "${rootDir}/src/main/resources/public")
}

task<Delete>("cleanNpm") {
    group = "build client"
    dependsOn("clean")
    delete("${rootDir}/client/node", "${rootDir}/client/node_modules")
}

task("npmUpdate") {
    group = "build client"
    dependsOn("npm_update")
}

//------------------------------------------------------------------------------------------------------------------------
//    Tasks - Standalone client
//------------------------------------------------------------------------------------------------------------------------

task<NpmTask>("buildStandaloneClient") {
    dependsOn("npmInstall")
    group = "build client"
    description = "Compile client side folder for development"
    setArgs(listOf("run", "buildStandalone"))
}

task<NpmTask>("serveStandaloneClientWatch") {
    dependsOn("npmInstall")
    group = "build client"
    description = "Builds, serves and watches the client side assets for rebuilding"
    setArgs(listOf("run", "serveStandaloneWatch"))
}

task<NpmTask>("serveStandaloneClient") {
    dependsOn("npmInstall")
    group = "build client"
    description = "Compile client side folder for production"
    setArgs(listOf("start"))
}

task<GradleBuild>("bootStandaloneClient") {
    group = "info.krasnoff.bulletin.board"
    tasks = mutableListOf("clean", "buildStandaloneClient", "serveStandaloneClient")
//    finalizedBy 'npm_shutdown'
}

task<GradleBuild>("bootStandaloneClientWatch") {
    group = "info.krasnoff.bulletin.board"
    tasks = mutableListOf("clean", "serveStandaloneClientWatch")
//    finalizedBy 'npm_shutdown'
}

//------------------------------------------------------------------------------------------------------------------------
//    Tasks - Integrated client
//------------------------------------------------------------------------------------------------------------------------

task<NpmTask>("buildClientToSpark") {
    dependsOn("npmInstall")
    group = "build client"
    description = "Compile client side folder for development"
    setArgs(listOf("run", "buildToSpark"))
}
