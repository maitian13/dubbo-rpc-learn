package maitian.dubbo.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

/**
 * Created by maitian13 on 2016/12/2.
 */
public class NettyHandler2 extends ChannelOutboundHandlerAdapter{
    private NettyServer server;
    public NettyHandler2(NettyServer server){
        this.server=server;
    }
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        System.out.println("outBound~");
        Object result=server.received(msg);
        //super.write(ctx, msg, promise);
        super.write(ctx, msg, promise);
    }
}
