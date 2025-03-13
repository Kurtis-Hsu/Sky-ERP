package com.vireosci.sky.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/// 处理所有未处理异常的处理器
@ControllerAdvice
public class FinalExceptionHandler
{
    private static final Logger log = LoggerFactory.getLogger(FinalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ProblemDetail> unhandledException(Exception e)
    {
        unhandledQueue.offer(e);
        return new ResponseEntity<>(
                ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    private static final BlockingDeque<Exception> unhandledQueue = new LinkedBlockingDeque<>();

    static
    {
        Thread.ofVirtual()
                .name("unhandled-exception-handler")
                .start(() -> {
                    try
                    {
                        // noinspection InfiniteLoopStatement
                        while (true)
                            log.error("-- unhandled exception:", unhandledQueue.takeFirst());
                    }
                    catch (InterruptedException ignored) { log.error("异常打印线程中断"); }
                });
    }
}
