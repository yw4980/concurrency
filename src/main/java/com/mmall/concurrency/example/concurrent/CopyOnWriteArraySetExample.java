package com.mmall.concurrency.example.concurrent;

import com.mmall.concurrency.annoations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

/**
 * 并发容器：CopyOnWriteArraySet为线程安全类
 *
 * CopyOnWriteArraySet具有以下特性：
 * 1. 它最适合于具有以下特征的应用程序：Set 大小通常保持很小，只读操作远多于可变操作，需要在遍历期间防止线程间的冲突。
 * 2. 它是线程安全的。
 * 3. 因为通常需要复制整个基础数组，所以可变操作（add()、set() 和 remove() 等等）的开销很大。
 * 4. 迭代器支持hasNext(), next()等不可变操作，但不支持可变 remove()等 操作。
 * 5. 使用迭代器进行遍历的速度很快，并且不会与其他线程发生冲突。在构造迭代器时，迭代器依赖于不变的数组快照。
 *
 *
 */
@Slf4j
@ThreadSafe
public class CopyOnWriteArraySetExample {

    //请求总数
    public static int clientTotal=5000;

    //同时并发执行的线程数
    public static int threadTotal=200;

    private static Set<Integer> Set = new CopyOnWriteArraySet<Integer>();

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
       log.info("size:{}"+Set.size());
    }

    private static void update(int i){
        Set.add(i);
    }
}
