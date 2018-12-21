package com.mmall.concurrency.example.publish;

import com.mmall.concurrency.annoations.NoThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * 发布对象时，线程不安全的类，因为此对象发布出去后，
 * 会经过其他线程修改，这样就会改变对对象域的值，导致
 * 对象里的值会被修改，从而导致线程不安全
 */
@Slf4j
@NoThreadSafe
public class UnsafePulish {

    private String[] states = {"a","b","c"};

    public String[] getStates(){
        return states;
    }

    public static void main(String[] args){
        UnsafePulish unsafePulish = new UnsafePulish();
        log.info("{}", Arrays.toString(unsafePulish.getStates()));

        unsafePulish.getStates()[0] = "d";
        log.info("{}", Arrays.toString(unsafePulish.getStates()));

    }
}
