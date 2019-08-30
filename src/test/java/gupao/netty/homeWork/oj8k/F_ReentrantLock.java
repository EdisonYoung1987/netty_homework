package gupao.netty.homeWork.oj8k;

import java.util.concurrent.locks.ReentrantLock;

public class F_ReentrantLock {
    private static final ReentrantLock lock=new ReentrantLock();
    private static int num=0;

    static class MyThread extends Thread{
        @Override
        public void run() {
            lock.lock();
            try {
                for(int i=0;i<99999;i++) {
                    num=num+2;
                    /*if(i%5==0)
                        Thread.sleep(1);*/
                }
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) throws Exception{
        Thread[] threads=new Thread[1000];
        for(int i=0;i<1000;i++){
            Thread thread=new MyThread();
            thread.start();
            threads[i]=thread;
//            thread.join();
        }
        for(int i=0;i<1000;i++)
            threads[i].join();
        System.out.println("num="+num);
    }
}
