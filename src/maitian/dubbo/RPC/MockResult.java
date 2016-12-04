package maitian.dubbo.RPC;

import java.io.Serializable;

/**
 * Created by maitian13 on 2016/11/24.
 */
public class MockResult implements Serializable{
    private static final long serialVersionUID = -3630485157441794463L;

    private final Object mResult;

    public MockResult(Object result)
    {
        mResult = result;
    }

    public Object getResult()
    {
        return mResult;
    }
}
