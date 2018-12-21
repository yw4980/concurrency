package com.mmall.concurrency;

import com.mmall.concurrency.annoations.NoThreadSafe;
import com.mmall.concurrency.annoations.NotRecommend;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

@Slf4j
@NoThreadSafe
public class ConcurrencyTest {

    //请求总数
    public static int clientTotal=1000;

    //同时并发执行的线程数
    public static int threadTotal=50;

    public static int count=0;

    public static void main(String[] args) throws Exception{
        ExecutorService executorService= Executors.newCachedThreadPool();
        final Semaphore semaphore= new Semaphore(threadTotal);
        final CountDownLatch countDownLatch=new CountDownLatch(clientTotal);
        for (int i=0;i<clientTotal;i++){
            executorService.execute(()->{
                try{
                    semaphore.acquire();
                    add();
                    semaphore.release();
                }catch (Exception e){
                    System.out.println("exception"+e);
                    //log.error("exception",e);
                }
                countDownLatch.countDown();
            });
            countDownLatch.await();
            executorService.shutdown();
            System.out.println("count:{}"+count);
        }
    }

    private static void add(){
        count++;
    }
}

