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

    real 1m14.340s
    user 1m13.491s
    sys 0m0.735s

    real 1m13.923s
    user 1m13.080s
    sys 0m0.722s

    real 1m14.663s
    user 1m13.789s
    sys 0m0.743s


### Results for subset

Command: `fgrep -c notifyStuckThreadDetected tomcat-logs/catalina.2022-01*` (repeat 3 times)

    real 0m9.040s
    user 0m8.945s
    sys 0m0.087s

    real 0m9.222s
    user 0m9.116s
    sys 0m0.095s

    real 0m9.264s
    user 0m9.160s
    sys 0m0.092s


## Use `rg` (ripgrep)

Benchmark with `rg --sort-files`.

### Results for full dataset

Command: `rg -c --sort-files notifyStuckThreadDetected tomcat-logs/*` (repeat 3 times)

    real 0m1.091s
    user 0m0.363s
    sys 0m0.676s

    real 0m1.024s
    user 0m0.363s
    sys 0m0.659s

    real 0m1.021s
    user 0m0.363s
    sys 0m0.655s


### Results for subset

Command: `rg -c --sort-files notifyStuckThreadDetected tomcat-logs/catalina.2022-01*` (repeat 3 times)

    real 0m0.129s
    user 0m0.049s
    sys 0m0.078s

    real 0m0.129s
    user 0m0.048s
    sys 0m0.079s

    real 0m0.117s
    user 0m0.044s
    sys 0m0.072s


## Node.js `fs.readFileSync` (sync)

Run sequentially using `fs.readFileSync`.

### Results for full dataset

Command: `node task-1-sync.mjs tomcat-logs/` (repeat 3 times)

    real 0m14.463s
    user 0m12.319s
    sys 0m3.286s

    real 0m14.450s
    user 0m12.286s
    sys 0m3.249s

    real 0m14.686s
    user 0m12.565s
    sys 0m3.226s


### Results for subset

Command: `node task-1-sync.mjs tomcat-logs/ catalina.2022-01` (repeat 3 times)

    real 0m1.784s
    user 0m1.416s
    sys 0m0.426s

    real 0m1.717s
    user 0m1.391s
    sys 0m0.417s

    real 0m1.719s
    user 0m1.396s
    sys 0m0.425s


## Node.js `fsPromises.readFile` (parallel with `Promise.all`)

Run parallel, but wait for all results with `Promise.all`.

### Results for full dataset

Command: `node task-1-promise-all.mjs tomcat-logs/` (repeat 3 times)

    real 0m13.840s
    user 0m12.629s
    sys 0m6.146s

    real 0m13.839s
    user 0m12.677s
    sys 0m5.908s

    real 0m13.921s
    user 0m12.703s
    sys 0m5.952s


### Results for subset

Command: `node task-1-promise-all.mjs tomcat-logs/ catalina.2022-01` (repeat 3 times)

    real 0m1.567s
    user 0m1.424s
    sys 0m0.686s

    real 0m1.606s
    user 0m1.423s
    sys 0m0.670s

    real 0m1.601s
    user 0m1.411s
    sys 0m0.674s


## Node.js `fsPromises.readFile` (async/await)

Run in sequential order with async/await.

### Results for full dataset

Command: `node task-1-async-await.mjs tomcat-logs/` (repeat 3 times)

    real 0m15.312s
    user 0m13.225s
    sys 0m3.560s

    real 0m15.225s
    user 0m13.192s
    sys 0m3.533s

    real 0m15.179s
    user 0m13.146s
    sys 0m3.513s


### Results for subset

Command: `node task-1-async-await.mjs tomcat-logs/ catalina.2022-01` (repeat 3 times)

    real 0m1.831s
    user 0m1.554s
    sys 0m0.441s

    real 0m1.819s
    user 0m1.538s
    sys 0m0.445s

    real 0m1.802s
    user 0m1.534s
    sys 0m0.425s


## Node.js `fsPromises.readFile` (parallel)

Run parallel but ensure sequential output.

### Results for full dataset

Command: `node task-1-parallel.mjs tomcat-logs/` (repeat 3 times)

    real 0m13.889s
    user 0m12.676s
    sys 0m5.874s

    real 0m13.958s
    user 0m12.733s
    sys 0m5.938s

    real 0m13.885s
    user 0m12.521s
    sys 0m5.963s


### Results for subset

Command: `node task-1-parallel.mjs tomcat-logs/ catalina.2022-01` (repeat 3 times)

    real 0m1.634s
    user 0m1.428s
    sys 0m0.660s

    real 0m1.645s
    user 0m1.434s
    sys 0m0.661s

    real 0m1.617s
    user 0m1.437s
    sys 0m0.694s


## Go(sync)

Run sequentially with a very basic go program

### Results for full dataset

Command: `go run . tomcat-logs/` (repeat 3 times)

    real 0m6.138s
    user 0m5.080s
    sys 0m1.857s

    real 0m6.038s
    user 0m5.109s
    sys 0m1.895s

    real 0m6.030s
    user 0m5.108s
    sys 0m1.891s


### Results for subset

Command: `go run . tomcat-logs/ catalina.2022-01` (repeat 3 times)

    real 0m0.938s
    user 0m0.770s
    sys 0m0.512s

    real 0m0.920s
    user 0m0.750s
    sys 0m0.476s

    real 0m0.909s
    user 0m0.758s
    sys 0m0.498s


