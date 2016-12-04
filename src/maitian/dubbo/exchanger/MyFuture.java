package maitian.dubbo.exchanger;

import maitian.dubbo.netty.Client;
import maitian.dubbo.netty.EndPoint;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by maitian13 on 2016/11/24.
 */
public class MyFuture {
    //private static final Map<Long, Channel> CHANNELS   = new ConcurrentHashMap<Long, Channel>();

    private static final Map<Long, MyFuture> FUTURES   = new ConcurrentHashMap<Long, MyFuture>();
    private static final Map<Long, EndPoint>       CHANNELS   = new ConcurrentHashMap<Long, EndPoint>();
    //// TODO: 2016/11/24 这里的锁难道只是用来等待response的么
    private final Lock lock = new ReentrantLock();
    private final Condition done = lock.newCondition();
    private final long id;
    private final EndPoint client;
    private final Request request;
    private ResponseCallback callback;
    //// TODO: 2016/11/24 这里使用同步变量
    private volatile Response response;
    public MyFuture(EndPoint client, Request request){
        this.client = client;
        this.request = request;
        this.id = request.getId();
        FUTURES.put(id, this);
        CHANNELS.put(id, client);
    }
    //// TODO: 2016/11/24 同步获取
    public Object get(){
        if(!isDone()){
            try{
                lock.lock();
                while(!isDone()){
                    done.await(1000, TimeUnit.MILLISECONDS);//等待
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
        return returnFromResponse();
    }
    public void setCallback(ResponseCallback callback){
        if(isDone()){
            invokeCallback(callback);
        }else{
            boolean isdone=false;
            lock.lock();
            try{
                if(!isDone()){
                    this.callback=callback;
                }else {
                    isdone=true;
                }
            }finally {
                lock.unlock();
            }
            if(isdone){
                //todo
                // 这句话貌似根本调用不到啊
                invokeCallback(callback);
            }
        }
    }
    public static void received(Response response){
        try{
            MyFuture future =FUTURES.remove(response.getId());
            if(future!=null){
                future.doReceived(response);
            }
        }finally {
            //ChannelHandler handler=CHANNELS.get(response.getId());
            //handler.close();
            CHANNELS.remove(response.getId());
        }
    }
    private void doReceived(Response res){
        lock.lock();
        try{
            response=res;
            if(done!=null){
                //// TODO: 2016/11/24 结束等待
                done.signal();
            }
        }finally {
            lock.unlock();
        }
        //// TODO: 2016/11/24 回调在这里执行
        if(callback!=null){
            invokeCallback(callback);
        }
    }
    private void invokeCallback(ResponseCallback c){
        //// TODO: 2016/11/24 为什么这里要拷贝
        ResponseCallback callbackCopy = c;
        Response res=response;
        callbackCopy.done(res.getResult());
    }
    public boolean isDone(){
        return response!=null;
    }
    private Object returnFromResponse(){
        Response res=response;
        if(res==null){
            throw new IllegalStateException("response cannot be null");
        }
        return res.getResult();
    }
}
