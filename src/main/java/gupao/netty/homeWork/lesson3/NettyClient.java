package gupao.netty.homeWork.lesson3;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

//TODO 客户端如何退出？ 参考src/main/java/gupao/netty/selfStudy/A_BootStrap/NettyClient.java
//TODO 客户端读写顺序怎么定的？
public class NettyClient implements Runnable {

	@Override
	public void run() {
		EventLoopGroup group=new NioEventLoopGroup(100);//最大100个线程去执行逻辑处理
		try{
			Bootstrap bootstrap=new Bootstrap(); //netty组装好的一个客户端启动器框架，用户只需要把各个零件插上去就行
			bootstrap.group(group);              //多线程reactor模型的reactor
			bootstrap.channel(NioSocketChannel.class); //工厂类ReflectiveChannelFactory：后续调用newChannel(z) 反射创建实例，需无参
			bootstrap.option(ChannelOption.TCP_NODELAY, true);//Map<ChannelOption<?>, Object> options的配置
			
			bootstrap.handler(new ChannelInitializer<SocketChannel>() {//初始化-一个特殊的ChannelInboundHandlerAdapter抽象类，
													//通过该初始化器可以将用户自定义的ChannelHandler对象加入到处理器链中
						@Override
						protected void initChannel(SocketChannel sc) throws Exception {
							ChannelPipeline channelPipeline=sc.pipeline(); //负责处理或拦截输入事件和输出操作的处理器链表
							
							//自定义长度解码器  编/解码  ： //这种消息格式是 指定消息长度+消息内容的基本方式，其他还有特殊分隔符、定长消息、文本中的回车换行符
							//参数：发送最大帧数 - 定义长度域位于发送的字节数组中的下标 - 长度域的长度 - 补偿值 - 跳过报文长度
							//第四个参数：假如希望长度值不包含消息头的长度，就可以用-4
							//第五个参数：定义的是读取消息时跳过报文的长度，这里是4，就跳过了前面4字节的长度内容
							channelPipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,0,4,0,4));
							channelPipeline.addLast("frameDecoder",new LengthFieldPrepender(4,false));//编码 消息添加4字节的长度内容 false-不包含报文长度内容的长度
							
							//字节码到String的转换
							channelPipeline.addLast("StringDecoder",new StringDecoder(CharsetUtil.UTF_8));
							channelPipeline.addLast("StringEncoder",new StringEncoder(CharsetUtil.UTF_8));
						    
							//实际的逻辑处理
							channelPipeline.addLast("handler",new Myclient());
						}
					});
			
			for(int i=0;i<3;i++){
				//创建一个channel实例，并注册到group上的一个EventLoop上
				//channel.eventLoop().execute(执行连接)-channel.connect(remoteAddress, connectPromise);
				ChannelFuture future=bootstrap.connect("127.0.0.1",8888).sync();//sync干嘛用的？ // ChannelFuture#sync() 等待阻塞成功 这里就是等待连接成功
				future.channel().writeAndFlush("hello "+Thread.currentThread().getName()+"-"+i);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		for(int i=0;i<2;i++){
			new Thread(new NettyClient(),"this is thread "+i).start();
		}
	}

}
