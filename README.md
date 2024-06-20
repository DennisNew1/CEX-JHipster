# Getting Started

In order to start developing you need todo the following:

- clone this project
- create and setup an oracle DB
- use Maven to run this project.

Use the `git clone` command to clone this project (link follows once this is pushed)

For Oracle DB Setup use the following commands

```powershell
docker run -d -p 1521:1521 --name oracle-db container-registry.oracle.com/database/free:latest
# wait some time so the DB is running and ready
# this changes the password to oracledb
docker exec oracle-db ./setPassword.sh oracledb

```

Since the DB is ready now, you can run the application from the main directory with `./mvnw` on powershell or `mvnw` on OSX.

Open the Browser in `localhost:8080` and you should see the startpage and login interface.

# jhipster

This application was generated using JHipster 8.5.0, you can find documentation and help at [https://www.jhipster.tech/documentation-archive/v8.5.0](https://www.jhipster.tech/documentation-archive/v8.5.0).
