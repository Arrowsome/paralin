plugins {
    kotlin("jvm") version "1.4.32"
    maven
}

group = "dev.devura"
version = "0.1.2"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    compileOnly("io.javalin:javalin:3.13.4")
}
