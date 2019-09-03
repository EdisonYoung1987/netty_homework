package gupao.netty.homeWork.oj8k;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class G_Semaphore {
    private static Semaphore semaphore=new Semaphore(4);

    static class User extends Thread{
        private  Semaphore semaphore;
        private String name;
        public User(Semaphore semaphore,String name){
            this.semaphore=semaphore;
            this.name=name;
        }

        @Override
        public void run() {
            Random random=new Random(System.currentTimeMillis());
            int time=random.nextInt(4)+4;
            for(;;) {
                try {
                    System.out.println(name+" 尝试获取资源");
                    semaphore.acquire();
                    break;
                }catch (InterruptedException e){
                    System.out.println(name+" 尝试获取资源被中断，继续获取资源");
                    continue;
                }
            }
            try {
                System.out.println(name+" 睡眠"+time+"秒");
                Thread.sleep(1000*time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(name+" 释放资源");
            semaphore.release();
        }
    }

    public static void main(String[] args) {
        for(int i=0;i<20;i++) {
            User user = new User(semaphore,"user" + i);
            user.start();
        }
    }
}
