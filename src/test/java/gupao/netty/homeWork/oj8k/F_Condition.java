package gupao.netty.homeWork.oj8k;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
//其实应该仿写linkedblockingqueue
public class F_Condition  {
   public static  int num=0;
   public static final ReentrantLock lock=new ReentrantLock();
   public static final Condition condition=lock.newCondition();
   public static final Integer MAX=100;

    //定义三个子任务 需要交替执行 一个对奇数+1，一个对偶数+1，一个对10的倍数+5
   static class MyRunnable1 implements Runnable{
        private String name;
        public MyRunnable1(String name){
            this.name=name;
        }
        @Override
        public void run() {
            System.out.println(name+" start... ");
            try {
                Lock lock = F_Condition.lock;
                Condition condition = F_Condition.condition;
                lock.lock();
                while(true) {
                    //避免被唤醒的时候发现不是奇数
                    while (num % 2 != 1) {
                        if(num==MAX) {
                            condition.signalAll();
                            return;
                        }
                        System.out.println(name + "不是奇数，进入睡眠:" + num);
                        condition.await(); //释放锁
                        System.out.println(name + "被唤醒，检查是否为奇数或已到达最大值：" + num);

                    }
                    System.out.println("是奇数，加一");
                    if (num == MAX) {//达到最大值就退出
                        System.out.println(name + " 已达到最大值：" + num);
                        condition.signalAll();
                        return;
                    }
                    num++;
                    condition.signalAll();
                    condition.await();
                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        }
    }
    static class MyRunnable2 implements Runnable{
        private String name;
        public MyRunnable2(String name){
            this.name=name;
        }
        @Override
        public void run() {
            System.out.println(name+" start... ");
            try {
                Lock lock=F_Condition.lock;
                Condition condition=F_Condition.condition;
                lock.lock();

                while(true) {
                    //避免被唤醒的时候发现不是奇数
                    while (num % 2 != 0) {
                        if(num==MAX) {
                            condition.signalAll();
                            return;
                        }
                        System.out.println(name + "不是偶数，进入睡眠:" + num);
                        condition.await(); //释放锁
                        System.out.println(name + "被唤醒，检查是否为偶数或已到达最大值：" + num);
                    }
                    System.out.println("是偶数，加一");
                    if (num == MAX) {//达到最大值就退出
                        System.out.println(name + " 已达到最大值：" + num);
                        condition.signalAll();
                        return;
                    }
                    num++;
                    condition.signalAll();
                    condition.await();
                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        Thread t1=new Thread(new MyRunnable1("[奇数加一线程1]"));
        Thread t2=new Thread(new MyRunnable1("[奇数加一线程2]"));
        Thread t3=new Thread(new MyRunnable2("[偶数加一线程1]"));
        Thread t4=new Thread(new MyRunnable2("[偶数加一线程2]"));

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        try {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(num);
    }
}
