package com.mmall.concurrency.example.syncContainer;

import com.mmall.concurrency.annoations.NoThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;
import java.util.Vector;

/**
 * foreach、iterator做删除操作不要在遍历的时候进行，这样会导致ConcurrentModificationException异常
 * 因在遍历过程中，标记出需要删除的元素，在遍历结束后，进行删除操作
 * 如 以下
 */
@Slf4j
@NoThreadSafe
public class VectorExample3 {

    //failed java.util.ConcurrentModificationException
    private static void test1(Vector<Integer> v1){ //foreach
        for(Integer i:v1){
            if(i.equals(3)){
                v1.remove(i);
            }
        }
    }

    //failed java.util.ConcurrentModificationException
    private static void test2(Vector<Integer> v1){ // iterator
        Iterator<Integer> iterator = v1.iterator();
        while (iterator.hasNext()){
            Integer i = iterator.next();
            if(i.equals(3)){
                v1.remove(i);
            }
        }

    }

    //success
    private static void test3(Vector<Integer> v1){ // for
        for(int i=0;i<v1.size();i++){
            if(v1.get(i).equals(3)){
                v1.remove(i);
            }
        }
    }

    public static void main(String[] args){
        Vector<Integer> vector = new Vector<>();
        vector.add(1);
        vector.add(2);
        vector.add(3);
      //  vector.add(4);
  //      test1(vector);
       test2(vector);
        test3(vector);

    }
}
