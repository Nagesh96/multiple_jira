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

# Define the Jira issue keys here
ticket_details = os.environ.get('JIRA_TICKET')
jira_tickets = ticket_details.split(',')
print(jira_tickets)
# Placeholder values for field_id and new_value
field_id = "customfield_17856"
new_value = os.environ.get('Value')

# Check if correct number of arguments is provided (username, password)
if len(sys.argv) != 3:
    print("Usage: python script.py username password")
    sys.exit(1)

# Parse command line arguments
username = sys.argv[1]
password = sys.argv[2]

update_issue_field(username, password, field_id, new_value, jira_tickets)
