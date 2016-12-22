package cn.edu.cuit.netty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class HelloServer extends ChannelInboundHandlerAdapter {
	
	private static Logger logger = LoggerFactory.getLogger(NettyServer.class);
	
	@Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		logger.info("server收到client消息："+ msg.toString());
		
		String obj = "222";
		logger.info("server发送client消息：" + obj);
		ctx.writeAndFlush(obj);
    }

	@Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }
	
	@Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error("server caught exception", cause);
        ctx.close();
    }

}
