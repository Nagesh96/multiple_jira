pipeline {
    agent any

    parameters {
        string(name: 'jiraTicket', description: 'Enter Jira ticket(s) separated by commas', defaultValue: '', trim: true)
    }

    environment {
        JIRA_TICKETS = "${params.jiraTicket}"
    }

    stages {
        stage('Convert Jira Ticket(s)') {
            steps {
                script {
                    def tickets = JIRA_TICKETS.split(',')
                    for (int i = 0; i < tickets.size(); i++) {
                        def envVarName = "JIRA_TICKET_${i+1}"
                        env."${envVarName}" = tickets[i].trim()
                    }
                }
            }
        }

        stage('Example Stage') {
            steps {
                script {
                    // Example usage of the created environment variables
                    echo "First Jira Ticket: ${env.JIRA_TICKET_1}"
                    echo "Second Jira Ticket: ${env.JIRA_TICKET_2}"
                    // Add more actions using the environment variables as needed
                }
            }
        }
    }
}
