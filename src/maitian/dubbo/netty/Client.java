package maitian.dubbo.netty;


import maitian.dubbo.utils.URL;

/**
 * Created by maitian13 on 2016/11/21.
 */
public interface Client {
    URL getURL();
    void send(Object message);
    boolean isConnected();
}
