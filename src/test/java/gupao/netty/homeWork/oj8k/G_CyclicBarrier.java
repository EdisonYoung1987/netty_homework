package gupao.netty.homeWork.oj8k;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class G_CyclicBarrier {
    static class Mythread extends Thread{
        private CyclicBarrier cyclicBarrier;
        public Mythread(CyclicBarrier cyclicBarrier) {
            this.cyclicBarrier=cyclicBarrier;
        }

        @Override
        public void run() {
            String name=Thread.currentThread().getName();
                System.out.println(name+" 执行任务开始");
                Random random=new Random(System.currentTimeMillis());
//                Thread.sleep((random.nextInt(10)+2)*1000);
                System.out.println(name+" 进入await()");
                while(true) {
                    try {
                        cyclicBarrier.await();
                        break;
                    } catch (InterruptedException e) {
                        System.out.print(name+"被中断: ");
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        System.out.print(name+"被破坏: ");
                        e.printStackTrace();
                        synchronized (cyclicBarrier){
                            if(cyclicBarrier.isBroken()) {
                                System.out.print(name+"执行重置 ");
                                cyclicBarrier.reset();
                            }
                        }
                    }
                }
                System.out.println(name+" await()返回");

        }
    }

    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier=new CyclicBarrier(1000);
        for(int i=0;i<1000;i++){
            Thread t=new Mythread(cyclicBarrier);
            t.start();
            if(i==998) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                t.interrupt();
            }
        }
    }
}
