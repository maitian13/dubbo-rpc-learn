package maitian.dubbo.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelInboundHandlerAdapter;
import maitian.dubbo.exchanger.MyFuture;
import maitian.dubbo.exchanger.Request;
import maitian.dubbo.exchanger.Response;

/**
 * Created by maitian13 on 2016/11/24.
 */
public class ResultHandler extends ChannelInboundHandlerAdapter {//ChannelInboundHandlerAdapter
    private Object response;
    public Object getResponse(){
        return response;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        response=msg;
        MyFuture.received((Response)msg);
        //System.out.println("收到服务器相应:"+((Response)msg).getResult());
        ctx.close();
        //super.channelRead(ctx, msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("client exception");
        super.exceptionCaught(ctx, cause);
    }
}
