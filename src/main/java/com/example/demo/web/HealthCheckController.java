package com.example.demo.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.TreeMap;

@RestController
public class HealthCheckController {

    @Value("${server.env:unknown}")
    private String env;

    @Value("${server.port:8080}")
    private String serverPort;

    @Value("${server.serverAddress:0.0.0.0}")
    private String serverAddress;

    @Value("${serverName:app}")
    private String serverName;

    @GetMapping("/hc")
    public ResponseEntity<?> healthCheck() {
        Map<String, String> res = new TreeMap<>();
        res.put("serverName", serverName);
        res.put("serverAddress", serverAddress);
        res.put("serverPort", serverPort);
        res.put("env", env);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/env")
    public ResponseEntity<String> getEnv() {
        return ResponseEntity.ok(env);
    }
}
