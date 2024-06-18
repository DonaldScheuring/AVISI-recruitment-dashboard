# Group 5

Our project is a dashboard for the Human Resources department of the company AVISI and has "recruitment-dashboard" as its root directory. 

## ! TO-DO !

In util > JiraIssueDeserializer in line 51, issue id corresponding to InternIssue needs to be adjusted to correspond to the id of intern issues in the json file returned by the Jira API endpoint.


## Documentation

To access the System and Software Documentation of the project, first import the recruitment-dashboard folder into IntelliJ IDEA. Then navigate to recruitment-dashboard/build/dokka/index.html. This is the main page of our documentation page. You can open it by clicking on one of the browser icons in the upper right corner of the IDE.
The documentation contains all information for running, configuring and deploying the project, as well as all other information on System and Software.


## Running the Main Project

Once you have opened the documentation page, navigat to 'System Documentation' > 'Requirements and Getting Started'.
Here you will find a checklist for running the project. If you just want to run it using our mock data, it is enough to simply set up the database as described and then run the main function in RecruitmentDashboardApplication.kt. Doing this will run the routes and populate the database with statistics and data calculated from mock-data which simulates the response of the Jira API endpoint.

