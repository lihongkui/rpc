/**
 * 
 */
package com.lhk.rpc.protocol.netty;

import java.net.InetSocketAddress;

import com.lhk.rpc.api.RpcInnvocation;
import com.lhk.rpc.consumer.handler.NettyClientHandler;
import com.lhk.rpc.framework.decode.ObjectDecoder;
import com.lhk.rpc.framework.decode.ObjectEncoder;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
* @author lhk:
* @version 创建时间：2020年2月9日 下午3:12:13
* 类说明
*/
/**
 * @author ASUS
 *
 */
public class NettyClient {
	private final String host;
	private final int port;
	private final RpcInnvocation rpcInnvocation;
	
	//构造客户端方法
	public NettyClient(String host,int port,RpcInnvocation rpcInnvocation){
		this.host=host;
		this.port=port;
		this.rpcInnvocation=rpcInnvocation;
	}
	//客户端启动
	public RpcInnvocation start() throws InterruptedException {
		//创建生命周期组，EventLoopGroup包含一个或多个EventLoop，而EventLoop在一个生命周期内只能绑定一个Thread
		//每一个EventLoop的I/O事件都是由这个Thread处理，一个channel在生命周期内只能对应一个EventLoop，
		//但一个EventLoop可以被分给一个或多个channel，因此channel和thread是对应的
		EventLoopGroup group = null;
		final NettyClientHandler nettyClientHandler = new NettyClientHandler(rpcInnvocation);
		try {
			Bootstrap bootstrap = new Bootstrap();//创建一个引导启动类
			group = new NioEventLoopGroup();
			bootstrap.group(group)//把事件生命周期组EventLoopGroup注册引导启动类中去启动
				.channel(NioSocketChannel.class)//注册channel类型为NioSocketChannel，这个类型还有NioSctpChannel，NioDatagramChannel，LocalServerChannel，EmbeddedChannel
				.remoteAddress(new InetSocketAddress(host, port))//注册连接的服务器地址端口
				//注册事件操作句柄，使用childHandler时候不可以，所以只能用handler代替了
				.handler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(//初始化通道
							SocketChannel ch)
							throws Exception {
						//一个SocketChannel可以添加多个ChannelHandler，可以多加addLast，
						//这个ChannelHandler，有两种In和Out即ChannelInboundHandler，ChannelOutboundHandler
						//pipeline 在处理In和Out顺序是，in是从头部开始，out是尾部开始，例如 in1，out1,out2,in2,运行结果是in1->in2,out2->out1
                                                //ChannelInboundHandler之间的传递，通过调用 ctx.fireChannelRead(msg) 实现；调用ctx.write(msg) 将传递到ChannelOutboundHandler，
                                                //ctx.write()方法执行后，需要调用flush()方法才能令它立即执行，
                                                //pipeline中outhandler不能放在最后，否则不生效
						ch.pipeline().addLast(new ObjectEncoder(),
								new ObjectDecoder(),
								nettyClientHandler);
						
					}
				});
// 最后绑定服务器等待直到绑定完成，调用sync()方法会阻塞直到服务器完成绑定,然后服务器等待通道关闭，因为使用sync()，所以关闭操作也会被阻塞。
				ChannelFuture sync=bootstrap.connect().sync();//引导启动连接，ChannelFuture为将要执行操作的占位符
				//sync.channel().close().sync();
				sync.channel().closeFuture().sync();//关闭占位符，而不关闭整个通道，close是关闭整个客户端
			}finally {
			group.shutdownGracefully().sync();//关闭整个线程组
			}
		return nettyClientHandler.getRpcInnvocation();
	}
}
