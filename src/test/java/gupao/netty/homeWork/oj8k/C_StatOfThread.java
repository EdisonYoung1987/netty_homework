package gupao.netty.homeWork.oj8k;

import java.util.concurrent.locks.LockSupport;

public class C_StatOfThread {
    /**
     * NEW : new Thread()
     * Runnable(Running) start() LockSupport.unpark(当前线程)等被唤醒 、 获取锁
     * Blocked:  Object.wait()返回(Object为synchronized持有的对象)-syncronized  进入lock池
     * Waiting:  Object.wait()(Object为synchronized持有的对象)-join()-LockSupport#park() 进入等待队列
     * Timed Waiting: sleep(x) Object.wait(xx) LockSupport.parkNanos(x)/parkUntil(x)
     * Terminate：异常或者执行完成
     * Condition.await()实际上目前只有AQS实现了，内部调用LockSupport.park()
     * */
    public static void main(String[] args) {
        Object object=new Object();
        synchronized (object) {
            try {
                object.wait(11);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        LockSupport.park();
        System.out.println("sss");
    }

}
