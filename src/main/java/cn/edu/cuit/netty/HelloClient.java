package cn.edu.cuit.netty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class HelloClient extends ChannelInboundHandlerAdapter {

	private static Logger logger = LoggerFactory.getLogger(NettyServer.class);
	
	@Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		logger.info("收到server消息："+ msg.toString());
    }
	
	@Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
		String msg = "111";
		logger.info("client发送消息：" + msg);
        ctx.writeAndFlush(msg);
    }
	
	@Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }
	
	@Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }


	
}
