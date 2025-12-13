pipeline {
    agent any

    environment {
        SONAR_TOKEN            = credentials('sonar-token')
        AWS_ACCESS_KEY_ID      = credentials('aws-access-key')
        AWS_SECRET_ACCESS_KEY  = credentials('aws-secret-key')

        IMAGE_NAME = "irctc-rest-api"
        AWS_REGION = "ap-south-1"
        ECR_REPO   = "024848440975.dkr.ecr.${AWS_REGION}.amazonaws.com/${IMAGE_NAME}:latest"
    }

    stages {

        stage('Git Checkout') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/aditya12-g/IRCTC-REST-API.git',
                    credentialsId: 'github-token'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('sonarqube') {
                    sh '''
                       mvn clean package -DskipTests sonar:sonar \
                      -Dsonar.projectKey=irctc \
                      -Dsonar.host.url=http://13.201.13.138:9000 \
                      -Dsonar.login=$SONAR_TOKEN
                    '''
                }
            }
        }

        stage('Maven Build') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Docker Build') {
            steps {
                sh 'docker build -t irctc-rest-api .'
            }
        }

        stage('Trivy Scan') {
            steps {
                sh 'trivy image --severity HIGH,CRITICAL irctc-rest-api'
            }
        }

        stage('Push to ECR') {
            steps {
                sh '''
                  aws ecr get-login-password --region ap-south-1 | \
                  docker login --username AWS --password-stdin 024848440975.dkr.ecr.ap-south-1.amazonaws.com

                  docker tag irctc-rest-api $ECR_REPO
                  docker push $ECR_REPO
                '''
            }
        }

        stage('Deploy to EKS') {
            steps {
                sh '''
                  aws eks --region ap-south-1 update-kubeconfig --name <CLUSTER_NAME>

                  kubectl apply -f k8s/deployment.yaml
                  kubectl apply -f k8s/service.yaml
                '''
            }
        }
    }

    post {
        success {
            echo "Pipeline completed successfully! 🎉"
        }
        failure {
            echo "Pipeline failed. ❌ Check logs."
        }
    }
}


