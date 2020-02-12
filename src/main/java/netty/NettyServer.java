package netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

/**
 * Netty服务端
 * 最小化参数配置简单总结：
 * 要启动一个Netty服务端，必须要指定三类属性，分别是线程模型、IO模型、连接读写处理逻辑，之后再绑定端口则服务端能正常启动起来
 * Created by xujia on 2019/10/15
 */
public class NettyServer {

    public static void main(String[] args) {
        // 服务端启动引导类，这个类负责服务端的启动工作
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        // 创建两个线程组，第一个线程组负责监听端口，创建新连接的线程组；第二个线程组负责处理每一条连接的数据读写（简单来说就是一个老板负责接活，另一个负责具体干活）
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        serverBootstrap
                // 首先给引导类配置两大线程，这个引导类的线程模型也就定型了
                .group(boss, worker)
                // 然后指定服务端的IO模型为NIO，如果想指定IO模型为BIO，那么这里配置上OioServerSocketChannel.class类型即可
                .channel(NioServerSocketChannel.class)
                // childHandler()就是定义后续每条连接的数据读写，处理业务逻辑，NioSocketChannel该类就是Netty对NIO类型连接的抽象
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new StringDecoder());
                        ch.pipeline().addLast(new SimpleChannelInboundHandler<String>() {
                            @Override
                            protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
                                System.out.println(s);
                            }
                        });
                    }
                });
        // 绑定端口号
        serverBootstrap.bind(8000);
    }

    /**
     * 端口号从1000自动递增直到绑定成功
     * @param serverBootstrap
     * @param port
     */
    private static void bind(final ServerBootstrap serverBootstrap, int port) {
        serverBootstrap.bind(8000).addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                if (future.isSuccess()) {
                    System.out.println("端口[" + port + "]绑定成功 !");
                } else {
                    System.out.println("端口[" + port + "]绑定失败 !");
                    // 绑定失败则端口号自动递增
                    bind(serverBootstrap, port + 1);
                }
            }
        });
    }
}
