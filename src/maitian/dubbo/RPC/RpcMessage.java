package maitian.dubbo.RPC;


import java.io.Serializable;

/**
 * Created by maitian13 on 2016/11/24.
 */
public class RpcMessage implements Serializable{
    private static final long serialVersionUID = -5148079121106659095L;
    private String mClassName;

    private String mMethodDesc;

    private Class<?>[] mParameterTypes;

    private Object[] mArguments;

    public RpcMessage(String cn, String desc, Class<?>[] parameterTypes,Object[] args)
    {
        mClassName = cn;
        mMethodDesc = desc;
        mParameterTypes = parameterTypes;
        mArguments = args;
    }

    public String getClassName()
    {
        return mClassName;
    }

    public String getMethodDesc()
    {
        return mMethodDesc;
    }

    public Class<?>[] getParameterTypes() {
        return mParameterTypes;
    }

    public Object[] getArguments()
    {
        return mArguments;
    }
}
