import fs from "fs";
import {countStuckThreadsInFile} from "./functions/count-stuck-threads-in-file.mjs";
import {fileNamesFromArgs} from "./functions/file-names-from-args.mjs";

const fsPromises = fs.promises;
const {folderName, fileNames} = fileNamesFromArgs();

async function readFiles(filenames) {
    for (const filename of filenames) {
        const file = await fsPromises.readFile(`${folderName}/${filename}`, 'utf-8');
        const stuckThreads = countStuckThreadsInFile(file);
        console.log(`${filename}:${stuckThreads}`);
    }
}

await readFiles(fileNames);
