package com.mmall.concurrency.example.singleton;

import com.mmall.concurrency.annoations.NoThreadSafe;

/**
 * 懒汉模式
 * 单例实例在第一次使用时进行创建
 * 如果两个线程，同时通过单例模式，拿到了对象，同时对该对象进行了操作，这样就会导致线程不安全
 */
@NoThreadSafe
public class SingletonExample1 {

    //私有构造函数
    private SingletonExample1(){

    }

    //单例对象
    private static SingletonExample1 instance = null;

    //静态工厂方法
    public static SingletonExample1 getInstance(){
        if (instance == null){
            instance = new SingletonExample1();
        }
        return instance;
    }

}
