package maitian.dubbo.netty;

import maitian.dubbo.dispatcher.ReplierDispatcher;
import maitian.dubbo.exchanger.ExhangeHandler;
import maitian.dubbo.utils.URL;

/**
 * Created by maitian13 on 2016/11/21.
 */
public interface Transporter {
    ChannelHandler bind(URL url,ExhangeHandler dispatcher);
    ChannelHandler connect(URL url);
}
