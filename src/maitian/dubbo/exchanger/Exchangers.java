package maitian.dubbo.exchanger;


import io.netty.channel.ChannelHandlerAdapter;
import maitian.dubbo.dispatcher.ExchangeHandlerDispatcher;
import maitian.dubbo.dispatcher.Replier;
import maitian.dubbo.utils.URL;

/**
 * Created by maitian13 on 2016/11/24.
 */
//todo
// 工厂方法？
public class Exchangers {
    public static MyExchanger myExchanger;
    public static ExchangerServer bind(URL url, Replier<?>replier){
        return bind(url,new ExchangeHandlerDispatcher(replier));
    }
    public static ExchangerClient connect(URL url){
        return getExchanger().connect(url);
    }
    public static ExchangerServer bind(URL url,ExhangeHandler handler){
        return getExchanger().bind(url,handler);
    }
    public static MyExchanger getExchanger(){
        if(myExchanger==null) {
            synchronized (Exchangers.class) {
                if(myExchanger==null){
                    myExchanger=new MyExchanger();
                }
            }
        }
        return myExchanger;
    }
}
