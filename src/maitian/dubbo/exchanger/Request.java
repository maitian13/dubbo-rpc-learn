package maitian.dubbo.exchanger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by maitian13 on 2016/11/24.
 */
public class Request implements Serializable {
    private final long mId;
    private Object  mData;
    private static final AtomicLong INVOKE_ID = new AtomicLong(0);
    public Request(long id){
        mId = id;
    }
    public Request() {
        mId = newId();
    }

    public Object getmData() {
        return mData;
    }

    public void setmData(Object mData) {
        this.mData = mData;
    }

    private static long newId() {
        // getAndIncrement()增长到MAX_VALUE时，再增长会变为MIN_VALUE，负数也可以做为ID
        return INVOKE_ID.getAndIncrement();
    }
    public long getId(){
        return this.mId;
    }
    private void writeObject(ObjectOutputStream o) throws IOException {
        o.writeObject(this.mData);
        o.writeObject(this.mId);
    }

    private void readObject(ObjectInputStream o) throws IOException, ClassNotFoundException {
        this.mData = (Object) o.readObject();
    }
}
