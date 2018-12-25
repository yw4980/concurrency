package com.mmall.concurrency.example.aqs;


import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Semaphore，
 * Semaphore是信号量，用于管理一组资源。
 * 其内部是基于AQS的共享模式，
 * AQS的状态表示许可证的数量，在许可证数量不够时，
 * 线程将会被挂起；而一旦有一个线程释放一个资源，
 * 那么就有可能重新唤醒等待队列中的线程继续执行。
 *
 *
 * 通俗点说就是：Semaphore主要用于控制当前活动线程数目，
 * 就如同停车场系统一般，而Semaphore则相当于看守的人，
 * 用于控制总共允许停车的停车位的个数，而对于每辆车来说就如同一个线程，
 * 线程需要通过acquire()方法获取许可，而release()释放许可。
 * 如果许可数达到最大活动数，那么调用acquire()之后，
 * 便进入等待队列，等待已获得许可的线程释放许可，从而使得多线程能够合理的运行。
 *
 *
 */
@Slf4j
public class SemaphoreExample2 {

    private final static int threadCount = 20;

    public static void main(String[] args) throws Exception{
        ExecutorService exec = Executors.newCachedThreadPool();

        final Semaphore semaphore = new Semaphore(3);

        for (int i = 0; i < threadCount; i++) {
            final int threadNum = i;
            exec.execute(()->{
                try{
                    semaphore.acquire(3); //获取多个许可
                    test(threadNum);
                    semaphore.release(3); //释放多个许可
                }catch (Exception e){
                    log.error("exception",e);
                }
            });
        }
        log.info("finish");
        exec.shutdown();
    }

    private static void test(int threadNum) throws Exception{

        log.info("{}",threadNum);
        Thread.sleep(1000);
    }
}
