# Shared Jenkins library to support slack notifications.

This is a Jenkins shared library to allow multiple Jenkins pipeline jobs to send Slack notifications with minimum code reuse.

As we continually copy code for new jobs, we start to notice that we’re reusing the same code over and over again in many jobs. Rather than repeating code patterns in multiple locations (does not comply with principle of DRY), take things a step further and define that code pattern as a specific helper function that is managed and maintained in one global location. This is Shared Libraries in a nutshell. [jenkinsfile best practices](https://blogs.perficient.com/2018/02/22/maintenance-reuse-best-practices-jenkins-pipelines/)

## Getting Started

### Prerequisites

[Jenkins](http://jenkins.io)
[Slack Account](https://slack.com)
Slack Credentials in Jenkins Credential Store
[Jenkins Slack Plugin](http://wiki.jenkins-ci.org/display/JENKINS/Slack+Plugin) Tested with 2.20
Jenkins Shared Library Configured
Version Control (github/gitlab)

### Installing

1. Configure Jenkins Shared Library:
   [Jenkins Shared Library Docs}(https://jenkins.io/doc/book/pipeline/shared-libraries/)
   [Shared Library Tutorial](https://cleverbuilder.com/articles/jenkins-shared-library/#example-creating-and-using-a-jenkins-shared-library)

2. Drop sendNotifications.groovy into the /vars directory in your shared library repo.

3. In your Jenkinsfile for a pipeline job that will utilize the slack notification library, add the following line to the beginning:

```
@Library('my-shared-library-name') _
def myslackchannel = '#mychannel'
def myslacktokenCredentialId = 'slack-credential-id-from-jenkins-credential-store'
def myslackteamDomain = 'my-team-domain'
```

4. In your Jenkinsfile for a pipeline job that will utilize the slack notification library, you can use the following example to send a slack notification:

```
   stage('Send Notification to slack when build starts'){
       steps {
            script {
                sendNotifications.buildstartnotify ("${myslackchannel}", "${myslacktokenCredentialId}", "${myslackteamDomain}")
            }
```

## Authors

- **Dane Fetterman** -

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments
