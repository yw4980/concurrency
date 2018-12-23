package com.mmall.concurrency.example.immutable;


import com.google.common.collect.Maps;
import com.mmall.concurrency.annoations.NoThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * 用final修饰map后，map里的key，value是可以改变的
 */
@Slf4j
@NoThreadSafe
public class ImmutableExample1 {

    private final static Integer a=1;
    private final static String b="22";
    private final static Map<Integer,Integer> map= Maps.newHashMap();

    static {
        map.put(1,2);
        map.put(3,4);
        map.put(5,6);
    }

    public static void main(String[] args){
//        a=2;
//        b="3";
//        map=new HashMap();

        map.put(1,3);
        log.info("{}",map.get(1));
    }

    private void test(final int a){
//        a=1;
    }
}
