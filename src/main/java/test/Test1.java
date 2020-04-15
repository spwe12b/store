package test;

import test.Singlten.T;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class Test1 {
    public static void main(String[] args) throws Exception{
        ThreadPoolExecutor threadPoolExecutor=new ThreadPoolExecutor(35,50,
                1,TimeUnit.MINUTES,new ArrayBlockingQueue<>(10));
        for(int i=0;i<30;i++) {
            threadPoolExecutor.execute(new ThreadTest());


        }
        threadPoolExecutor.shutdown();
        Thread.sleep(1000);
        System.out.println(Demo.i);
    }

}
class Demo{
   // public static AtomicInteger i=new AtomicInteger(0);
   /* public static  Integer i=0;
    public  static void add()throws Exception{
             i++;
    }*/
   public static AtomicInteger i=new AtomicInteger(0);
    public  static void add()throws Exception{
        i.incrementAndGet();
    }
}
