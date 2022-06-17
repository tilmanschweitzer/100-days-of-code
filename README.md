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

## Day 11

Add test case with multiple stuck threads and try to extract meaningful information from the stack traces.

Insights

* Currently, the direction of the features is not clear, and I feel a little stuck with the next steps
* It should be possible add parameters to ignore the new statistic feature to be able to still compare the speed between implementations

## Day 12

Refactoring of the whole java implementation.
Allow to enable different handlers via commandline parameter to ignore costly but unnecessary calculations.
Split up the parser and the stuck thread handlers to meet to open-closed principle.

Insights

* The open-closed principle really helped to reason about the relationship between the parser and the handler
* The existing test cases helped a lot to find defects
* Tests should cover more lines

## Day 13

Use one StuckThreadHandler per file to avoid concurrency issues.
The new approach with the separation of the parser lead to a shared state in the handler instances.
This caused race conditions and ConcurrentModificationExceptions when started with the --async parameter.

Insights

* It's not necessary to make everything immutable, but Suppliers can help to provide new instances for every potentially concurrent operations.

## Day 14

Remove coupling to static Files functions allow tests without mocking static functions.
The implementation relied on the methods Files.readLines and Files.walk.
With this dependency to the global state of the file system it is hard to tests parts of the functionality.
Therefore, I added interfaces to remove the direct dependency to these static functions

## Day 15

Add tests to check how the parsers walk through the files.

## Day 16

Add heuristic to weight the code lines depending on the line.
Usually, code lines higher in the stack trace contain more meaningful information than the later framework or filter code.
Therefore, a weight based on the average line number of the code line can help to get more useful reports.

## Day 17

Add tests for StuckThread and refactor related code parts.

## Day 18

Extract core classes into separate maven module to create a stricter barrier between the parser, and the commandline interface.

## Day 19

Move print function to commandline app to separate interface.
Replace LogFileParserResult interface with a generic type to make the parser completely independent of the String output (`getPrintableResult`).

## Day 20

Add test setup with bats-core to verify commandline output of different implementations

## Day 21

Start with *sudoku-solver* as new challenge.
Implement first draft of a java application to parse sudoku data.
Use dataset under public domain with 9 million sudokus: https://www.kaggle.com/datasets/rohanrao/sudoku

## Day 22

Add validation methods to check the correctness of sudoku solutions as preparation for the backtracking implementation of the solver.

## Day 23

Add SudokuSolver interface and a backtracking implementation.
Implement an ExecutionTimer class to be able to measure the execution time and to quantify improvements.

## Day 24

Add first implementation DeductiveSudokuSolver which needs to be supported by a fallback solver for some sudokus, but it already speeds up the execution time.

## Idea Backlog

* Add tests for Node.js (and check out test libs for Go)
* Implementation in Java?
* Add statistics on the stack traces of the stuck threads to find similarities quickly
