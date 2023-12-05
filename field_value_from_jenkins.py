from jira import JIRA
import sys
import os

def update_issue_field(username, password, issue_keys, field_id, new_value):
    jira_url = "https://jira.charter.com"

    try:
        jira = JIRA(server=jira_url, basic_auth=(username, password))

        for issue_key in issue_keys:
            issue = jira.issue(issue_key)

            # Prepare the update field dictionary
            update_fields = {field_id: new_value}

            issue.update(fields=update_fields)

            print(f"Field '{field_id}' in issue '{issue_key}' updated successfully to '{new_value}'.")
    except Exception as e:
        print(f"Failed to update the field '{field_id}'. Error:", str(e))

# Set your Jira username, password, and issue keys here
username = "P3214461"
password = "Home16Lander*"
jiraTickets = ["MOBIT2-31903", "MOBIT2-31904"]  # Example list of Jira ticket IDs

# Fetching field ID from the script directly
field_id = "customfield_17856"

# Fetching field value from Jenkins environment variable
new_value = os.environ.get('FIELD_VALUE')

if not new_value:
    print("Field value not provided from Jenkins.")
    sys.exit(1)

update_issue_field(username, password, jiraTickets, field_id, new_value)
