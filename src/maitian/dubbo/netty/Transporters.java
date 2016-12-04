package maitian.dubbo.netty;


import maitian.dubbo.dispatcher.ReplierDispatcher;
import maitian.dubbo.exchanger.ExhangeHandler;
import maitian.dubbo.utils.URL;

/**
 * Created by maitian13 on 2016/11/21.
 */
//单例模式
public class Transporters {
    private static Transporter transporter;
    public static Transporter getTranspoter(){
        if(transporter==null) {
            synchronized (Transporters.class) {
                if(transporter==null){
                    transporter=new NettyTransporter();
                }
            }
        }
        return transporter;
    }
    public static ChannelHandler bind(URL url,ExhangeHandler dispatcher){
        return getTranspoter().bind(url,dispatcher);
    }
    public static ChannelHandler connect(URL url){
        return getTranspoter().connect(url);
    };
}
