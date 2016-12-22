package cn.edu.cuit.netty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class HelloClientHandler extends SimpleChannelInboundHandler<String> {

	private static Logger logger = LoggerFactory.getLogger(NettyServer.class);

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
		logger.info("client receive:" + msg);
	}
	
	@Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
		String msg = "1111";
        ctx.writeAndFlush(msg);
        logger.info("client send: "+ msg);
    }
	
	


	
}
