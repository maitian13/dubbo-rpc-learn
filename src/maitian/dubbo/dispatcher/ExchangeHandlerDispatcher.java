package maitian.dubbo.dispatcher;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import maitian.dubbo.exchanger.*;

/**
 * Created by maitian13 on 2016/11/24.
 */
public class ExchangeHandlerDispatcher implements ExhangeHandler {
    private final ReplierDispatcher replierDispatcher;
    //private final ChannelHandlerDispatcher handlerDispatcher;
    public ExchangeHandlerDispatcher(){
        this.replierDispatcher=new ReplierDispatcher();
    }
    public ExchangeHandlerDispatcher(Replier<?> replier){
        replierDispatcher = new ReplierDispatcher(replier);
        //handlerDispatcher = new ChannelHandlerDispatcher(handlers);
        //telnetHandler = new TelnetHandlerAdapter();
    }
    public <T> void addReplier(Class<T> type,Replier<T> replier){
        this.replierDispatcher.addReplier(type,replier);
    }
    @Override
    public Object reply(Object request) {
        return replierDispatcher.reply(request);
    }
    @Override
    public Response handleRequest(Request req) {
        Response res = new Response(req.getId());
        Object msg=req.getmData();
        Object result= reply(msg);
        res.setmResult(result);
        return res;
    }

    @Override
    public Object received(Object msg) {
        if(msg instanceof Request){
            return handleRequest((Request)msg);
        }
        if(msg instanceof Response){
            handleResponse((Response)msg);
        }
        return null;
    }

    public static void handleResponse(Response res){
        MyFuture.received(res);
    }
}
