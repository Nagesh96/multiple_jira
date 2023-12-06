stage('Upload artifacts to JFrog Repo') {
    steps {
        script {
            def server = Artifactory.server('Artifactory')
            def rtMaven = Artifactory.newMavenBuild()
            
            def uploadSpec = [
                files: [
                    [
                        pattern: '*.war',
                        target: 'example-repo-local'
                    ]
                ]
            ]
            
            // Capture console output in a variable
            def consoleOutput = server.upload(uploadSpec).trim()
            
            // Extracting JFrog URL using regex
            def jfrogUrlRegex = ~/https?:\/\/(?:www\.)?jfrog\.com\/[^\s]+/
            def jfrogUrlMatch = (consoleOutput =~ jfrogUrlRegex)
            
            if (jfrogUrlMatch.find()) {
                def jfrogUrl = jfrogUrlMatch[0]
                echo "JFrog URL: ${jfrogUrl}"
                // Use jfrogUrl variable as needed
            } else {
                echo "JFrog URL not found in console output"
                // Handle the case when the URL is not found
            }
        }
    }
}
