The Java Simulation Library (JSL) is a Java library for performing Monte Carlo and Discrete-Event
Dynamic System computer simulations.

This is a Gradle based project.

## JSL Book

https://rossetti.git-pages.uark.edu/jslbookdownbook

## JSL Documentation

If you are looking for the JSL documentation you can find it here:

https://rossetti.git-pages.uark.edu/JSL-Documentation/

The repository for the documentation is here:

https://git.uark.edu/rossetti/JSL-Documentation

Please be aware that the book and javadoc documentation may lag the releases due to lack of developer time.

## Gradle and Build Details

The JSL is organized as a multi-project gradle build.  There are two sub-projects:

JSLCore - the main simulation functionality, with a limited number of dependencies

JSLExtensions - an extension of the JSL that adds database, Excel and other functionality that has many open source dependencies

Additional projects are available for illustrating and other work related to the JSL.

JSExamples - a project that has example code that illustrates the JSLCore and JSLExtensions being used.

JSLExampleProject - a pre-configured project using gradle that is setup to use the JSLCore and JSLExtensions

JSLTesting - a separate project that does some very basic testing related to the JSL

JSLKotlin - very preliminary work on porting the JSL to Kotlin

To add the JSL to your gradle build files use the following artifact coordinates:

group = "io.github.rossetti"
name = "JSLCore"
version = "R1.0.7"

group = "io.github.rossetti"
name = "JSLExtensions"
version = "R1.0.7"

Of course, the version numbers may be different for additional releases. As an example, for kotlin DLS:

```gradle
plugins {
    `java-library`
}

//group = "org.example"
//version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {

    // next two lines allows use of JSL libraries within the project
    // update the release number when new releases become available
    api(group = "io.github.rossetti", name = "JSLCore", version = "R1.0.7")
    api(group = "io.github.rossetti", name = "JSLExtensions", version = "R1.0.7")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_11
}
```

There is an example gradle project called JSLExampleProject that has the appropriate gradle build script that will get you started with a JSL project.

## Cloning and Setting Up a Project

If you are using IntelliJ, you can use its clone repository functionality to 
setup a working version. Or, simply download the repository and use IntelliJ to open up
the repository.  IntelliJ will recognize the JSL project as a gradle build and configure an appropriate project.

## Release Notes

Latest Release: R1.0.7
	The project has been moved to GitHub and now available on maven central.

Latest Release: R1.0.6
	Release 1.0.6 is not backwards compatible. There are changes to the JSL class that are likely to cause users to need to update their code.