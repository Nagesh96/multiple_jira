from jira import JIRA
import sys

def update_issue_field(username, password, field_id, new_value, jira_tickets):
    jira_url = "https://jira.charter.com"

    try:
        jira = JIRA(server=jira_url, basic_auth=(username, password))

        # Prepare the update field dictionary
        update_fields = {field_id: new_value}

        for issue_key in jira_tickets:
            issue = jira.issue(issue_key)

            issue.update(fields=update_fields)

            print(f"Field '{field_id}' in issue '{issue_key}' updated successfully to '{new_value}'.")
    except Exception as e:
        print(f"Failed to update the field '{field_id}'. Error:", str(e))

# Check if correct number of arguments is provided
if len(sys.argv) != 5:
    print("Usage: python script.py username password customfield_ID=new_value")
    sys.exit(1)

# Parse command line arguments
username = sys.argv[1]
password = sys.argv[2]
field_arg = sys.argv[3]
field_id, new_value = field_arg.split('=')

# Define the Jira issue keys here
jira_tickets = ["MOBIT2-31903", "MOBIT2-31904"]  # Replace these with your specific issue keys

update_issue_field(username, password, field_id, new_value, jira_tickets)
