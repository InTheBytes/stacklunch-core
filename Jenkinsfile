pipeline {
    agent any
    tools {
        maven 'Maven'
        jdk 'Java JDK'
        dockerTool 'Docker'
    }
    stages {
        stage('Clean and Test target') {
            steps {
                sh 'mvn clean test'
            }
        }
        stage('Test and Package') {
            steps {
                sh 'mvn package'
            }
        }
        stage('Code Analysis: Sonarqube') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    sh 'mvn sonar:sonar'
                }
            }
        }
        stage('Await Quality Gateway') {
            steps {
                waitForQualityGate abortPipeline: true
            }
        }
        stage('Upload to S3 Bucket') {
            steps {
                dir('/var/lib/jenkins/workspace/Core-Library/target') {
                    withAWS(region:'us-east-2',credentials:'aws-ecr-creds') {
                        s3Delete(bucket:"stacklunch-core-library", path:'');
                        s3Upload(bucket:"stacklunch-core-library", path:'', includePathPattern:'**/*.jar');
                    }   
                } 
            }
        }
    }
    post {
        always {
            sh 'mvn clean'
        }
    }
}