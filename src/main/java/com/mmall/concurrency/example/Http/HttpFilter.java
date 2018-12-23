package com.mmall.concurrency.example.Http;

import lombok.extern.slf4j.Slf4j;

import java.util.logging.Filter;
import java.util.logging.LogRecord;

@Slf4j
public class HttpFilter implements Filter {
    @Override
    public boolean isLoggable(LogRecord record) {
        return false;
    }
}
