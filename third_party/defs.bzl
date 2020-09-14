load("@rules_jvm_external//:defs.bzl", "maven_install")


def dependencies():
    maven_install(
        artifacts = [
            "com.jcabi.incubator:xembly:0.24.0",
            "com.jcabi:jcabi-xml:0.22.2",
            "org.cactoos:cactoos:0.46",

            "com.google.guava:guava:29.0-jre",
            "org.projectlombok:lombok:1.18.12",
            "com.github.spullara.mustache.java:compiler:0.9.6",

            "org.slf4j:slf4j-api:1.7.30",
            "org.slf4j:slf4j-simple:1.7.30",
            "ch.qos.logback:logback-classic:1.2.3",
            "info.picocli:picocli:4.5.0",

            "org.apache.commons:commons-compress:1.20",
            "org.apache.maven:maven-compat:3.6.3",
            "org.apache.maven:maven-embedder:3.6.3",
            "org.apache.maven.wagon:wagon-http:2.9",
            "org.apache.maven.wagon:wagon-http-lightweight:2.9",
            "org.apache.maven.wagon:wagon-provider-api:2.9",
            "org.eclipse.aether:aether-connector-basic:1.1.0",
            "org.eclipse.aether:aether-transport-wagon:1.0.2.v20150114",

            "junit:junit:4.12",
            "org.hamcrest:hamcrest-library:1.3",
        ],
        repositories = [
            "https://jcenter.bintray.com/",
            "https://maven.google.com",
            "https://repo1.maven.org/maven2",
        ],
        fetch_sources = True,
    )