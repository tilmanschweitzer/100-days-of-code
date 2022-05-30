import fs from "fs";
import {countStuckThreadsInFile} from "./functions/count-stuck-threads-in-file.mjs";
import {fileNamesFromArgs} from "./functions/file-names-from-args.mjs";

const {folderName, fileNames} = fileNamesFromArgs();

fileNames.forEach(filename => {
    const file = fs.readFileSync(`${folderName}/${filename}`, 'utf-8');
    const stuckThreads = countStuckThreadsInFile(file)
    console.log(`${filename}:${stuckThreads}`);
});
