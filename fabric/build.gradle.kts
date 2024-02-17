architectury {
    platformSetupLoomIde()
    fabric()
}

val common: Configuration by configurations.creating
val shadowCommon: Configuration by configurations.creating
val developmentFabric: Configuration by configurations.getting

configurations {
    compileClasspath.configure { extendsFrom(common) }
    runtimeClasspath.configure { extendsFrom(common) }
    developmentFabric.extendsFrom(common)
}

dependencies {
    modImplementation("net.fabricmc:fabric-loader:${libs.versions.fabric.loader.version.get()}")
    modApi("net.fabricmc.fabric-api:fabric-api:${libs.versions.fabric.api.version.get()}+${libs.versions.minecraft.version.get()}")
    modApi("dev.architectury:architectury-fabric:${libs.versions.architectury.version.get()}")
    modImplementation(libs.create.fabric)
    modImplementation(libs.kubejs.fabric)
    implementation(libs.gson.overwrite)
    include(libs.gson.overwrite)
    common(project(":common", "namedElements")) {
        isTransitive = false
    }
    shadowCommon(project(":common", "transformProductionFabric")) {
        isTransitive = false
    }
    modLocalRuntime(libs.lazy.dfu)
    modLocalRuntime(libs.mod.menu)


}
tasks.processResources {
    set(
            "version" to version.toString(),
            "fabric-loader-version" to libs.versions.fabric.loader.version.get(),
            "fabric_api_version" to libs.versions.fabric.api.version.get(),
            "minecraft_version" to libs.versions.minecraft.version.get(),
            "create_version" to libs.versions.create.fabric.get(),
    )
}

tasks.shadowJar {
    exclude("architectury.common.json")
    configurations = listOf(shadowCommon)
    archiveClassifier.set("dev-shadow")
}

tasks.remapJar {
    injectAccessWidener = true
    inputFile.set(tasks.shadowJar.get().archiveFile)
    dependsOn(tasks.shadowJar.get())
    archiveClassifier.set(null as String?)
}

tasks.jar {
    archiveClassifier.set("dev")
}

tasks.sourcesJar {
    val commonSources = project(":common").tasks.sourcesJar
    dependsOn(commonSources.get())
    from(commonSources.get().archiveFile.map {
        zipTree(it)
    })
}

components.getByName("java") {
    this as AdhocComponentWithVariants
    withVariantsFromConfiguration(project.configurations.getByName("shadowRuntimeElements")) {
        skip()
    }
}

fun ProcessResources.set(vararg vars: Pair<String, String>) {
    val mapOf: Map<String, String> = mapOf(*vars)
    mapOf.forEach {
        inputs.property(it.key, it.value)
    }
    filesMatching("fabric.mod.json") {
        expand(mapOf)
    }
}
