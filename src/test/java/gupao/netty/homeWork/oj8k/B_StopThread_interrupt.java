package gupao.netty.homeWork.oj8k;

public class B_StopThread_interrupt {
    static class MyThread extends  Thread{
        @Override
        public void run() {
            long start=System.currentTimeMillis();
            while(!Thread.currentThread().isInterrupted()){
                System.out.println("something");
            }
            System.out.println((System.currentTimeMillis()-start)/1000);
        }
    }

    public static void main(String[] args) {
        MyThread myThread=new MyThread();
        myThread.start();
        try {
            Thread.sleep(5000);
            myThread.interrupt();
            myThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
