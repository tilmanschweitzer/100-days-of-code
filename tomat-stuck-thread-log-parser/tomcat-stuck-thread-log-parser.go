package main

import (
    "bufio"
    "fmt"
    "log"
    "os"
    "strings"
    "sync"
)


type Result struct {
    filename string
    stuckThreads int
}

func main() {
    filenames := filenamesFromArgs()

    ch := make(chan Result, len(filenames))
    wg := sync.WaitGroup{}

    for _, filename := range filenames {
        wg.Add(1)

        go countStuckThreadsInFileAsync(filename, ch, &wg)
    }

    wg.Wait()
    close(ch)

    for result := range ch {
       fmt.Printf("%s:%d\n", result.filename, result.stuckThreads)
    }
}

func countStuckThreadsInFile(filename string) (int) {
    file, err := os.Open(filename)
    if err != nil {
        log.Fatal(err)
    }
    defer file.Close()

    counter := 0

    scanner := bufio.NewScanner(file)

    for scanner.Scan() {
        if strings.Contains(scanner.Text(), "notifyStuckThreadDetected") {
            counter++
        }
    }

    if err := scanner.Err(); err != nil {
        log.Fatal(err)
    }

    file.Close()
    return counter
}

func countStuckThreadsInFileAsync(filename string, ch chan Result, wg *sync.WaitGroup) {
    countedStuckThreads := countStuckThreadsInFile(filename)  // Send a signal; value does not matter.

    ch <- Result{filename, countedStuckThreads}

    // let the wait group know we finished
    wg.Done()
}

func filenamesFromArgs() ([]string) {
    args := os.Args[1:]

    if len(args) < 1 {
        fmt.Println("Error: No folder given")
        os.Exit(1)
    }

    folder := args[0]
    prefix := valueAtIndexOrDefault(args, 1, "")

    files, err := os.ReadDir(folder)
    if err != nil {
        log.Fatal(err)
    }

    var filenames []string

    for _, file := range files {
        if strings.Contains(file.Name(), prefix) {
            filenames = append(filenames, folder + "/" + file.Name())
        }
    }

    return filenames
}

func valueAtIndexOrDefault(args []string, index int, defaultValue string) (string) {
    if len(args) > index {
        return args[index]
    }
    return defaultValue
}
