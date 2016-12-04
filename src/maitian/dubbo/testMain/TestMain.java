package maitian.dubbo.testMain;

import maitian.dubbo.dispatcher.Replier;
import maitian.dubbo.dispatcher.ReplierDispatcher;
import maitian.dubbo.exchanger.ExchangeChannel;
import maitian.dubbo.exchanger.ExchangerClient;
import maitian.dubbo.exchanger.Exchangers;
import maitian.dubbo.utils.URL;

/**
 * Created by maitian13 on 2016/11/21.
 */
public class TestMain {
    public static void main(String[] args){
        startServer(8889);
        //test(8889);
    }
    private static void startServer(int port){
        ReplierDispatcher dispatcher=new ReplierDispatcher();
        dispatcher.addReplier(Object.class, new Replier<Object>() {

            @Override
            public Object reply(Object request) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return new String("测试通信"+request);
            }
        });
        Exchangers.bind(URL.valueOf("dubbo://localhost:"+port),dispatcher);
    }
    private static void test(int port){
        ExchangerClient client=Exchangers.connect(URL.valueOf("dubbo:localhost:"+port));
        client.test("first");
    }
}
