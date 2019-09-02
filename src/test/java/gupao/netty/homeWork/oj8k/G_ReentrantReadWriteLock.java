package gupao.netty.homeWork.oj8k;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
/**
 * 仿写一个可重入的读写锁，要点如下：
 * 1. 写排他 读共享
 * 2. 需要一个重入次数 一个持有线程 一个写等待队列
 * 3. 还需要一个当前读队列，因为写会阻塞写线程请求，以保证有线程在读时，不能写，保证一致性*/
public class G_ReentrantReadWriteLock {
    private  final ReentrantLock lock=new ReentrantLock(); //锁

    private  final Condition readCondition=lock.newCondition();//读condition
    private  final Condition writeCondition=lock.newCondition();//写condition
    private  int stat=0; //重入次数
    private  Thread exclusiveThread=null;//当前写线程
    private  int writeRequest=0; //等待写线程数量
    private  HashMap<Thread,Integer> readThreads=new HashMap<>(256);

    public  void getWriteLock() {
        System.out.println(Thread.currentThread().getName()+":尝试获取写锁");
        lock.lock();
        writeRequest++;
        for(;;) {
            while(readThreads.size()!=0){//有线程正在读，为保证一致性，阻塞写
                try {
                    System.out.println(Thread.currentThread().getName()+":有线程在读，进入await");
                    writeCondition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (stat == 0) {//无读写线程
                System.out.println(Thread.currentThread().getName()+":占有写锁");
                stat++;
                writeRequest--;
                exclusiveThread = Thread.currentThread();
                break;
            } else {
                if (exclusiveThread == Thread.currentThread()) {
                    System.out.println(Thread.currentThread().getName()+":重入写锁");
                    stat++;
                    writeRequest--;
                    break;
                } else {
                    try {
                        System.out.println(Thread.currentThread().getName()+":待其他写锁释放，进入await");
                        writeCondition.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        lock.unlock();
    }
    public void  releaseWriteLock() throws Exception{
        System.out.println(Thread.currentThread().getName()+":开始释放写锁");
        lock.lock();
        try {
            if (exclusiveThread != Thread.currentThread())
                throw new Exception(Thread.currentThread().getName()+"未持有锁");
            stat--;
            if (stat == 0) {
                exclusiveThread = null;
                if (writeRequest == 0) {
                    System.out.println(Thread.currentThread().getName()+":唤醒其他读锁");
                    readCondition.signalAll();
                }else {
                    System.out.println(Thread.currentThread().getName()+":唤醒其他写锁");
                    writeCondition.signalAll();//优先唤醒待写线程
                }
            }
        }finally {
            lock.unlock();
        }

    }

    public void getReadLock(){
        System.out.println(Thread.currentThread().getName()+":尝试获取读锁");
        lock.lock();
        Thread thread=Thread.currentThread();
        try{
            while(writeRequest!=0 || stat!=0 ) {//有待写、写线程 需阻塞
                System.out.println(Thread.currentThread().getName()+":不满足读条件，需await"+writeRequest+" "+stat);
                try {
                    readCondition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if(readThreads.containsKey(thread)){
                System.out.println(Thread.currentThread().getName()+":重入读锁");
                readThreads.put(thread,readThreads.get(thread)+1);
            }else{
                System.out.println(Thread.currentThread().getName()+":第一次获取读锁");
                readThreads.put(thread,1);
            }

        }finally {
            lock.unlock();
        }
    }

    public void releaseReadLock() throws Exception{
        System.out.println(Thread.currentThread().getName()+":释放读锁");

        lock.lock();
        Thread thread=Thread.currentThread();
        try{
            if(!readThreads.containsKey(thread)){
                throw new Exception(Thread.currentThread().getName()+"未持有读锁");
            }
            int c=readThreads.get(thread);
            if(c==1)
                readThreads.remove(thread);
            else
                readThreads.put(thread,c-1);

            if(readThreads.size()==0) {//已经没有在读线程
                System.out.println(Thread.currentThread().getName()+":唤醒写/读锁");
                writeCondition.signalAll();
                readCondition.signalAll();
            }
        }finally {
            lock.unlock();
        }
    }
}
