package maitian.dubbo.exchanger;

import java.io.Serializable;

/**
 * Created by maitian13 on 2016/11/24.
 */
public class Response implements Serializable{
    private long mId=0;
    private Object mResult;
    public Response(long id){
        mId = id;
    }
    public long getId(){return this.mId;}
    public Object getResult() {
        return mResult;
    }

    public void setmResult(Object mResult) {
        this.mResult = mResult;
    }
}
