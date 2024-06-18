# Group 5

Our project is a dashboard for the Human Resources department of the company AVISI and has "recruitment-dashboard" as its root directory. 

## ! TO-DO !

In util > JiraIssueDeserializer in line 53, issue id corresponding to InternIssue needs to be adjusted to correspond to the id of intern issues in the json file returned by the Jira API endpoint.

Aso in In util > JiraIssueDeserializer in line 44, the path to the extraCost field needs to be specified.

### Generating an App Password for Gmail Integration

To integrate Gmail with our application securely, you need to generate an app password. This password is used to authenticate the application without exposing your main Gmail password. Follow these steps to generate an app password for your Gmail account:

1. **Enable Two-Step Verification**:
   - Go to your [Google Account settings](https://myaccount.google.com).
   - Click on "Security" in the left-hand menu.
   - Under "Signing in to Google," find "2-Step Verification" and follow the instructions to enable it.
   - Complete the setup process, which may involve adding a phone number for verification.

2. **Generate an App Password**:
   - After enabling Two-Step Verification, go back to the "Security" section in your Google Account settings.
   - Under "Signing in to Google," click on "App passwords."
   - Sign in again if prompted.
   - In the "Select app" dropdown, choose "Mail" (or select "Other (Custom name)" and enter a name like "GmailRouteApp").
   - In the "Select device" dropdown, choose the appropriate device or select "Other" and provide a custom name.
   - Click "Generate."
   - A 16-character app password will be generated. Copy this password.

3. **Use the App Password in Your Application**:
   - Replace your regular Gmail password with the app password in your application's configuration.
   - For example, in your Apache Camel route configuration, update the password parameter with the app password:
     ```kotlin
     from("imaps://imap.gmail.com?username=your-email@gmail.com&password=your-app-password&delete=false&unseen=false")
         .routeId("gmailRoute")
         // Additional route configuration...
     ```



## Documentation

To access the System and Software Documentation of the project, first import the recruitment-dashboard folder into IntelliJ IDEA. Then navigate to recruitment-dashboard/build/dokka/index.html. This is the main page of our documentation page. You can open it by clicking on one of the browser icons in the upper right corner of the IDE.
The documentation contains all information for running, configuring and deploying the project, as well as all other information on System and Software.


## Running the Main Project

Once you have opened the documentation page, navigat to 'System Documentation' > 'Requirements and Getting Started'.
Here you will find a checklist for running the project. If you just want to run it using our mock data, it is enough to simply set up the database as described and then run the main function in RecruitmentDashboardApplication.kt. Doing this will run the routes and populate the database with statistics and data calculated from mock-data which simulates the response of the Jira API endpoint.

