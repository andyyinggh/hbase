package cn.edu.cuit.netty;

import cn.edu.cuit.utils.SerializationUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 编码
 * @author Guanghua.Ying
 * @date 2016年12月22日
 *
 */
@SuppressWarnings("rawtypes")
public class Encoder extends MessageToByteEncoder {
	
	private Class<?> genericClass;

    public Encoder(Class<?> genericClass) {
        this.genericClass = genericClass;
    }

    @Override
    public void encode(ChannelHandlerContext ctx, Object in, ByteBuf out) throws Exception {
        if (genericClass.isInstance(in)) {
            byte[] data = SerializationUtil.serialize(in);
            out.writeInt(data.length);
            out.writeBytes(data);
        }
    }

}
