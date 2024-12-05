package com.example.logger_demo.logger;

import lombok.Data;
import java.util.Map;
import java.util.UUID;
import java.util.Stack;
import java.util.HashMap;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

/**
 * @author niffler on 24/09/24
 * @project logger-demo
 */

@Slf4j
@Data
public class APILogger {

    private Stack<Map<String, Object>> nestedTaskLogs = new Stack<>();
    private final Gson gson = new Gson();

    public APILogger(String path, String requestId) {
        Map<String, Object> allLogData = new HashMap<>();
        allLogData.put("#_path", path);
        allLogData.put("$", "root");
        allLogData.put("#_requestId", StringUtils.hasLength(requestId) ? requestId : UUID.randomUUID().toString());
        allLogData.put("$_startAt", System.currentTimeMillis());
        nestedTaskLogs.push(allLogData);
    }

    public void add(String key, Object value) {
        nestedTaskLogs.peek().put(key, value);
    }

    public void startTask(String taskName) {
        long currentTimeMillis = System.currentTimeMillis();
        Map<String, Object> map = new HashMap<>();
        map.put("$", taskName);
        map.put("$_startAt", currentTimeMillis);
        nestedTaskLogs.push(map);
    }

    public boolean endTask() {
        if (nestedTaskLogs.size() > 1) {
            Map<String, Object> map = nestedTaskLogs.pop();
            String taskName = calculateExecutionTime(map);
            add(taskName, map);
            return true;
        }
        return false;
    }

    private static String calculateExecutionTime(Map<String, Object> map) {
        String taskName = (String) map.get("$");
        long currentTimeMillis = System.currentTimeMillis();
        long totalTimeMillis = currentTimeMillis - ((long) map.get("$_startAt"));
        map.put("$_endAt", currentTimeMillis);
        map.put("$_TotalTimeTaken", totalTimeMillis);
        return taskName;
    }

    public void logSuccess(int statusCode) {
        while(endTask());
        add("reason", "success");
        add("status", statusCode);
        calculateExecutionTime(nestedTaskLogs.peek());
        log.info(gson.toJson(nestedTaskLogs.peek()));
    }

    public void logError(String error, int statusCode, Throwable e) {
        add("reason", error);
        add("status", statusCode);
        if (e != null) {
            add("$_exception", e.getMessage());
        }
        while(endTask());
        calculateExecutionTime(nestedTaskLogs.peek());
        log.info(gson.toJson(nestedTaskLogs.peek()));
    }

    public void logError(String error, int statusCode) {
        logError(error, statusCode, null);
    }

//    private void add(Map<String, String> map, String key, Map value) {
//        map.put(key, value.toString());
//    }

}