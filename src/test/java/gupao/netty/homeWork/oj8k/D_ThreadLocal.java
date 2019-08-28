package gupao.netty.homeWork.oj8k;

public class D_ThreadLocal {
    public static ThreadLocal<Integer> threadLocal=new ThreadLocal<Integer>(){
        @Override
        protected Integer initialValue() {
            return 0;
        }
    };
    public static void addOne(){
        int val=threadLocal.get();
        threadLocal.set(++val);
    }
    //thread unsafe
    public static int val=0;
    public static void addOne2(){
        val++;
    }

    static class MyRunnable implements  Runnable{
        @Override
        public void run() {
            for (int i = 0; i < 1000; i++){
                addOne();
                addOne2();
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("MyRunnable:"+threadLocal.get());

        }
    }
    static class MyRunnable2 implements  Runnable{
        @Override
        public void run() {
            for(int i=0;i<95533;i++){
                addOne();
                addOne2();
            }
            System.out.println("MyRunnable2:"+threadLocal.get());
        }
    }

    public static void main(String[] args) throws Exception {
        MyRunnable myRunnable=new MyRunnable();
        MyRunnable2 myRunnable2=new MyRunnable2();
        for(int i=0;i<10;i++)
            new Thread(myRunnable).start();
        new Thread(myRunnable2).start();

        Thread.sleep(5000);
        System.out.println("val="+val);
    }
}
