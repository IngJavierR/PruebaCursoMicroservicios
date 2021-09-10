pipeline {
	agent any
	tools {
		maven 'M3'
        jdk 'jdk-1.8.101'
		//nodejs 'NodeJs12'
	}
	environment {
		//Functions
		DEPLOY_PATTERN = NewPatterName()

		// Projtect configurations
		COMPANY = "MyPersonalCompany"
		//SLACK_CHANNEL = "#slack_channel"
		ENVIRONMENT = "Dev"

		// General configurations		
		SONAR_KEY = "21_COMPANY_Microservicio_Dev";
		SONAR_SERVER = "http://172.17.0.3:9000";
		SONAR_TOKEN = "";
		COMMITER_EMAIL = sh(
			script: 'git --no-pager show -s --format=\'%an - %ae\'',
			returnStdout: true
		).trim()
	}
	stages {
		stage('Build Microservice') {
            steps {
                echo 'Building Microservice'
                dir('microservicio-service/'){
                    sh "mvn clean package"
                }
            }
		}

		stage('Sonarqube') {
			steps {
                dir('microservicio-service/'){
                    echo 'Analyse Code'
                    sh ''
                    withSonarQubeEnv('sonarqube') { // If you have configured more than one global server connection, you can specify its name
                        sh "mvn clean package sonar:sonar \
                            -Dsonar.projectKey=${SONAR_KEY} \
                            -Dsonar.projectName=${SONAR_KEY} \
                            -Dsonar.sources=src/main \
                            -Dsonar.coverage.exclusions=**/*TO.java,**/*DO.java \
                            -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml"
                    }
                }
			}
		}

        stage('Build Docker') {
			steps {
                dir('microservicio-service/'){
                    echo 'Build image'
                    sh 'docker build -t microservicio .'
                }
			}
		}

        stage('Run Docker') {
			steps {
                dir('microservicio-service/'){
                    echo 'Run Docker'
                    sh 'docker run -d -p 8090:8090 microservicio'
                }
			}
		}

		/*stage('Database') {
			steps {
				dir('database/dev/'){
					sh '/opt/liquibase/liquibase --version'
					sh '/opt/liquibase/liquibase --changeLogFile="changesets/db.changelog-master.xml" update'
					echo 'Applying Db changes'
				}
			}
		}*/

		/*stage('Testing Estress') {
			steps {
				dir('testing/stress/'){
					echo 'Running Stress Testing'
				}
			}
		}*/

		/*stage('Testing UI') {
			steps {
				dir('testing/ui/'){
					echo 'Running UI Testing'
				}
			}
		}*/
	}
	post {
		always {
			deleteDir()
			echo 'Always'
		}
		success {
			echo 'I succeeeded!'
		}
		unstable {
			echo 'I am unstable :/'
		}
		failure {
			echo 'I failed :('
		}
		changed {
			echo 'Things were different before...'
		}
	}
}

def NewPatterName()
{
	return "_" + new Date().format('ddMMyyyy') + "_" + "${env.BUILD_NUMBER}";
}