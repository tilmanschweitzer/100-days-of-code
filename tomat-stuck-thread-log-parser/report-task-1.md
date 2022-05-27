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

    real 1m15.774s
    user 1m14.881s
    sys 0m0.794s

    real 1m15.375s
    user 1m14.391s
    sys 0m0.809s

    real 1m14.548s
    user 1m13.620s
    sys 0m0.778s


### Results for subset

Command: `fgrep -c notifyStuckThreadDetected tomcat-logs/catalina.2022-01*` (repeat 3 times)

    real 0m8.898s
    user 0m8.770s
    sys 0m0.100s

    real 0m8.903s
    user 0m8.806s
    sys 0m0.088s

    real 0m8.964s
    user 0m8.854s
    sys 0m0.096s


## Use `rg` (ripgrep)

Benchmark with `rg --sort-files`.

### Results for full dataset

Command: `rg -c --sort-files notifyStuckThreadDetected tomcat-logs/*` (repeat 3 times)

    real 0m1.088s
    user 0m0.356s
    sys 0m0.644s

    real 0m0.868s
    user 0m0.305s
    sys 0m0.560s

    real 0m0.864s
    user 0m0.304s
    sys 0m0.558s


### Results for subset

Command: `rg -c --sort-files notifyStuckThreadDetected tomcat-logs/catalina.2022-01*` (repeat 3 times)

    real 0m0.111s
    user 0m0.041s
    sys 0m0.068s

    real 0m0.110s
    user 0m0.040s
    sys 0m0.068s

    real 0m0.110s
    user 0m0.041s
    sys 0m0.068s


## Node.js `fs.readFileSync` (sync)

Run sequentially using `fs.readFileSync`.

### Results for full dataset

Command: `node task-1-sync.mjs tomcat-logs/` (repeat 3 times)

    real 0m14.550s
    user 0m12.236s
    sys 0m3.239s

    real 0m14.288s
    user 0m12.189s
    sys 0m3.163s

    real 0m14.373s
    user 0m12.344s
    sys 0m3.168s


### Results for subset

Command: `node task-1-sync.mjs tomcat-logs/ catalina.2022-01` (repeat 3 times)

    real 0m1.694s
    user 0m1.376s
    sys 0m0.409s

    real 0m1.705s
    user 0m1.382s
    sys 0m0.416s

    real 0m1.694s
    user 0m1.381s
    sys 0m0.398s


## Node.js `fsPromises.readFile` (parallel with `Promise.all`)

Run parallel, but wait for all results with `Promise.all`.

### Results for full dataset

Command: `node task-1-promise-all.mjs tomcat-logs/` (repeat 3 times)

    real 0m13.576s
    user 0m12.470s
    sys 0m5.748s

    real 0m13.624s
    user 0m12.452s
    sys 0m5.700s

    real 0m13.744s
    user 0m12.458s
    sys 0m5.712s


### Results for subset

Command: `node task-1-promise-all.mjs tomcat-logs/ catalina.2022-01` (repeat 3 times)

    real 0m1.595s
    user 0m1.375s
    sys 0m0.651s

    real 0m1.566s
    user 0m1.391s
    sys 0m0.635s

    real 0m1.569s
    user 0m1.390s
    sys 0m0.628s


## Node.js `fsPromises.readFile` (async/await)

Run in sequential order with async/await.

### Results for full dataset

Command: `node task-1-async-await.mjs tomcat-logs/` (repeat 3 times)

    real 0m14.926s
    user 0m12.960s
    sys 0m3.409s

    real 0m14.769s
    user 0m12.818s
    sys 0m3.398s

    real 0m14.824s
    user 0m12.865s
    sys 0m3.396s


### Results for subset

Command: `node task-1-async-await.mjs tomcat-logs/ catalina.2022-01` (repeat 3 times)

    real 0m1.780s
    user 0m1.513s
    sys 0m0.423s

    real 0m1.785s
    user 0m1.510s
    sys 0m0.435s

    real 0m1.778s
    user 0m1.508s
    sys 0m0.425s


## Node.js `fsPromises.readFile` (parallel)

Run parallel but ensure sequential output.

### Results for full dataset

Command: `node task-1-parallel.mjs tomcat-logs/` (repeat 3 times)

    real 0m13.644s
    user 0m12.468s
    sys 0m5.820s

    real 0m13.598s
    user 0m12.396s
    sys 0m5.719s

    real 0m13.798s
    user 0m12.440s
    sys 0m5.846s


### Results for subset

Command: `node task-1-parallel.mjs tomcat-logs/ catalina.2022-01` (repeat 3 times)

    real 0m1.609s
    user 0m1.410s
    sys 0m0.639s

    real 0m1.599s
    user 0m1.391s
    sys 0m0.638s

    real 0m1.562s
    user 0m1.405s
    sys 0m0.670s


