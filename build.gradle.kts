plugins {
	java
	id("org.springframework.boot") version "3.1.1"
	// id("io.spring.dependency-management") version "1.1.0"
	id("org.graalvm.buildtools.native") version "0.9.23"
}

apply(plugin = "io.spring.dependency-management")
group = "com.myown.echo"
version = "0.0.1-SNAPSHOT"


repositories {
    mavenLocal()
    gradlePluginPortal()
    maven {
        url = uri("https://repo.maven.apache.org/maven2/")
    }
    maven {
        url = uri("https://repo.spring.io/release")
    }
}
apply(plugin = "org.springframework.boot")

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(20))
    }
}
dependencies {
    implementation("org.springframework.boot:spring-boot-starter")    
    implementation("org.springframework.boot:spring-boot-starter-actuator")   
	implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-security")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.withType<JavaCompile> {
	val compilerArgs = options.compilerArgs
    compilerArgs.add("--enable-preview")
	compilerArgs.add("-Xdoclint:all,-missing")
	compilerArgs.add("-Xlint:all")
}
graalvmNative {
    binaries {
        named("main") {
            javaLauncher.set(javaToolchains.launcherFor {
                languageVersion.set(JavaLanguageVersion.of(20))
                // vendor.set(JvmVendorSpec.matching("GraalVM Community"))
            })
        }
    }
}