package maitian.dubbo.exchanger;

import maitian.dubbo.netty.ChannelHandler;
import maitian.dubbo.netty.Client;

/**
 * Created by maitian13 on 2016/11/24.
 */
public class ExchangerClient{
    private ChannelHandler client;
    private final ExchangeChannel channel;
    public ExchangerClient(ChannelHandler client){
        this.client=client;
        this.channel=new ExchangeChannel(client);
    }
    public MyFuture test(Object msg){
        System.out.println("flag1");
        return this.channel.request(msg);
    }
    public boolean isConnected(){
        return client.isConnected();
    }
    public MyFuture request(Object request){
        return channel.request(request);
    }
    public void close(){
        this.client.close();
    }
}
