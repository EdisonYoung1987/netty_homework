package gupao.netty.selfStudy.B_EventLoopGroup;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**一个定时启动且间隔执行任务
 * EventLoopGroup就是继承了该类*/
public class TestScheduledExecutorService {
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
			 
    public void beepForAnHour() throws Exception {
    
		final Runnable beeper = new Runnable() {
			public void run() {
				System.out.println("beep");
			}
		};//一个打印对象

		//一个5秒后触发，每次间隔1秒执行的任务
		System.out.println("scheduleAtFixedRate");
		final ScheduledFuture<?> beeperHandle = scheduler.scheduleAtFixedRate(beeper, 5, 1, TimeUnit.SECONDS);

		System.out.println("schedule");
		ScheduledFuture<?> beeperHandleStop=scheduler.schedule(new Runnable() {
			public void run() {
				System.out.println("去停止");
				beeperHandle.cancel(true);
			}
		}, 10, TimeUnit.SECONDS);//10秒后执行匿名对象去停止原来的定时任务
		
//		scheduler.shutdown();//只要前面设置的任务都执行了，不管有没有结束，就退出
//		scheduler.shutdownNow();//强制停止所有任务并退出
//		scheduler.awaitTermination(20, TimeUnit.SECONDS);//等待20秒就关闭
		
		try {
			scheduler.awaitTermination(20, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally {
			scheduler.shutdown();//如果超时都没关闭 就直接强制关掉
		}
//		while(!beeperHandle.isDone()) {//感觉这种方式好点
//			Thread.sleep(2);
//		}
//		beeperHandleStop.cancel(true);
//		scheduler.shutdown();
    }
	public static void main(String[] args) throws Exception {
		System.out.println("线程="+Thread.currentThread().getName()+":"+Thread.currentThread().getId());
		TestScheduledExecutorService test=new TestScheduledExecutorService();
		test.beepForAnHour();

	}

}
