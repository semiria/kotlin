import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.maven
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.vcs

/*
The settings script is an entry point for defining a TeamCity
project hierarchy. The script should contain a single call to the
project() function with a Project instance or an init function as
an argument.

VcsRoots, BuildTypes, Templates, and subprojects can be
registered inside the project using the vcsRoot(), buildType(),
template(), and subProject() methods respectively.

To debug settings scripts in command-line, run the

    mvnDebug org.jetbrains.teamcity:teamcity-configs-maven-plugin:generate

command and attach your debugger to the port 8000.

To debug in IntelliJ Idea, open the 'Maven Projects' tool window (View
-> Tool Windows -> Maven Projects), find the generate task node
(Plugins -> teamcity-configs -> teamcity-configs:generate), the
'Debug' option is available in the context menu for the task.
*/

version = "2020.1"

project {

    buildType(Build)

    features {
        feature {
            id = "PROJECT_EXT_3"
            type = "OAuthProvider"
            param("endpoint", "approle")
            param("role-id", "9a28f343-ce46-50d8-0279-a0ebaa6bfc99")
            param("displayName", "Connection vault on project")
            param("secure:secret-id", "credentialsJSON:a0ef4086-43ca-4b03-a962-7a966dd9049a")
            param("namespace", "")
            param("fail-on-error", "true")
            param("vault-namespace", "")
            param("providerType", "teamcity-vault")
            param("url", "https://vault.stef.us1.dev.riq.com")
        }
    }
}

object Build : BuildType({
    name = "Build"
    description = "test 123 poulet"

    vcs {
        root(DslContext.settingsRoot)
    }

    steps {
        maven {
            goals = "clean test"
            pomLocation = ".teamcity/pom.xml"
            runnerArgs = "-Dmaven.test.failure.ignore=true"
        }
    }

    triggers {
        vcs {
        }
    }
})
