package maitian.dubbo.exchanger;

import io.netty.channel.ChannelHandler;

/**
 * Created by maitian13 on 2016/11/24.
 */
public interface ExhangeHandler {
    Object reply(Object request);
    Response handleRequest(Request request);
    Object received(Object request);
}
