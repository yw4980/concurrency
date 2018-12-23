package com.mmall.concurrency.example.commonUnsafe;

import com.mmall.concurrency.annoations.NoThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.logging.SimpleFormatter;

/**
 * SimpleDateFormat为线程不安全的日期类
 */
@Slf4j
@NoThreadSafe
public class DateFormateExample1 {

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");

    //请求总数
    public static int clientTotal=5000;

    //同时并发执行的线程数
    public static int threadTotal=200;

    public static void main(String[] args) throws Exception{
        ExecutorService executorService= Executors.newCachedThreadPool();
        final Semaphore semaphore= new Semaphore(threadTotal);
        final CountDownLatch countDownLatch=new CountDownLatch(clientTotal);
        for (int i=0;i<clientTotal;i++){
            executorService.execute(()->{
                try{
                    semaphore.acquire();
                    update();
                    semaphore.release();
                }catch (Exception e){
                    log.error("exception",e);
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        executorService.shutdown();
//        log.info("size:{}"+stringBuilder.length());

    }

    private static void update(){
        try{
            simpleDateFormat.parse("20180208");
        }catch (Exception e){
            log.error("parse exception",e);
        }

    }
}
