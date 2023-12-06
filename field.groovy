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
            
            def artifacts = buildInfo.getDeployedArtifacts()
            def artifactUrl = artifacts[0].getRemotePath()
            env.ARTIFACTORY_URL = "${server.getUrl()}${artifactUrl}"
            
            echo "Artifact URL: ${env.ARTIFACTORY_URL}"
        }
    }
}
