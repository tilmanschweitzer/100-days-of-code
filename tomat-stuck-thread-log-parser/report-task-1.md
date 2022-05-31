# report-task-1.md

> Runtime comparison of different ways to count the logs message `notifyStuckThreadDetected` in tomcat logs.

## Environment

    CPU: Intel(R) Core(TM) i9-9880H CPU @ 2.30GHz
    Cores: 8

    Node v18.2.0
    fgrep (BSD grep) 2.5.1-FreeBSD


## Dataset

    ## Full dataset (tomcat-logs/)

    Size: 5.0G
    Lines: 28,953,182

    ## Subset (tomcat-logs/catalina.2022-01)

    Size: 594M
    Lines: 2,924,502


## Use `fgrep`

Baseline with `fgrep`.

### Results for full dataset

Command: `fgrep -c notifyStuckThreadDetected tomcat-logs/*` (repeat 3 times)

    real 1m11.851s
    user 1m11.198s
    sys 0m0.598s

    real 1m10.660s
    user 1m10.064s
    sys 0m0.568s

    real 1m11.105s
    user 1m10.483s
    sys 0m0.583s


### Results for subset

Command: `fgrep -c notifyStuckThreadDetected tomcat-logs/catalina.2022-01*` (repeat 3 times)

    real 0m8.559s
    user 0m8.476s
    sys 0m0.075s

    real 0m8.476s
    user 0m8.403s
    sys 0m0.070s

    real 0m8.460s
    user 0m8.389s
    sys 0m0.069s


## Use `rg` (ripgrep)

Benchmark with `rg --sort-files`.

### Results for full dataset

Command: `rg -c --sort-files notifyStuckThreadDetected tomcat-logs/*` (repeat 3 times)

    real 0m0.887s
    user 0m0.304s
    sys 0m0.535s

    real 0m0.824s
    user 0m0.300s
    sys 0m0.523s

    real 0m0.829s
    user 0m0.302s
    sys 0m0.525s


### Results for subset

Command: `rg -c --sort-files notifyStuckThreadDetected tomcat-logs/catalina.2022-01*` (repeat 3 times)

    real 0m0.105s
    user 0m0.040s
    sys 0m0.063s

    real 0m0.106s
    user 0m0.041s
    sys 0m0.064s

    real 0m0.106s
    user 0m0.040s
    sys 0m0.064s


## Node.js `fs.readFileSync` (sync)

Run sequentially using `fs.readFileSync`.

### Results for full dataset

Command: `node tstlp-js-sync.mjs tomcat-logs/` (repeat 3 times)

    real 0m13.663s
    user 0m11.811s
    sys 0m3.001s

    real 0m13.555s
    user 0m11.650s
    sys 0m3.022s

    real 0m13.511s
    user 0m11.611s
    sys 0m2.993s


### Results for subset

Command: `node tstlp-js-sync.mjs tomcat-logs/ catalina.2022-01` (repeat 3 times)

    real 0m1.596s
    user 0m1.310s
    sys 0m0.384s

    real 0m1.604s
    user 0m1.310s
    sys 0m0.385s

    real 0m1.598s
    user 0m1.307s
    sys 0m0.381s


## Node.js `fsPromises.readFile` (parallel with `Promise.all`)

Run parallel, but wait for all results with `Promise.all`.

### Results for full dataset

Command: `node tstlp-js-promise-all.mjs tomcat-logs/` (repeat 3 times)

    real 0m12.587s
    user 0m11.621s
    sys 0m5.277s

    real 0m12.608s
    user 0m11.636s
    sys 0m5.246s

    real 0m12.501s
    user 0m11.594s
    sys 0m5.308s


### Results for subset

Command: `node tstlp-js-promise-all.mjs tomcat-logs/ catalina.2022-01` (repeat 3 times)

    real 0m1.465s
    user 0m1.284s
    sys 0m0.625s

    real 0m1.474s
    user 0m1.299s
    sys 0m0.612s

    real 0m1.461s
    user 0m1.343s
    sys 0m0.634s


## Node.js `fsPromises.readFile` (async/await)

Run in sequential order with async/await.

### Results for full dataset

Command: `node tstlp-js-async-await.mjs tomcat-logs/` (repeat 3 times)

    real 0m13.845s
    user 0m12.041s
    sys 0m3.229s

    real 0m14.018s
    user 0m12.158s
    sys 0m3.260s

    real 0m13.864s
    user 0m12.122s
    sys 0m3.238s


### Results for subset

Command: `node tstlp-js-async-await.mjs tomcat-logs/ catalina.2022-01` (repeat 3 times)

    real 0m1.656s
    user 0m1.415s
    sys 0m0.406s

    real 0m1.626s
    user 0m1.386s
    sys 0m0.407s

    real 0m1.641s
    user 0m1.396s
    sys 0m0.420s


## Node.js `fsPromises.readFile` (parallel)

Run parallel but ensure sequential output.

### Results for full dataset

Command: `node tstlp-js-parallel.mjs tomcat-logs/` (repeat 3 times)

    real 0m12.575s
    user 0m11.604s
    sys 0m5.283s

    real 0m12.666s
    user 0m11.786s
    sys 0m5.329s

    real 0m12.600s
    user 0m11.725s
    sys 0m5.340s


### Results for subset

Command: `node tstlp-js-parallel.mjs tomcat-logs/ catalina.2022-01` (repeat 3 times)

    real 0m1.477s
    user 0m1.295s
    sys 0m0.626s

    real 0m1.479s
    user 0m1.304s
    sys 0m0.618s

    real 0m1.489s
    user 0m1.296s
    sys 0m0.633s


## Node.js with worker threads

Run parallel and split up workload to worker threads

### Results for full dataset

Command: `node tstlp-js-worker-threads.mjs tomcat-logs/` (repeat 3 times)

    real 0m3.815s
    user 0m19.995s
    sys 0m9.423s

    real 0m3.743s
    user 0m20.092s
    sys 0m9.538s

    real 0m3.691s
    user 0m20.100s
    sys 0m9.437s


### Results for subset

Command: `node tstlp-js-worker-threads.mjs tomcat-logs/ catalina.2022-01` (repeat 3 times)

    real 0m0.622s
    user 0m3.622s
    sys 0m1.756s

    real 0m0.630s
    user 0m3.630s
    sys 0m1.771s

    real 0m0.613s
    user 0m3.540s
    sys 0m1.738s


## Go(sync)

Run sequentially with a very basic go program

### Results for full dataset

Command: `go run . tomcat-logs/` (repeat 3 times)

    real 0m2.534s
    user 0m13.005s
    sys 0m6.554s

    real 0m2.235s
    user 0m12.481s
    sys 0m6.267s

    real 0m2.220s
    user 0m12.153s
    sys 0m6.005s


### Results for subset

Command: `go run . tomcat-logs/ catalina.2022-01` (repeat 3 times)

    real 0m0.522s
    user 0m1.743s
    sys 0m1.196s

    real 0m0.555s
    user 0m1.815s
    sys 0m1.280s

    real 0m0.556s
    user 0m1.766s
    sys 0m1.213s


