package com.dev.log.common.log;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.zalando.logbook.Correlation;
import org.zalando.logbook.HttpLogWriter;
import org.zalando.logbook.Precorrelation;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AccessLogWriter implements HttpLogWriter  {

@Override
public void write(Precorrelation precorrelation, String request) throws IOException {
    if(log.isInfoEnabled()) {
        log.info("############################ request: {}", request);
    }
}

@Override
public void write(Correlation correlation, String response) throws IOException {
    if(log.isInfoEnabled()) {
        log.info("############################ response: {}", response);
    }
}
}