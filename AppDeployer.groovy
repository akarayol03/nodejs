properties([[$class: 'JiraProjectProperty'], parameters([string(defaultValue: 'V1', description: 'Please provide  a version number', name: 'APP_VERSION', trim: false)])])
node {
    stage ("pull repo"){
       git 'https://github.com/akarayol03/nodejs.git'
    }

    stage ("Build Image"){
        sh "docker build -t docker-app:${APP_VERSION} ."
        
    }

    stage ("Image Tag"){
        sh '''docker tag docker-app:${APP_VERSION} 489673539939.dkr.ecr.us-east-1.amazonaws.com/docker-app:${APP_VERSION}'''
        
    }

    stage ("Login to ECR"){
        sh '''$(aws ecr get-login --no-include-email --region us-east-1)'''
        
    }

    stage ("Push Image"){
        sh "docker images"
        sh "docker push 489673539939.dkr.ecr.us-east-1.amazonaws.com/docker-app:${APP_VERSION}"
    }

    stage ("Notify"){
        sh "echo Hello"
        
    }

}
