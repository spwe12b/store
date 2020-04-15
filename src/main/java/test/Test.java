package test;

import com.alibaba.druid.sql.visitor.functions.Char;
import org.joda.time.DateTime;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import test.Singlten.T;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Test {

    public static void main(String[] args)throws Exception {
        File file=new File("G:\\图片\\1.jpg");
        File file1=new File("G:\\图片\\5.jpg");
        BufferedInputStream bufferedInputStream=new BufferedInputStream(new FileInputStream(file));
        BufferedOutputStream bufferedOutputStream=new BufferedOutputStream(new FileOutputStream(file1));
        byte[] bytes=new byte[1024*100];
        FileInputStream in=new FileInputStream(file);
        FileOutputStream out=new FileOutputStream(file1);
        FileChannel inChannel=in.getChannel();
        FileChannel outChannel=out.getChannel();
       ByteBuffer buffer=ByteBuffer.allocate(100);
        while(true){
            int count=inChannel.read(buffer);
            if(count==-1){
                break;
            }
            buffer.flip();
            outChannel.write(buffer);
            buffer.clear();

        }
        in.close();
        out.close();
        inChannel.close();
        outChannel.close();




    }
    private static void copyFileUsingFileStreams(File source, File dest)
       throws IOException {
            InputStream input = null;
            OutputStream output = null;
            try {
                        input = new FileInputStream(source);
                       output = new FileOutputStream(dest);
                        byte[] buf = new byte[1024];
                        int bytesRead;
                        while ((bytesRead = input.read(buf))!= -1) {
                            System.out.println(bytesRead);

                                output.write(buf, 0, bytesRead);
                            }
                } finally {
                     input.close();
                     output.close();
                 }
        }
    public int test(){

        try{
            int i=1/0;
            //return 2;
        }catch (Exception e){
           return 2;
        }finally {
            return 1;
            //System.out.println("ha");
        }
       // return 1;

    }
    private static int i1;

    public static void test1(){
        final int i2;
       ThreadTest t=new ThreadTest();

        class in{
            public void test(){
                System.out.println();
                i1=1;
            }
        }
    }
    static ReentrantLock reentrantLock=new ReentrantLock();
    static Condition condition1=reentrantLock.newCondition();
    static Condition condition2=reentrantLock.newCondition();

    public static void to()throws Exception{
        reentrantLock.lock();

        System.out.println(Thread.currentThread().getName()+"doing");
        condition1.await();
       // reentrantLock.unlock();
    }
}
class MyThread implements Runnable{
    public MyThread(CountDownLatch countDownLatch){
        this.countDownLatch=countDownLatch;
    }
    private CountDownLatch countDownLatch;
    int tab1,tab2,tab3;
    @Override
    public void run() {
        try {
       switch (Thread.currentThread().getName()){
           case "thread1":tab1=new Random().nextInt(100);
               System.out.println(Thread.currentThread().getName()+"doing"+tab1);
               break;
           case "thread2":tab2=new Random().nextInt(100);
               System.out.println(Thread.currentThread().getName()+"doing"+tab2);
               break;
           case "thread3":tab3=new Random().nextInt(100);
               System.out.println(Thread.currentThread().getName()+"doing"+tab3);
       }
    }finally {
            if(countDownLatch!=null){
                countDownLatch.countDown();
            }
        }
        }
        public void sum(){
            System.out.println(tab1+tab2+tab3);
        }
}
class Solution1 {
    public  int  searchInsert(int[] nums, int target) {
        int right = nums.length - 1;
        int left = 0;
        int index;
        int mid=0;
        while(right > left) {
            mid = (right + left) / 2;
            if(nums[mid]==target){
                return mid;
            }
            if(target>nums[mid]){
                left=mid+1;
            }else{
                right=mid-1;
            }
        }
        if(target>nums[right]){
            return right+1;
        }else{
            return right;
        }

    }
}
