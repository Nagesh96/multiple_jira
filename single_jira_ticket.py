from jira import JIRA
import sys

def update_issue_field(username, password, issue_key, field_id, new_value):
    jira_url = "https://jira.charter.com"

    try:
        jira = JIRA(server=jira_url, basic_auth=(username, password))
        issue = jira.issue(issue_key)

        # Prepare the update field dictionary
        update_fields = {field_id: new_value}

        issue.update(fields=update_fields)

        print(f"Field '{field_id}' in issue '{issue_key}' updated successfully to '{new_value}'.")
    except Exception as e:
        print(f"Failed to update the field '{field_id}'. Error:", str(e))

# Set your Jira username, password, and issue key here
username = "P3214461"
password = "Home16Lander*"
jiraTicket = "MOBIT2-31903"

# Check if correct number of arguments is provided
if len(sys.argv) != 2:
    print("Usage: python script.py customfield_ID=new_value")
    sys.exit(1)

# Parse key-value pair for the field update from command line arguments
field_arg = sys.argv[1]
field_id, new_value = field_arg.split('=')

update_issue_field(username, password, jiraTicket, field_id, new_value)
