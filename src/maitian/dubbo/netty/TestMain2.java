package maitian.dubbo.netty;

import maitian.dubbo.exchanger.ExchangerClient;
import maitian.dubbo.exchanger.Exchangers;
import maitian.dubbo.utils.URL;

/**
 * Created by maitian13 on 2016/11/28.
 */
public class TestMain2 {
    public static void main(String[] args){
        test(8889);
    }
    private static void test(int port){
        ExchangerClient client= Exchangers.connect(URL.valueOf("dubbo://localhost:"+port));
        System.out.println((String)client.request("first").get());
        client.close();
    }
}
