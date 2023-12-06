https://jfrog.charter.com/artifactory/mobile-snapshot-local/com/spectrum/mobile/mbo-dataassetbuildup/4.0.0-SNAPSHOT/mbo-dataassetbuildup-4.0.0-SNAPSHOT.war


  https:\/\/jfrog\.sample\.com\/artifactory\/[\w-]+\/[\w.-]+\/[\w.-]+\/[\d.]+-(SNAPSHOT|\d+\.\d+\.\d+)\/[\w.-]+-[\d.]+-(SNAPSHOT|\d+\.\d+\.\d+)\.(war|jar)



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
          
            env.ARTIFACT_URL = server.getUrl() + "/example-repo-local/your-artifact.war"
            echo "Artifact Display Url: ${env.ARTIFACT_URL}"
        }
    }
}
