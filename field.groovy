pipeline {
    agent any

    parameters {
        string(name: 'JIRA_TICKETS', defaultValue: '', description: 'Provide the Jira ticket no')
    }

    tools {
        maven 'maven-3'
    }

    stages {
        stage('Validate Inputs') {
            steps {
                script {
                    echo "USER INPUT ........"
                    //env.JIRA_TICKETS = ${params.JIRA_TICKETS}
                    echo "JiraTickets: ${JIRA_TICKETS}"
                }
            }
        }
        
        stage('git checkout') {
            steps {
                checkout scmGit(branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[credentialsId: 'git-cred', url: 'https://github.com/Nagesh96/addressbook.git']])
            }
        }
        
        stage('Compile and Build') {
            steps {
                echo 'building the artifacts'
                sh "mvn clean install -U -DskipTests=true"
            }
        }

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
                    echo "Artifact Display Url: ${env.RUN_ARTIFACTS_DISPLAY_URL}"
                }
            }
        }
        
        /*stage('Run Python') {
            steps {
                script {
                    sh "pwd"
                    sh "python3 field.py"
                }
            }
        }*/
    }
}
