package com.mmall.concurrency.example.syncContainer;

import com.mmall.concurrency.annoations.NoThreadSafe;
import com.mmall.concurrency.annoations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 同步容器：vector虽然为线程安全的类，但也有线程不安全的写法
 * 如 以下
 */
@Slf4j
@NoThreadSafe
public class VectorExample2 {

    private static Vector<Integer> vector = new Vector<>();

    public static void main(String[] args){
        while (true){
            for(int i=0;i<10;i++){
                vector.add(i);
            }

            /**
             * 当for循环访问到i=9时，假如线程1此时正在remove 下标为9这个元素
             * 而线程2正在get 下标为9的元素，此时就会发生线程不安全的问题
             */
            Thread thread1=new Thread(){
                public void run(){
                    for(int i=0;i<vector.size();i++){
                        vector.remove(i);
                    }
                }
            };

            Thread thread2=new Thread(){
                public void run(){
                    for(int i=0;i<vector.size();i++){
                        vector.get(i);
                    }
                }
            };
            thread1.start();
            thread2.start();
        }
    }
}
