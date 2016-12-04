package maitian.dubbo.netty;


import maitian.dubbo.dispatcher.ReplierDispatcher;
import maitian.dubbo.exchanger.ExhangeHandler;
import maitian.dubbo.utils.URL;

/**
 * Created by maitian13 on 2016/11/21.
 */
public class NettyTransporter implements Transporter{
    @Override
    public ChannelHandler bind(URL url, ExhangeHandler dispatcher) {
        return new NettyServer(url,dispatcher);
    }

    @Override
    public ChannelHandler connect(URL url) {
        return new NettyClient(url);
    }
}
