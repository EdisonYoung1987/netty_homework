package gupao.netty.homeWork.oj8k;

import com.sun.org.apache.bcel.internal.generic.NEW;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class A_StartThread {
    static class MyRunnable implements  Runnable{
        @Override
        public void run() {
            System.out.println("MyRunnable running:"+Thread.currentThread().getName());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("MyRunnable stop");
        }
    }

    static class MyThread extends  Thread{
        @Override
        public void run() {
            System.out.println("MyThread running:"+Thread.currentThread().getName());        }
    }

    static class MyCallable implements Callable<String> {
        @Override
        public String call() throws Exception {
            System.out.println("MyCallable running:"+Thread.currentThread().getName());
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }return "Mycalllabllllle";
        }
    }

    public static void main(String[] args) {
        MyRunnable myRunnable=new MyRunnable();
        new Thread(myRunnable).start();

        MyThread myThread=new MyThread();
        myThread.start();

        MyCallable myCallable=new MyCallable();
        FutureTask futureTask=new FutureTask(myCallable);
        new Thread(futureTask).start();
        try {
            System.out.println(futureTask.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
