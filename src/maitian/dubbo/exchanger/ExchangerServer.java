package maitian.dubbo.exchanger;

import maitian.dubbo.netty.ChannelHandler;
import maitian.dubbo.netty.Server;

/**
 * Created by maitian13 on 2016/11/24.
 */
public class ExchangerServer {
    private final ChannelHandler server;
    public ExchangerServer(ChannelHandler server){
        if(server==null){
            throw new IllegalArgumentException("server==null");
        }
        this.server=server;
    }
}
