import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.2.3"
	id("io.spring.dependency-management") version "1.1.4"
	kotlin("jvm") version "1.9.22"
	kotlin("plugin.spring") version "1.9.22"
	kotlin("plugin.jpa") version "1.9.22"
	id("org.jetbrains.dokka") version "1.9.20"
}

group = "nl.avisi.recruitment"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_21
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-data-rest")
	implementation("org.springframework.boot:spring-boot-starter-hateoas")
	implementation("org.springframework.boot:spring-boot-starter-mail")
	implementation("com.sun.mail:jakarta.mail:2.0.1")
	implementation("javax.mail:javax.mail-api:1.6.2")
	implementation("com.sun.mail:javax.mail:1.6.2")
	implementation ("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.apache.camel.springboot:camel-mail-starter:4.4.0")
	implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.apache.camel.springboot:camel-spring-boot-starter:4.4.0")
	implementation("com.google.code.gson:gson:2.8.5")
	implementation ("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.13.0")
	implementation(kotlin("reflect"))
	implementation("org.jboss.logging:jboss-logging:3.5.0.Final")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.apache.poi:poi-ooxml:5.0.0")
	implementation("org.apache.camel:camel-jackson:4.4.0")
	runtimeOnly("org.postgresql:postgresql")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "21"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.dokkaHtml {
	outputDirectory.set(buildDir.resolve("dokka"))

	dokkaSourceSets {
		configureEach {
			includeNonPublic.set(false)
			reportUndocumented.set(true)
			skipEmptyPackages.set(true)
			skipDeprecated.set(false)
			jdkVersion.set(8)
		}
	}

	doLast {
		// Copy System Documentation
		copy {
			from("system_documentation")
			into(buildDir.resolve("dokka/system_documentation"))
		}
	}
}

