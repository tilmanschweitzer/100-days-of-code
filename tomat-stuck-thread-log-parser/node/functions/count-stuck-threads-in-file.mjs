function countStuckThreadsInFile(fileContent) {
    const lines = fileContent.split(/\r?\n/);
    let stuckThreads = 0;
    lines.forEach(line => {
        if (line.indexOf("notifyStuckThreadDetected") > 0) {
            stuckThreads++;
        }
    });
    return stuckThreads;
}


export {countStuckThreadsInFile}
