package maitian.dubbo.dispatcher;

import maitian.dubbo.exchanger.ExchangeChannel;
import maitian.dubbo.netty.ChannelHandler;

/**
 * Created by maitian13 on 2016/11/24.
 */
public interface Replier<T> {
    Object reply(T request);
}
