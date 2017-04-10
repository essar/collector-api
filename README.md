This is the Collector API

## Testing instructions

The simplest way to test the API is using the CombinedTestCollector class. This will create 3 collectors, register them with the API and push data via UDP to them.

* Run collector-api as a MuleSoft application.
* Run CombinedTestCollector as a Java application. If you wish you can override the default values.

---

`Usage:`
`CombinedTestCollector <hostname> <httpPort> <udpPort> <cycles> <delayMS> <parallel>`
`hostname:	remote API hostname or IP address`
`httpPort:	remote HTTP API port`
`udpPort:	remote data API port`
`cycles:		number of observations to send`
`delayMS:	delay between transmissions in milliseconds`
`parallel:	number of parallel Collectors to run`

* Once the collectors are running, query the /groups/testing endpoint to view aggregate data. Use can use an API client such as Postman or a web browser.
  http://localhost:8081/groups/testing
* You can also query individual collectors: [http://localhost:8081/collectors]
* or an individual collector: [http://localhost:8081/{collectorID}]

* The archive data will be written to the `archive` folder inside your project folder.
 