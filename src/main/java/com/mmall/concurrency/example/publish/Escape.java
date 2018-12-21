package com.mmall.concurrency.example.publish;

import com.mmall.concurrency.annoations.NoThreadSafe;
import com.mmall.concurrency.annoations.NotRecommend;
import lombok.extern.slf4j.Slf4j;

/**
 * 当InnerClass这个内部类的对象还没构造完成，就被其他线程所见，
 * 此种方式为不安全的，对象逸出了
 *
 */
@Slf4j
@NoThreadSafe
@NotRecommend
public class Escape {

    private int thisCanBeEscape = 0;

    public Escape(){
        new InnerClass();
    }

    private class InnerClass{
        public InnerClass(){
            log.info("{}",Escape.this.thisCanBeEscape);
        }
    }

    public static void main(String[] args){
        new Escape();
    }
}
