package com.mmall.concurrency.example.concurrent;

import com.mmall.concurrency.annoations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;
import java.util.concurrent.*;

/**
 * 并发容器：ConcurrentSkipListSet为线程安全类
 *
 * ConcurrentSkipListSet继承于AbstractSet。因此，它本质上是一个集合。
 * ConcurrentSkipListSet实现了NavigableSet接口。因此，ConcurrentSkipListSet是一个有序的集合。
 * ConcurrentSkipListSet是通过ConcurrentSkipListMap实现的。
 * ConcurrentSkipListSet包含一个ConcurrentNavigableMap对象m，而m对象实际上是ConcurrentSkipListMap的实例。
 * ConcurrentSkipListMap中的元素是key-value键值对
 * ConcurrentSkipListSet是集合，它只用到了ConcurrentSkipListMap中的key！
 *
 */
@Slf4j
@ThreadSafe
public class ConcurrentSkipListSetExample {

    //请求总数
    public static int clientTotal=5000;

    //同时并发执行的线程数
    public static int threadTotal=200;

    private static Set<Integer> Set = new ConcurrentSkipListSet<>();

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
