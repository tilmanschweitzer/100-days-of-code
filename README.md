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


## Day 3

First draft to implement a concurrent Go version.

Insights

* Ran into issues with too many open file handles
* Ran into errors with semacquire when processing to many files parallel
* Output is still unordered

## Day 4 

### Part 1

Rework the concurrent Go implementation to create an ordered output and fix the other issues.

* The approach to order the output does not feel elaborate -> TODO: Read more Go code to find better approaches
* TODO: Refactor the code

### Part 2

Add synchronous python implementation as preparation for an async version tomorrow.


## Day 5

Add Node.js implementation using worker threads to speed up parsing each file.

Insights

* Reworked implementation with worker threads is not so far away from the Go speed
* Good example to understand the difference between I/Ointensive and CPU-intensive workload
* Helpful to compare different parallelization concepts between Go, Python and Node.js

## Day 6

Reorganize files and add basic JS tests.

Insights

* Currently, only jasmine seems to work with ES modules out-of-the-box.
* I should use TDD for further features

## Day 7

Add sync implementation in Java.

Insights

* Execution time is much better than I expected and comparable to Node.js and Python
* Implementing the basic version felt much smoother than I expected
* Parallelizing the sync implementation with Streams.parallel() much simpler than all previous parallel implementations (still unordered in the first attempt)

## Day 8

Add async and ordered implementation in Java.
Activate the async implementation with the parameter `--async`.
Split up the implementation into separate clases with a common interface and abstract class.

Insights

* Executors are very easy to handle and creating an ordered output from a multi-core parallelization feels much easy than in Node.js or Go

## Day 9

Add basic JUnit 5 test setup for Java

## Day 10

First attempt to parse stack traces to be able to generate statistical reports.

## Idea Backlog

* Add tests for Node.js (and check out test libs for Go)
* Implementation in Java?
* Add statistics on the stack traces of the stuck threads to find similarities quickly
