
## Technical requirements

Must use docker + compose to set up the local environment 
    H2 database
    Docker
    gradle
    Kotlin
    Junit 5 
    restassured
    Swagger(open api)
    localstack rds

must use terraform to set up a cloud environment
    EKS(helm, carpenter)
    ALB
    RDS 
    Security group
    ACL
    WAF
    vpc
    private subnet(eks)
    public subnet(alb)
    isolated subnet(db)
    

## Functional specification


## BUILD

docker compose up -d db
docker compose build
docker compose up calculationApp

