package com.example.logger_demo.controller;

import com.example.logger_demo.logger.APILogger;
import com.example.logger_demo.logger.APILoggerContexHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

/**
 * @author niffler on 24/09/24
 * @project logger-demo
 */

@RestController
public class HomeController {

    @GetMapping("/")
    public ResponseEntity<String> HealthCheck() {
        return ResponseEntity.ok("I'm running..!!");
    }

    @GetMapping("/v1/test")
    public ResponseEntity<Map> getLogger() throws InterruptedException {
        APILogger logger = APILoggerContexHolder.getLogger();
        logger.add("Method-started", "test");
        logger.add("Hello", "World");
        Thread.sleep(5000);
        logger.startTask("NestedTask");
        Thread.sleep(2000);
        logger.add("Task", 1);
        logger.startTask("NestedTask1");
        Thread.sleep(3000);
        logger.add("Task", 2);
//        logger.endTask();
//        Thread.sleep(1000);
        logger.endTask();
        Thread.sleep(2000);
//        logger.endTask();
//        logger.endTask();
//        logger.endTask();
        logger.logSuccess(HttpStatus.OK.value());
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<>(logger.getNestedTaskLogs().peek(), headers, HttpStatus.OK);
    }
}