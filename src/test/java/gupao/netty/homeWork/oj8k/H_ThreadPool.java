package gupao.netty.homeWork.oj8k;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

public class H_ThreadPool {
    static class MyRunnable implements Callable<String>{
        @Override
        public String call() throws Exception {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "成功";
        }
    }
    public static void main(String[] args) throws Exception {
        MyRunnable myCallable=new MyRunnable();

        //第一种方式 new Thread(new FutureTask<v>(new Callable()))).start()+ futureTask.get();
        FutureTask<String> futureTask=new FutureTask<String>(myCallable);
        Thread thread=new Thread(futureTask);
        thread.start();//这种是无结果的
        System.out.println(futureTask.get());

        //第二种方式：ExecutorService继承类
        ThreadPoolExecutor poolExecutor=new ThreadPoolExecutor();
    }
}
