package de.tilmanschweitzer.tstlp.handler;

public interface StuckThreadHandler {

    void startLogFile(String filename);

    void startStuckThread(String line);

    void lineInStuckThread(String line);

    void endStuckThread();

    LogFileParserResult getResult();
}
