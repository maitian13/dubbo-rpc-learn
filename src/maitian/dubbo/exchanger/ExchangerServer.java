package maitian.dubbo.exchanger;

import maitian.dubbo.netty.EndPoint;
import maitian.dubbo.netty.Server;

/**
 * Created by maitian13 on 2016/11/24.
 */
public class ExchangerServer {
    private final EndPoint server;
    public ExchangerServer(EndPoint server){
        if(server==null){
            throw new IllegalArgumentException("server==null");
        }
        this.server=server;
    }
}
