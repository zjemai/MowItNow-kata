#!groovy​

def config = [:]


pipeline {

    agent {
        label 'agent-slave'
    }


    tools {
        maven 'maven-3.6.1'
        jdk 'jdk-17.0.4.1+1'
    }

    stages {

        stage('Checkout sources') {
            steps {
                deleteDir()
                checkout scm
            }
        }

        stage(' Build') {
            steps {
                script {
                   configFileProvider([configFile(fileId: 'mower-maven-settings', variable: 'MAVEN_SETTINGS_XML')]) {
                        sh "mvn -U --batch-mode -s $MAVEN_SETTINGS_XML clean install"
                    }
                }
            }
        }


        stage(' Packaging ') {
            steps {
                script {
                    configFileProvider([configFile(fileId: 'mower-maven-settings', variable: 'MAVEN_SETTINGS_XML')]) {
                        sh "mvn -U --batch-mode -s $MAVEN_SETTINGS_XML clean package -P packaging-jar packaging-ipk"
                    }
                }
            }
        }


    }

    post {
        success {
            sendEmail("Successful")
        }
        unstable {
            sendEmail("Unstable")
        }
        failure {
            sendEmail("Failed")
        }
    }
}
