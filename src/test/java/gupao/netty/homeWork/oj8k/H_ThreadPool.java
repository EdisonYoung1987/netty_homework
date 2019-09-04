package gupao.netty.homeWork.oj8k;

import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class H_ThreadPool {
    static class MyRunnable implements Callable<String>{
        private String name;
        public MyRunnable(String name){
            this.name=name;
        }
        @Override
        public String call() throws Exception {
            System.out.println(name+"被调用");
            try {
                Random random=new Random(System.currentTimeMillis());
                Thread.sleep(2000+1000*(random.nextInt(3)));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(name+"执行完成");
            return "成功";
        }
    }
    public static void main(String[] args) throws Exception {
        MyRunnable myCallable=new MyRunnable("单独的");

        //第一种方式 new Thread(new FutureTask<v>(new Callable()))).start()+ futureTask.get();
        FutureTask<String> futureTask=new FutureTask<String>(myCallable);
        Thread thread=new Thread(futureTask);
        thread.start();//这种是无结果的
        System.out.println(futureTask.get());

        //第二种方式：ExecutorService继承类
        ThreadPoolExecutor poolExecutor=new ThreadPoolExecutor(10,75,20,TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(50),new MyThreadFactory("计算子任务"),
                new MyRejectedExecutionHandler());
        for(int i=0;i<200;i++) {
//            poolExecutor.submit(new MyRunnable("任务"+i));
            poolExecutor.submit(myCallable);
        }
        poolExecutor.shutdown();
    }

    private static class MyThreadFactory implements ThreadFactory {//直接将DefaultThreadFactory抄过来
        private static final AtomicInteger poolNumber = new AtomicInteger(1);
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        MyThreadFactory(String name) {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() :
                    Thread.currentThread().getThreadGroup();
            namePrefix = "pool-" +name+
                    poolNumber.getAndIncrement() +
                    "-thread-";
        }

        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r,
                    namePrefix + threadNumber.getAndIncrement(),
                    0);
            if (t.isDaemon())
                t.setDaemon(false);
            if (t.getPriority() != Thread.NORM_PRIORITY)
                t.setPriority(Thread.NORM_PRIORITY);
            return t;
        }
    }

    private static class MyRejectedExecutionHandler implements RejectedExecutionHandler {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            //如果什么都不做，和DiscardPolicy-直接丢弃没有区别
            System.out.println("丢弃");
            if(r instanceof Callable){

            }
        }
    }
}
