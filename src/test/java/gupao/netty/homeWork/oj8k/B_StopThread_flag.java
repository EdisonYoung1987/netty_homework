package gupao.netty.homeWork.oj8k;

public class B_StopThread_flag {
    private  static volatile   boolean stopflag=false; //

    static class MyThread extends  Thread{
        @Override
        public void run() {
            long start=System.currentTimeMillis();
            while(!stopflag){
//                System.out.println("something");
//                try {
//                    Thread.sleep(300);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            }
            System.out.println((System.currentTimeMillis()-start)/1000);
        }


    }

    public static void main(String[] args) {
        MyThread myThread=new MyThread();
        myThread.start();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        stopflag=true;

        try {
            myThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
