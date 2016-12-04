package maitian.dubbo.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.http2.Http2Connection;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import maitian.dubbo.dispatcher.ReplierDispatcher;
import maitian.dubbo.exchanger.ExchangeChannel;
import maitian.dubbo.exchanger.ExhangeHandler;
import maitian.dubbo.utils.Constants;
import maitian.dubbo.utils.NetUtils;
import maitian.dubbo.utils.URL;
import sun.nio.ch.Net;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by maitian13 on 2016/11/21.
 */
public class NettyServer implements Server,EndPoint{
    private InetSocketAddress localAddress;
    private InetSocketAddress bindAddress;
    private ServerBootstrap bootstrap;
    private Map<String,Channel> channels;
    private int accepts;
    private int idleTimeout=600;//600s
    private ExhangeHandler dispatcher;
    private URL url;
    ExecutorService executorService;
    public NettyServer(URL url, ExhangeHandler dispatcher){
        this.dispatcher=dispatcher;
        this.url=url;
        localAddress=url.toInetSocketAddress();
        String host=url.getParameter(Constants.ANYHOST_KEY,false)|| NetUtils.isInvalidLocalHost(url.getHost())
                ?NetUtils.ANYHOST:url.getHost();
        bindAddress=new InetSocketAddress(host,url.getPort());
        this.accepts=url.getParameter(Constants.ACCEPTS_KEY, Constants.DEFAULT_ACCEPTS);
        this.idleTimeout = url.getParameter(Constants.IDLE_TIMEOUT_KEY, Constants.DEFAULT_IDLE_TIMEOUT);//url格式？
        try{
            doOpen();
        }catch (Exception e){
            System.out.println(e);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    @Override
    public void sent(Object message) {

    }

    public Object received(Object message){
        ExchangeChannel exchangeChannel = ExchangeChannel.getOrAddChannel(NettyServer.this);
        return this.dispatcher.received(message);
    }
    public void doOpen()throws Throwable{
        EventLoopGroup boss= new NioEventLoopGroup();
        EventLoopGroup worker=new NioEventLoopGroup();
        try{
            ServerBootstrap serverBootstrap=new ServerBootstrap().group(boss,worker).channel(NioServerSocketChannel.class)
                    .localAddress(bindAddress).childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline=socketChannel.pipeline();
                            pipeline.addLast("frameDecoder",new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,0,4,0,4));
                            pipeline.addLast("frameEncoder", new LengthFieldPrepender(4));
                            pipeline.addLast("encoder",new ObjectEncoder());
                            pipeline.addLast("decoder",new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)));//inbound
                            //pipeline.addLast("encoder",new NettyEncoder());
                            //pipeline.addLast("decoder",new NettyDecoder());
                            pipeline.addLast(new NettyHandler2(NettyServer.this));
                            pipeline.addLast(new NettyHandler(NettyServer.this));
                        }
                    }).option(ChannelOption.SO_BACKLOG,128).childOption(ChannelOption.SO_KEEPALIVE,true);
            ChannelFuture future=serverBootstrap.bind(url.getPort()).sync();
            future.channel().closeFuture().sync();
        }catch (Exception e){
            boss.shutdownGracefully();
            worker.shutdownGracefully();
            System.out.println(e);
        }

    }

    @Override
    public boolean isConnected() {
        return false;
    }

    @Override
    public void close() {

    }
}
