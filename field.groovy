stage('Upload artifacts to JFrog Repo') {
    steps {
        echo 'Publishing Artifacts'
        script {
            def server = Artifactory.server('Artifactory')
            def rtMaven = Artifactory.newMavenBuild()
            def uploadSpec = """
                {
                    "files": [{
                        "pattern": "*.war",
                        "target": "example-repo-local"
                    }]
                }"""
            
            def buildInfo = server.upload(uploadSpec)
            server.publishBuildInfo(buildInfo)
            
            // Capture the artifact URL from the console output
            def consoleOutput = currentBuild.rawBuild.getLog(1000) // Adjust the number to capture more lines if needed
            def artifactUrl = consoleOutput =~ /Deploying artifact:\s(https?:\/\/[^ ]+)/
            
            if (artifactUrl) {
                env.ARTIFACTORY_URL = artifactUrl[0][1]
                echo "Artifact URL: ${env.ARTIFACTORY_URL}"
            } else {
                error("Failed to retrieve the artifact URL from console output.")
            }
        }
    }
}
