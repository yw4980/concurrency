package com.mmall.concurrency.example.singleton;

import com.mmall.concurrency.annoations.ThreadSafe;

/**
 * 饿汉模式
 * 单例实例在类装载进行创建
 * 缺点：容易造成性能浪费
 */
@ThreadSafe
public class SingletonExample6 {

    //私有构造函数
    private SingletonExample6(){

    }

    static {
        instance = new SingletonExample6();
    }
    //单例对象
    private static SingletonExample6 instance =  null;

    //静态工厂方法
    public static SingletonExample6 getInstance(){
        return instance;
    }

    public static void main(String[] args){
        System.out.println(getInstance().hashCode());
        System.out.println(getInstance().hashCode());

    }

}
