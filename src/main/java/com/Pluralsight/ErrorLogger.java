package com.Pluralsight;

import java.time.LocalDateTime;


public class ErrorLogger {
    private String level;
    private LocalDateTime timeStamp;
    private int linNumber;
    private String issue;
    private String expectedFormat;
    private String rawLine;

    public ErrorLogger(String level, LocalDateTime timeStamp, int linNumber, String issue, String expectedFormat, String rawLine) {
        this.level = level;
        this.timeStamp = timeStamp;
        this.linNumber = linNumber;
        this.issue = issue;
        this.expectedFormat = expectedFormat;
        this.rawLine = rawLine;
    }
}
