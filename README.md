# 100 days of code

> Personal challenge to write some code every day for 100 days.

## Day 1

Runtime comparison of different ways to count the logs message `notifyStuckThreadDetected` in tomcat logs.

Insights

* A basic Node.js is faster than `grep` or `fgrep`.
* Currently `rg` (ripgrep) outperforms all approaches (so far). 
* Running things parallel is not faster than `fs.readFileSync` in this scenario.
* See: [report-task-1.md](tomat-stuck-thread-log-parser/report-task-1.md)

 
## Day 2

Repeat the example in a new language: Go

First steps (https://go.dev/doc/tutorial/getting-started)

    # Init module
    go mod init tilmanschweitzer/tomat-stuck-thread-log-parse

    # Execute
    go run .

Insights

* Some aspects like appending strings to arrays are more different as I expected
* Executing time is 2-3 times faster than with Node.js
* Possible next step: learn to write more idiomatic Go code or parallelize the execution.
