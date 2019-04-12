#!/usr/bin/env groovy



// Call this from a jenkins job to echo message
// Usage:
//              script {
//              sendNotifications.info 'This is my message'
//              }
def info(mymessage) {
    echo "INFO: ${mymessage}"
}


// Call this from the beginning of a jenkins job to post to slack that a build has started
// Usage:
//                script {
//                sendNotifications.buildstartnotify ("${myslackchannel}", "${myslacktokenCredentialId}", "${myslackteamDomain}")
//                }
def buildstartnotify(mychannel, mytokenCredentialId, myteamDomain){
    colorCode = '#FFFF00'
    slackSend (channel: mychannel, color: colorCode, message: "STARTED:* Job ${env.JOB_NAME} build ${env.BUILD_NUMBER}\n More info at: ${env.BUILD_URL} (<${env.BUILD_URL}|Open>)", teamDomain: myteamDomain, tokenCredentialId: mytokenCredentialId)
}


// Call this from your manual intervention stage. That code should have logic to prompt for a manual intervention in the event of a deploy to prod.
// Usage:
//                 script {
//                 sendNotifications.manualintervention ("${myslackchannel}", "${myslacktokenCredentialId}", "${myslackteamDomain}")
//                 }
def manualintervention(mychannel, mytokenCredentialId, myteamDomain){
    //def mychannelpost = "${mychannel}"
    colorCode = '#FFFF00'
    slackSend (channel: mychannel, color: colorCode, message: "Code for ${env.JOB_NAME} has been pushed to production that requires an approval, please click here to continue ${env.BUILD_URL}input ", teamDomain: myteamDomain, tokenCredentialId: mytokenCredentialId)
}

// Call this as a post build action in your jenkins job to post the job status to slack
// Usage
//            post {
//            always {  
//                sendNotifications ("${myslackchannel}", "${myslacktokenCredentialId}", 'My Extra Message', "${myslackteamDomain}")
//            }
//        }   //(Outside of the stages block)
def call(mychannel, mytokenCredentialId, extramessage, myteamDomain){
  //def extramessagepost = "${extramessage}"
  def buildStatus = "${currentBuild.currentResult}"
  // Override default values based on build status
  if (buildStatus == 'STARTED') {
    color = 'YELLOW'
    colorCode = '#FFFF00'
  } else if (buildStatus == 'SUCCESS') {
    color = 'GREEN'
    colorCode = '#00FF00'
  } else {
    color = 'RED'
    colorCode = '#FF0000'
  }  
  //echo "lets see what this value is: ${currentBuild.currentResult}"
  slackSend (channel: mychannel, color: colorCode, message: "*${currentBuild.currentResult}:* Job ${env.JOB_NAME} extramessage: ${extramessage} build ${env.BUILD_NUMBER}\n More info at: ${env.BUILD_URL} (<${env.BUILD_URL}|Open>)", teamDomain: myteamDomain, tokenCredentialId: mytokenCredentialId)   

}



