package com.example.logger_demo.logger;

/**
 * @author niffler on 24/09/24
 * @project logger-demo
 */
public class APILoggerContexHolder {

    private static final ThreadLocal<APILogger> apiLoggerThreadLocal = new ThreadLocal<>();
    public static void setLogger(APILogger logger) {
        apiLoggerThreadLocal.set(logger);
    }
    public static APILogger getLogger() {
        return apiLoggerThreadLocal.get();
    }
    public static void remove() {
        apiLoggerThreadLocal.remove();
    }
}