package gupao.netty.homeWork.oj8k;

import java.util.Random;

public class G_ReentrantReadWriteLock_Test {
    public static final G_ReentrantReadWriteLock lock=new G_ReentrantReadWriteLock();

    public static void main(final String[] args) {
        Random random=new Random(System.currentTimeMillis());
        for(int i=0;i<10;i++){
            int j=random.nextInt(2);
            if(j==0){//启动一个读线程
                 new Thread("读线程"+i){
                    @Override
                    public void run() {
                        lock.getReadLock();
                        Random random=new Random(System.currentTimeMillis());
                        int time=random.nextInt(3);
                        try {
                            sleep(1000*time);
                            lock.releaseReadLock();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }else//启动一个写线程
                new Thread("写线程"+i){
                    @Override
                    public void run() {
                        lock.getWriteLock();
                        Random random=new Random(System.currentTimeMillis());
                        int time=random.nextInt(5)+5;
                        try {
                            sleep(1000*time);
                            lock.releaseWriteLock();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
        }
    }
}
