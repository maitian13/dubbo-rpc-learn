package maitian.dubbo.netty;

import com.sun.xml.internal.bind.v2.model.core.ClassInfo;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import maitian.dubbo.dispatcher.ReplierDispatcher;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by maitian13 on 2016/11/21.
 */
public class NettyHandler extends ChannelInboundHandlerAdapter {//ChannelInboundHandlerAdapter ChannelDuplexHandler
    private NettyServer server;
    public NettyHandler(NettyServer server){
        this.server=server;
    }
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("Inbound~");
        Object result=server.received(msg);
        ctx.write(result);
        ctx.flush();
        System.out.println("flushed!");
        super.channelRead(ctx,msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
