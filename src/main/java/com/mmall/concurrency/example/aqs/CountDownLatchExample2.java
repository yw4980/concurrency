package com.mmall.concurrency.example.aqs;


import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * CountDownLatch用于同步一个或多个任务，
 * 强制他们等待由其他任务执行的一组操作完成。
 * CountDownLatch典型的用法是将一个程序分为
 * N个互相独立的可解决任务，并创建值为N的
 * CountDownLatch。当每一个任务完成时，
 * 都会在这个锁存器上调用countDown，
 * 等待问题被解决的任务调用这个锁存器的await，
 * 将他们自己拦住，直至锁存器计数结束。
 *
 * 原子操作
 *
 *
 */
@Slf4j
public class CountDownLatchExample2 {

    private final static int threadCount = 200;

    public static void main(String[] args) throws Exception{
        ExecutorService exec = Executors.newCachedThreadPool();

        final CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            final int threadNum = i;
            exec.execute(()->{
                try{
                    test(threadNum);
                }catch (Exception e){
                    log.error("exception",e);

                }finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await(10, TimeUnit.MILLISECONDS);
        log.info("finish");
        exec.shutdown();
    }

    private static void test(int threadNum) throws Exception{
        Thread.sleep(100);
        log.info("{}",threadNum);
    }
}
