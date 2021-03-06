plugins {
   id("java")
   id("kotlin-multiplatform")
   id("java-library")
}

repositories {
   mavenCentral()
}

kotlin {

   targets {
      jvm {
         compilations.all {
            kotlinOptions {
               jvmTarget = "1.8"
            }
         }
      }
   }

   targets.all {
      compilations.all {
         kotlinOptions {
            freeCompilerArgs + "-Xuse-experimental=kotlin.Experimental"
         }
      }
   }

   sourceSets {

      val jvmTest by getting {
         dependencies {
            implementation(project(":kotest-core"))
            implementation(project(":kotest-assertions"))
            implementation(project(":kotest-runner:kotest-runner-junit5"))
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.3")
            implementation("org.apache.logging.log4j:log4j-slf4j-impl:2.12.1")
            implementation("com.nhaarman:mockito-kotlin:1.6.0")
            implementation("org.mockito:mockito-core:2.24.0")

         }
      }
   }
}

tasks {
   test {
      useJUnitPlatform()
      testLogging {
         showExceptions = true
         showStandardStreams = true
         events = setOf(
            org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED,
            org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED
         )
         exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
      }
   }
}

apply(from = "../../publish.gradle")
