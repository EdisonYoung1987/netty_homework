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
    private static final ReentrantLock lock=new ReentrantLock(); //锁

    private static final Condition readCondition=lock.newCondition();//读condition
    private static final Condition writeCondition=lock.newCondition();//写condition
    private static int stat=0; //重入次数
    private static Thread exclusiveThread=null;//当前写线程
    private static int writeRequest=0; //等待写线程数量
    private static HashMap<Thread,Integer> readThreads=new HashMap<>(256);

    public static  void getWriteLock() {
        lock.lock();
        writeRequest++;
        for(;;) {
            while(readThreads.size()!=0){//有线程正在读，为保证一致性，阻塞写
                try {
                    writeCondition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (stat == 0) {//无读写线程
                exclusiveThread = Thread.currentThread();
                break;
            } else {
                if (exclusiveThread == Thread.currentThread()) {
                    stat++;
                    writeRequest--;
                    break;
                } else {
                    try {
                        writeCondition.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        lock.unlock();
    }
    public static void  releaseWriteLock() throws Exception{
        lock.lock();
        try {
            if (exclusiveThread != Thread.currentThread())
                throw new Exception("未持有锁");
            stat--;
            if (stat == 0) {
                exclusiveThread = null;
                if (writeRequest == 0)
                    readCondition.signalAll();
                else
                    writeCondition.signalAll();//优先唤醒待写线程
            }
        }finally {
            lock.unlock();
        }

    }

    public static void getReadLock(){
        lock.lock();
        Thread thread=Thread.currentThread();
        try{
            while(writeRequest!=0 || stat!=0 || !readThreads.containsKey(thread)) {//有待写、写线程 需阻塞 且 本身不在读
                try {
                    readCondition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if(readThreads.containsKey(thread)){
                readThreads.put(thread,readThreads.get(thread)+1);
            }else{
                readThreads.put(thread,1);
            }

        }finally {
            lock.unlock();
        }
    }

    public static void releaseReadLock() throws Exception{
        lock.lock();
        Thread thread=Thread.currentThread();
        try{
            if(!readThreads.containsKey(thread)){
                throw new Exception("未持有读锁");
            }
            int c=readThreads.get(thread);
            if(c==1)
                readThreads.remove(thread);
            else
                readThreads.put(thread,c-1);

            if(readThreads.size()==0)//已经没有在读线程
                writeCondition.signalAll();
        }finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        G_ReentrantReadWriteLock rwl=new G_ReentrantReadWriteLock();

    }
}
