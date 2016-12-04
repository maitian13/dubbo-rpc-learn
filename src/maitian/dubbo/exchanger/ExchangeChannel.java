package maitian.dubbo.exchanger;

import maitian.dubbo.netty.ChannelHandler;
import maitian.dubbo.netty.Client;

import java.nio.channels.Channel;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by maitian13 on 2016/11/24.
 */
public class ExchangeChannel {
    private final ChannelHandler client;
    private static Map<ChannelHandler,ExchangeChannel> Channels=new HashMap<ChannelHandler,ExchangeChannel>();
    public ExchangeChannel(ChannelHandler client){
        this.client=client;
    }
    public void send(Object message){

    }
    public static ExchangeChannel getOrAddChannel(ChannelHandler ch) {
        if (ch == null) {
            return null;
        }
        ExchangeChannel ret = Channels.get(ch);
        if (ret == null) {
            ret = new ExchangeChannel(ch);
            if (ch.isConnected()) {
                Channels.put(ch,ret);
        }
        }
        return ret;
    }
    public MyFuture request(Object request){
        //int timeout=client.getURL().getPositiveParameter(Constants.TIMEOUT_KEY, Constants.DEFAULT_TIMEOUT);
        Request req=new Request();
        req.setmData(request);
        MyFuture future=new MyFuture(client,req);
        try{
            client.sent(req);
            //client.send(req);
        }catch (Exception e){
            System.out.println(e);
        }
        return future;
    }
}
