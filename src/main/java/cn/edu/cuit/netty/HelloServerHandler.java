package cn.edu.cuit.netty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class HelloServerHandler extends SimpleChannelInboundHandler<String> {
	
	private static Logger logger = LoggerFactory.getLogger(NettyServer.class);

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
		logger.info("server reciver:" + msg);
		String str = "222";
		ctx.writeAndFlush(str);
		logger.info("server send: " + str);
	}
	

}
