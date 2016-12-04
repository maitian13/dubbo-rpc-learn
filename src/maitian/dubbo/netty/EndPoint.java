package maitian.dubbo.netty;

/**
 * Created by maitian13 on 2016/11/28.
 */
public interface EndPoint {
    void sent(Object message);
    Object received(Object message);
    boolean isConnected();
    void close();
}
