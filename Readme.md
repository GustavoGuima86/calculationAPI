Hello there, it's just a smart calculator that save the calcs in a database.

But you must be asking yourself why I did it, well I wanted to learn some Kotlin concepts
and my only Idea at that moment was it.

If you want to use it as an example, feel free

That is not totally finished yet


## Technical requirements

Must use docker + compose to set up the local environment 
- H2 database
- Docker
- gradle
- Kotlin
- Junit 5 
- restassured
- Swagger(open api)
- localstack rds

Must use terraform to set up a cloud environment
- EKS(helm, carpenter)
- ALB
- RDS 
- Security group
- ACL
- WAF
- vpc
- public subnet(alb)
- isolated subnet(db)

The application must deploy using Helm Chart using the command

`helm upgrade --install calculation calculation/ --values calculation/values.yaml --debug`

your cluster must be run and configured locally

in the next interation I'll try to implement ArgoCD to deploy it 

Other change I'll make it to deploy the Image in a private ECR repo in the pipeline

## BUILD

`docker compose up -d db`
`docker compose build`
`docker compose up calculationApp`


# Swagger api

[alb_url/swagger-ui/index.html#/]()
