package com.mmall.concurrency.example.concurrent;

import com.mmall.concurrency.annoations.NoThreadSafe;
import com.mmall.concurrency.annoations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 并发容器：CopyOnWriteArrayList为线程安全类
 *
 * 使用CopyOnWriteMap需要注意两件事情：
 *
 * 　　1. 减少扩容开销。根据实际需要，初始化CopyOnWriteMap的大小，避免写时CopyOnWriteMap扩容的开销。
 *
 * 　　2. 使用批量添加。因为每次添加，容器每次都会进行复制，所以减少添加次数，可以减少容器的复制次数。
 */
@Slf4j
@ThreadSafe
public class CopyOnWriteArrayListExample {

    //请求总数
    public static int clientTotal=5000;

    //同时并发执行的线程数
    public static int threadTotal=200;

    private static List<Integer> list = new CopyOnWriteArrayList<Integer>();

    public static void main(String[] args) throws Exception{
        ExecutorService executorService= Executors.newCachedThreadPool();
        final Semaphore semaphore= new Semaphore(threadTotal);
        final CountDownLatch countDownLatch=new CountDownLatch(clientTotal);
        for (int i=0;i<clientTotal;i++){
            final Integer count=i;
            executorService.execute(()->{
                try{
                    semaphore.acquire();
                    update(count);
                    semaphore.release();
                }catch (Exception e){
                    log.error("exception",e);
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        executorService.shutdown();
       log.info("size:{}"+list.size());
    }

    private static void update(int i){
        list.add(i);
    }
}
