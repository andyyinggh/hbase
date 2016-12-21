package cn.edu.cuit.netty;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyClient {
	
	private static Logger logger = LoggerFactory.getLogger(NettyServer.class);
	
	private static String host= "127.0.0.1";
	private static int port = 8000;
	
	public static void main(String[] args) throws InterruptedException {
		EventLoopGroup group = new NioEventLoopGroup();
		
		
		try {
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(group);
			bootstrap.channel(NioSocketChannel.class);
			bootstrap.handler(new ChannelInitializer<SocketChannel>() {

				@Override
				public void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(new HelloClient());
				}
			});
			
			ChannelFuture future = bootstrap.connect(host, port).sync();
			logger.info("client 连接成功");
			future.channel().closeFuture().sync();
		} finally {
			group.shutdownGracefully();
		}
		
		

	}

}
