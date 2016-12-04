package maitian.dubbo.dispatcher;

import maitian.dubbo.exchanger.ExchangeChannel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by maitian13 on 2016/11/24.
 */
public class ReplierDispatcher implements Replier<Object>{
    private final Map<Class<?>,Replier<?>> repliers=new ConcurrentHashMap<>();
    private Replier<?> defaultReplier;
    public ReplierDispatcher(){this(null);}
    public ReplierDispatcher(Replier<?> replier){
        this.defaultReplier=replier;
    }
//    public ReplierDispatcher(){}
    public <T> void addReplier(Class<T> type,Replier<T> replier){
        if(defaultReplier==null)defaultReplier=replier;
        repliers.put(type,replier);
        return;
    }
    private Replier<?> getReplier(Class<?> type){
        for(Map.Entry<Class<?>, Replier<?>> entry : repliers.entrySet()) {
            if(entry.getKey().isAssignableFrom(type)) {
                return entry.getValue();
            }
        }
        if(this.defaultReplier!=null)return defaultReplier;
        return null;
    }
    @Override
    public Object reply(Object request) {
        return ((Replier)getReplier(request.getClass())).reply(request);
    }

}
