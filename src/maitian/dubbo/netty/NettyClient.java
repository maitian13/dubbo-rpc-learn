package maitian.dubbo.netty;

import com.sun.xml.internal.bind.v2.model.core.ClassInfo;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import maitian.dubbo.exchanger.Request;
import maitian.dubbo.utils.Constants;
import maitian.dubbo.utils.NetUtils;
import maitian.dubbo.utils.URL;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by maitian13 on 2016/11/21.
 */
public class NettyClient implements Client,ChannelHandler{
    private Bootstrap bootstrap;
    private InetSocketAddress bindAddress;
    private InetSocketAddress localAddress;
    private Channel channel;
    private URL url;
    private ChannelFuture future;
    private EventLoopGroup group;
    public NettyClient(URL url){
        this.url=url;
        localAddress=url.toInetSocketAddress();
        String host=url.getParameter(Constants.ANYHOST_KEY,false)|| NetUtils.isInvalidLocalHost(url.getHost())
                ?NetUtils.ANYHOST:url.getHost();
        bindAddress=new InetSocketAddress(host,url.getPort());
        doOpen();
        doConnect();
    }
    private void doOpen(){
        group=new NioEventLoopGroup();
        try{
            bootstrap=new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY,true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline=socketChannel.pipeline();
                            pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
                            pipeline.addLast("frameEncoder", new LengthFieldPrepender(4));
                            pipeline.addLast("encoder", new ObjectEncoder());//outbound
                            pipeline.addLast("decoder", new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)));//inbound
                            //pipeline.addLast("encoder",new NettyEncoder());
                            pipeline.addLast(new ResultHandler());
                            //pipeline.addLast(new NettyHandler2());
                        }
                    });

        }catch (Exception e){
            System.out.println(e);
        }
    }
    public void send(Object message){
//        try {
//            System.out.println("client going to send");
//            channel.writeAndFlush(message).sync();
//            System.out.println("hhhhh");
//            channel.closeFuture().sync();
//            System.out.println("finish receive");
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }
    private void connect(){
        //// TODO: 2016/11/24 这里需要加锁么？难道为了避免重复多次链接？
        if(isConnected()){
            return;
        }
        doConnect();
    }
    private void doConnect(){
        try {
            future=bootstrap.connect(bindAddress).sync();
            boolean ret=future.awaitUninterruptibly(10000, TimeUnit.MILLISECONDS);//超时
            if(ret&&future.isSuccess()){
                //// TODO: 2016/11/24
                Channel newChannel=future.channel();
                try{
                    Channel oldChannel=NettyClient.this.channel;
                    if(oldChannel!=null){
                        oldChannel.close();
                    }
                }finally {
                    NettyClient.this.channel=newChannel;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public boolean isConnected(){
        Channel channel=getChannel();
        if(channel==null)return false;
        else return true;
    }

    @Override
    public void close() {
        group.shutdownGracefully();
    }

    public URL getURL(){
        return this.url;
    }
    private Channel getChannel(){
        return this.channel;
    }

    @Override
    public void sent(Object message) {
        try {
            channel.writeAndFlush(message);
            channel.closeFuture();
        }finally {

        }
    }

    @Override
    public Object received(Object message) {
        return null;
    }
}
