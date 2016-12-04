package maitian.dubbo.RPC;

import maitian.dubbo.dispatcher.Replier;
import maitian.dubbo.dispatcher.ReplierDispatcher;
import maitian.dubbo.exchanger.*;
import maitian.dubbo.utils.URL;

/**
 * Created by maitian13 on 2016/11/24.
 */
public class Main {
    private static void startServer(int port){
        ReplierDispatcher dispatcher=new ReplierDispatcher();
        //dispatcher.addReplier(RpcMessage.class,new RpcMessageHandler());
        dispatcher.addReplier(Object.class, new Replier<Object>() {

            @Override
            public Object reply( Object msg) {
                for(int i=0;i<10000;i++)
                    System.currentTimeMillis();
                System.out.println("handle:"+msg+";thread:"+Thread.currentThread().getName());
                return "hello world";
            }
        });
        Exchangers.bind(URL.valueOf("dubbo://localhost:"+8888),dispatcher);
    }
    private static void test(int port){
        ExchangerClient client = Exchangers.connect(URL.valueOf("dubbo://localhost:" + port));

    }
    public static void main(String[] args){

    }
}
