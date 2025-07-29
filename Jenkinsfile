pipeline {
    agent any
    
    environment {
        DOCKER_REGISTRY = 'your-registry.hcmus.com'
        DOCKER_CREDENTIALS_ID = 'docker-hub-credentials'
    }
    
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        
        stage('Build Services') {
            parallel {
                stage('Build Config Server') {
                    steps {
                        dir('server/config-server') {
                            sh 'chmod +x gradlew && ./gradlew clean build -x test'
                        }
                    }
                }
                
                stage('Build Discovery Server') {
                    steps {
                        dir('server/discovery-server') {
                            sh 'chmod +x gradlew && ./gradlew clean build -x test'
                        }
                    }
                }
                
                stage('Build User Service') {
                    steps {
                        dir('server/user-service') {
                            sh 'chmod +x gradlew && ./gradlew clean build -x test'
                        }
                    }
                }
            }
        }
        
        stage('Test Services') {
            parallel {
                stage('Test Config Server') {
                    steps {
                        dir('server/config-server') {
                            sh './gradlew test'
                        }
                    }
                }
                
                stage('Test Discovery Server') {
                    steps {
                        dir('server/discovery-server') {
                            sh './gradlew test'
                        }
                    }
                }
                
                stage('Test User Service') {
                    steps {
                        dir('server/user-service') {
                            sh './gradlew test'
                        }
                    }
                }
            }
        }
        
        stage('Deploy with Kong') {
            steps {
                script {
                    sh 'docker-compose down'
                    sh 'docker-compose up -d'
                    sh 'sleep 60' // Wait for services to start
                    sh 'chmod +x kong-setup.sh && ./kong-setup.sh'
                }
            }
        }
        
        stage('Kong Health Check') {
            steps {
                script {
                    sh 'curl -f http://localhost:8001/status'
                    sh 'curl -f http://localhost:8000/api/users/health'
                }
            }
        }
    }
}
