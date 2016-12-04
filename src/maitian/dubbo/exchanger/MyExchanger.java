package maitian.dubbo.exchanger;

import maitian.dubbo.netty.ChannelHandler;
import maitian.dubbo.netty.Transporters;
import maitian.dubbo.utils.URL;

/**
 * Created by maitian13 on 2016/11/24.
 */
public class MyExchanger {
    public ExchangerClient connect(URL url){
        return new ExchangerClient(Transporters.connect(url));
    }
    public ExchangerServer bind(URL url,ExhangeHandler handler){
        return new ExchangerServer(Transporters.bind(url,handler));
    }
}
