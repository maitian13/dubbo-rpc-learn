package maitian.dubbo.utils;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import maitian.dubbo.utils.ByteBufToBytes;
import maitian.dubbo.utils.ByteObjConverter;

import java.util.List;

/**
 * Created by maitian13 on 2016/12/2.
 */
public class NettyDecoder extends ByteToMessageDecoder{

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> list) throws Exception {
        ByteBufToBytes read=new ByteBufToBytes();
        Object obj= ByteObjConverter.byteToObject(read.read(in));
        System.out.println("here decode");
        list.add(obj);
    }
}
