package gupao.netty.homeWork.lesson4;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public final class Server {
	public static void main(String[] args) throws Exception {
		// Configure the server
		// 创建两个EventLoopGroup对象
		// 创建boss线程组用于服务端接受客户端的连接
		EventLoopGroup bossGroup = new NioEventLoopGroup(1);
		// 创建 worker 线程组用于进行 SocketChannel 的数据读写
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			// 创建 ServerBootstrap 对象
			ServerBootstrap b = new ServerBootstrap();
			// 设置使⽤的EventLoopGroup
			b.group(bossGroup, workerGroup)
			// 设置要被实例化的为 NioServerSocketChannel 类
					.channel(NioServerSocketChannel.class)
					// 设置 NioServerSocketChannel 的处理器
					.handler(new LoggingHandler(LogLevel.INFO))
					// 设置连⼊服务端的 Client 的 SocketChannel 的处理器
					.childHandler(new ServerInitializer());
			// 绑定端⼝，并同步等待成功，即启动服务端
			ChannelFuture f = b.bind(8888);
			// 监听服务端关闭，并阻塞等待
			f.channel().closeFuture().sync();
		} finally {
			// 优雅关闭两个 EventLoopGroup 对象
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
}