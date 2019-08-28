package gupao.netty.homeWork.oj8k;

public class F_Synchronized {
    static class InnerClassCall1 extends Thread{
        private F_Synchronized f_synchronized;
        public InnerClassCall1(F_Synchronized f_synchronized){
            this.f_synchronized=f_synchronized;
        }
        @Override
        public void run() {
            f_synchronized.call1();
        }

    }
    static class InnerClassCall2 extends Thread{
        private F_Synchronized f_synchronized;
        public InnerClassCall2(F_Synchronized f_synchronized){
            this.f_synchronized=f_synchronized;
        }
        @Override
        public void run() {
            f_synchronized.call2();
        }

    }

    static class InnerClassCall3 extends Thread{
        private F_Synchronized f_synchronized;
        public InnerClassCall3(F_Synchronized f_synchronized){
            this.f_synchronized=f_synchronized;
        }
        @Override
        public void run() {
            f_synchronized.call3();
        }

    }

    public  void call1() {
        synchronized (F_Synchronized.class) {
                System.out.println(Thread.currentThread().getName()+" call1()... sleep 3 sec ");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+" call1() end ");
        }
    }

    public  void call2(){
        synchronized (this) {
            System.out.println(Thread.currentThread().getName()+" call2()... sleep 3 sec");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+" call2() end");
        }
    }
    public synchronized static void call3(){
       {
            System.out.println(Thread.currentThread().getName()+" call2()... sleep 3 sec");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+" call2() end");
        }
    }

    public static void main(String[] args) {
        F_Synchronized f_synchronized=new F_Synchronized();
        F_Synchronized f_synchronized2=new F_Synchronized();
        //1. 三个不同线程同时调用锁住类的方法
       /* Thread thread1=new InnerClassCall1(f_synchronized);
        Thread thread2=new InnerClassCall1(f_synchronized);
        Thread thread3=new InnerClassCall1(f_synchronized2);//不同对象也会被锁
        thread1.start();
        thread2.start();
        thread3.start();

        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        /*//2. 三个不同线程同时调用锁住对象的方法
        Thread thread4=new InnerClassCall2(f_synchronized);
        Thread thread5=new InnerClassCall2(f_synchronized);
        Thread thread6=new InnerClassCall2(f_synchronized2);//不同对象之间不会互斥
        thread4.start();
        thread5.start();
        thread6.start();

        try {
            thread4.join();
            thread5.join();
            thread6.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        //3. 三个不同线程同时调用锁住静态方法
        Thread thread7=new InnerClassCall3(f_synchronized);
        Thread thread8=new InnerClassCall3(f_synchronized);
        Thread thread9=new InnerClassCall3(f_synchronized2);
        thread7.start();
        thread8.start();
        thread9.start();

        try {
            thread7.join();
            thread8.join();
            thread9.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
