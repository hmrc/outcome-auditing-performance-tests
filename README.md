# outcome-auditing-performance-tests

Performance test suite for the `outcome-auditing` service, using [performance-test-runner](https://github.com/hmrc/performance-test-runner) under the hood.

## Pre-requisites

- Docker - to start MongoDB container
- [Service Manager 2 (sm2)](https://github.com/hmrc/sm2) installed and configured

### Services

If you don't have Mongo running locally, then startup via Docker container as follows:

```bash
  docker run --restart unless-stopped --name mongodb -p 27017:27017 -d percona/percona-server-mongodb:7.0 --replSet rs0
  docker exec -it mongodb mongosh --eval "rs.initiate();"
````

Start dependent microservices using the following shell script:
```shell
  ./start_services.sh
```

## Tests

### WARNING :warning:

Do **NOT** run a full performance test against staging from your local machine. Please [implement a new performance test job](https://confluence.tools.tax.service.gov.uk/display/DTRG/Practical+guide+to+performance+testing+a+digital+service#Practicalguidetoperformancetestingadigitalservice-SettingupabuildonJenkinstorunagainsttheStagingenvironment) and execute your job from the dashboard in [Performance Jenkins](https://performance.tools.staging.tax.service.gov.uk).

### Running local tests

Run smoke test (locally) using the following shell script:
```shell
  ./run_tests.sh
```

Run full performance test (locally) by passing the argument `false` to the script (to set `smoke` to `false`):

```bash
  ./run_tests.sh false
```

## Scalafmt

Check all project files are formatted as expected as follows:

```shell
  sbt scalafmtCheckAll scalafmtCheck
```

Format `*.sbt` and `project/*.scala` files as follows:

```shell
  sbt scalafmtSbt
```

Format all project files as follows:

```shell
  sbt scalafmtAll
```

## Logging

The default log level for all HTTP requests is set to `WARN`. Configure [logback.xml](src/test/resources/logback.xml) to update this if required.

## License

This code is open source software licensed under the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html").
