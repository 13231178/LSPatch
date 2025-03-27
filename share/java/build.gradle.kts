val apiCode: Int by rootProject.extra
val verCode: Int by rootProject.extra
val verName: String by rootProject.extra
val coreVerCode: Int by rootProject.extra
val coreVerName: String by rootProject.extra
val androidSourceCompatibility: JavaVersion by rootProject.extra
val androidTargetCompatibility: JavaVersion by rootProject.extra

plugins {
    id("java-library")
}

//java {
//    sourceCompatibility = androidSourceCompatibility
//    targetCompatibility = androidTargetCompatibility
//}
java {
    // 设置 JVM toolchain 为特定的 Java 版本，例如 Java 21
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}
val generateTask = task<Copy>("generateJava") {
    val template = mapOf(
        "apiCode" to apiCode,
        "verCode" to verCode,
        "verName" to verName,
        "coreVerCode" to coreVerCode,
        "coreVerName" to coreVerName
    )
    inputs.properties(template)
    from("src/template/java")
    into("$buildDir/generated/java")
    expand(template)
}

sourceSets["main"].java.srcDir("$buildDir/generated/java")
tasks["compileJava"].dependsOn(generateTask)
