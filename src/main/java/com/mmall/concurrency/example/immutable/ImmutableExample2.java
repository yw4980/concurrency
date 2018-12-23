package com.mmall.concurrency.example.immutable;


import com.google.common.collect.Maps;
import com.mmall.concurrency.annoations.NoThreadSafe;
import com.mmall.concurrency.annoations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.Map;

/**
 * 用Collections里的unmodifiableMap()方法修饰map后，map里的key，value是就不可以改变了，
 * 这样就保证了线程安全
 */
@Slf4j
@ThreadSafe
public class ImmutableExample2 {


    private  static Map<Integer,Integer> map= Maps.newHashMap();

    static {
        map.put(1,2);
        map.put(3,4);
        map.put(5,6);
        map = Collections.unmodifiableMap(map);
    }

    public static void main(String[] args){

        map.put(1,3);
        log.info("{}",map.get(1));
    }
}
