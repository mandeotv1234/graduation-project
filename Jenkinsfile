pipeline {
    agent any
    
    environment {
        DOCKER_REGISTRY = 'your-registry.com'
        DOCKER_CREDENTIALS_ID = 'docker-hub-credentials'
        GIT_CREDENTIALS_ID = 'git-credentials'
    }
    
    tools {
        gradle 'Gradle-8.0'
        nodejs 'NodeJS-18'
    }
    
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        
        stage('Build Config Server') {
            steps {
                dir('server/config-server') {
                    script {
                        sh 'chmod +x gradlew'
                        sh './gradlew clean build -x test'
                    }
                }
            }
        }
        
        stage('Test Config Server') {
            steps {
                dir('server/config-server') {
                    script {
                        sh './gradlew test'
                    }
                }
                publishTestResults testResultsPattern: 'server/config-server/build/test-results/test/*.xml'
            }
        }
        
        stage('Build Discovery Server') {
            steps {
                dir('server/discovery-server') {
                    script {
                        sh 'chmod +x gradlew'
                        sh './gradlew clean build -x test'
                    }
                }
            }
        }
        
        stage('Test Discovery Server') {
            steps {
                dir('server/discovery-server') {
                    script {
                        sh './gradlew test'
                    }
                }
                publishTestResults testResultsPattern: 'server/discovery-server/build/test-results/test/*.xml'
            }
        }
        
        stage('Build User Service') {
            steps {
                dir('server/user-service') {
                    script {
                        sh 'chmod +x gradlew'
                        sh './gradlew clean build -x test'
                    }
                }
            }
        }
        
        stage('Test User Service') {
            steps {
                dir('server/user-service') {
                    script {
                        sh './gradlew test'
                    }
                }
                publishTestResults testResultsPattern: 'server/user-service/build/test-results/test/*.xml'
            }
        }
        
        stage('Build API Gateway') {
            steps {
                dir('server/api-gateway') {
                    script {
                        sh 'chmod +x gradlew'
                        sh './gradlew clean build -x test'
                    }
                }
            }
        }
        
        stage('Test API Gateway') {
            steps {
                dir('server/api-gateway') {
                    script {
                        sh './gradlew test'
                    }
                }
                publishTestResults testResultsPattern: 'server/api-gateway/build/test-results/test/*.xml'
            }
        }
        
        stage('Build Client') {
            steps {
                dir('client') {
                    script {
                        sh 'npm ci'
                        sh 'npm run build'
                    }
                }
            }
        }
        
        stage('Test Client') {
            steps {
                dir('client') {
                    script {
                        sh 'npm test -- --coverage --watchAll=false'
                    }
                }
                publishTestResults testResultsPattern: 'client/test-results.xml'
                publishHTML([
                    allowMissing: false,
                    alwaysLinkToLastBuild: true,
                    keepAll: true,
                    reportDir: 'client/coverage/lcov-report',
                    reportFiles: 'index.html',
                    reportName: 'Client Coverage Report'
                ])
            }
        }
        
        stage('Code Quality Analysis') {
            parallel {
                stage('SonarQube Analysis - Server') {
                    steps {
                        script {
                            def services = ['config-server', 'discovery-server', 'user-service', 'api-gateway']
                            services.each { service ->
                                dir("server/${service}") {
                                    withSonarQubeEnv('SonarQube') {
                                        sh './gradlew sonarqube'
                                    }
                                }
                            }
                        }
                    }
                }
                
                stage('SonarQube Analysis - Client') {
                    steps {
                        dir('client') {
                            withSonarQubeEnv('SonarQube') {
                                sh 'npm run sonar'
                            }
                        }
                    }
                }
            }
        }
        
        stage('Build Docker Images') {
            parallel {
                stage('Build Config Server Image') {
                    steps {
                        dir('server/config-server') {
                            script {
                                def image = docker.build("${DOCKER_REGISTRY}/config-server:${BUILD_NUMBER}")
                                docker.withRegistry("https://${DOCKER_REGISTRY}", DOCKER_CREDENTIALS_ID) {
                                    image.push()
                                    image.push("latest")
                                }
                            }
                        }
                    }
                }
                
                stage('Build Discovery Server Image') {
                    steps {
                        dir('server/discovery-server') {
                            script {
                                def image = docker.build("${DOCKER_REGISTRY}/discovery-server:${BUILD_NUMBER}")
                                docker.withRegistry("https://${DOCKER_REGISTRY}", DOCKER_CREDENTIALS_ID) {
                                    image.push()
                                    image.push("latest")
                                }
                            }
                        }
                    }
                }
                
                stage('Build User Service Image') {
                    steps {
                        dir('server/user-service') {
                            script {
                                def image = docker.build("${DOCKER_REGISTRY}/user-service:${BUILD_NUMBER}")
                                docker.withRegistry("https://${DOCKER_REGISTRY}", DOCKER_CREDENTIALS_ID) {
                                    image.push()
                                    image.push("latest")
                                }
                            }
                        }
                    }
                }
                
                stage('Build API Gateway Image') {
                    steps {
                        dir('server/api-gateway') {
                            script {
                                def image = docker.build("${DOCKER_REGISTRY}/api-gateway:${BUILD_NUMBER}")
                                docker.withRegistry("https://${DOCKER_REGISTRY}", DOCKER_CREDENTIALS_ID) {
                                    image.push()
                                    image.push("latest")
                                }
                            }
                        }
                    }
                }
                
                stage('Build Client Image') {
                    steps {
                        dir('client') {
                            script {
                                def image = docker.build("${DOCKER_REGISTRY}/client:${BUILD_NUMBER}")
                                docker.withRegistry("https://${DOCKER_REGISTRY}", DOCKER_CREDENTIALS_ID) {
                                    image.push()
                                    image.push("latest")
                                }
                            }
                        }
                    }
                }
            }
        }
        
        stage('Integration Tests') {
            steps {
                script {
                    sh 'docker-compose -f docker-compose.test.yml up -d'
                    sh 'sleep 60' // Wait for services to start
                    
                    // Run integration tests
                    sh 'docker-compose -f docker-compose.test.yml exec -T api-gateway curl -f http://api-gateway:8080/actuator/health'
                    sh 'docker-compose -f docker-compose.test.yml exec -T user-service curl -f http://user-service:8081/actuator/health'
                    
                    sh 'docker-compose -f docker-compose.test.yml down'
                }
            }
        }
        
        stage('Deploy to Staging') {
            when {
                branch 'develop'
            }
            steps {
                script {
                    sh 'docker-compose -f docker-compose.staging.yml down'
                    sh 'docker-compose -f docker-compose.staging.yml up -d'
                }
            }
        }
        
        stage('Deploy to Production') {
            when {
                branch 'main'
            }
            steps {
                script {
                    input message: 'Deploy to production?', ok: 'Deploy'
                    sh 'docker-compose -f docker-compose.prod.yml down'
                    sh 'docker-compose -f docker-compose.prod.yml up -d'
                }
            }
        }
    }
    
    post {
        always {
            // Clean up
            sh 'docker system prune -f'
            
            // Archive artifacts
            archiveArtifacts artifacts: '**/build/libs/*.jar', fingerprint: true
            archiveArtifacts artifacts: 'client/build/**/*', fingerprint: true
            
            // Publish test results
            publishTestResults testResultsPattern: '**/build/test-results/test/*.xml'
            
            // Clean workspace
            cleanWs()
        }
        
        success {
            emailext (
                subject: "Jenkins Build Success: ${env.JOB_NAME} - ${env.BUILD_NUMBER}",
                body: "Build completed successfully. Check console output at ${env.BUILD_URL}",
                to: "${env.CHANGE_AUTHOR_EMAIL}"
            )
        }
        
        failure {
            emailext (
                subject: "Jenkins Build Failed: ${env.JOB_NAME} - ${env.BUILD_NUMBER}",
                body: "Build failed. Check console output at ${env.BUILD_URL}",
                to: "${env.CHANGE_AUTHOR_EMAIL}"
            )
        }
    }
}