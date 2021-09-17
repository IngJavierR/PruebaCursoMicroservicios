pipeline {
	agent any
	tools {
		maven 'M3_6'
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
		COMMITER_EMAIL = sh(
			script: 'git --no-pager show -s --format=\'%an - %ae\'',
			returnStdout: true
		).trim()
	}
	stages {

		stage('Build and Analyze') {
			steps {
                dir('microservicio-service/'){
                    echo 'Analyse Code'
                    withSonarQubeEnv('sonarqube') { 
                        sh "mvn clean package dependency-check:check sonar:sonar \
                            -Dsonar.projectKey=21_MyCompany_Microservice \
                            -Dsonar.projectName=21_MyCompany_Microservice \
                            -Dsonar.sources=src/main \
							-Dsonar.coverage.exclusions=**/*TO.java,**/*DO.java,**/curso/web/**/*,**/curso/persistence/**/*,**/curso/commons/**/*,**/curso/model/**/* \
                            -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml \
							-Dsonar.dependencyCheck.htmlReportPath=microservicio-web/target/site/dependency/dependency-check-report.html \
							-Dsonar.dependencyCheck.jsonReportPath=microservicio-web/target/site/dependency/dependency-check-report.json \
							-Dsonar.dependencyCheck.summarize=true \
							-Djacoco.output=tcpclient -Djacoco.address=127.0.0.1 -Djacoco.port=10001"
                    }
                }
			}
		}
		/*stage("Quality Gate") {
            steps {
				timeout(time: 2, unit: 'MINUTES') {
                	waitForQualityGate abortPipeline: true
              	}
            }
        }*/

        stage('Build Docker') {
			steps {
                dir('microservicio-service/'){
                    echo 'Build image'
                    withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: 'dockerhub_id	', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD']])  {
                        sh 'docker login -u $USERNAME -p $PASSWORD'
                        sh 'docker build -t microservicio .'
                    }
                }
			}
		}

		stage('Run Push') {
			steps {
                dir('microservicio-service/'){
                    echo 'Push Docker Image'
					withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: 'dockernexus_id', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD']])  {
						sh 'docker tag microservicio 172.17.0.5:8082/microservicio:1'
						sh 'docker push 172.17.0.5:8082/repository/docker-group/microservicio:1'
					}
                }
			}
		}

        /*stage('Run Docker') {
			steps {
                dir('microservicio-service/'){
                    echo 'Run Docker'
                    sh 'docker run -d -p 8090:8090 microservicio'
                }
			}
		}*/

		/*stage('Database') {
			steps {
				dir('database/dev/'){
					sh 'liquibase --version'
					//sh '/opt/liquibase/liquibase --changeLogFile="changesets/db.changelog-master.xml" update'
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