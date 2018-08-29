# code_challenge
## Description
QLess code challenge resolution.
Implemented two endpoints according to swagger file definition:
1. GET - /merchant/location
1. GET - /merchant/location/{location_gid}

## Building and running
### Build
1. Navigate to the base folder
1. Execute `mvn clean install`

## Run
### Run Locally
1. Execute `mvn spring-boot:run`.
1. The service will be available on: http://localhost:8080

## TODO list:
1. Add a validator for the enpoints parameters.
1. Add security to the endpoints
1. Add Integration Tests to cover all the scenarios