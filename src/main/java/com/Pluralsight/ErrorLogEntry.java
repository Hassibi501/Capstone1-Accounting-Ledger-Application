package com.Pluralsight;

public class ErrorLogEntry {
    private final String level;
    private final String timeStamp;
    private final int linNumber;
    private final String issue;
    private final String expectedFormat;
    private final String rawLine;

    public ErrorLogEntry(String level, String timeStamp, int linNumber, String issue, String expectedFormat, String rawLine) {
        this.level = level;
        this.timeStamp = timeStamp;
        this.linNumber = linNumber;
        this.issue = issue;
        this.expectedFormat = expectedFormat;
        this.rawLine = rawLine;
    }


}
