package maitian.dubbo.utils;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import maitian.dubbo.utils.ByteObjConverter;

/**
 * Created by maitian13 on 2016/12/2.
 */
public class NettyEncoder extends MessageToByteEncoder<Object> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Object o, ByteBuf byteBuf) throws Exception {
        byte[] data= ByteObjConverter.objectToByte(o);
        byteBuf.writeBytes(data);
        System.out.println("here encode");
        ctx.flush();

    }
}
