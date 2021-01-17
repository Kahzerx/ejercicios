package utils;

public enum LogLevel {
    INFORMATION("[INFO]"),
    ERROR("[ERROR]"),
    SUCCESS("[SUCCESS]");

    private final String level;

    LogLevel(String level) {
        this.level = level;
    }

    public String getLevel() {
        return level;
    }
}
