plugins {
    kotlin("jvm") version "1.4.32"
}

group = "dev.devura"
version = "0.1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    compileOnly("io.javalin:javalin:3.13.4")
}
