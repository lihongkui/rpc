/**
 * 
 */
package com.lhk.rpc.protocol.netty;

import com.lhk.rpc.framework.decode.ObjectDecoder;
import com.lhk.rpc.framework.decode.ObjectEncoder;
import com.lhk.rpc.provider.handler.NettyServerHandler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
* @author lhk:
* @version 创建时间：2020年2月9日 下午2:15:59
* 类说明
*/
/**
 * @author ASUS
 *
 */
public class NettyServer {
	/*private String host;
	private int port;*/
	public static void startUp(int port) throws InterruptedException{
		EventLoopGroup group = null;
		try{
		ServerBootstrap serverBootstrap = new ServerBootstrap();//创建server引导
		group = new NioEventLoopGroup();
		serverBootstrap.group(group)
			.channel(NioServerSocketChannel.class)
			.localAddress(port)
			.childHandler(new ChannelInitializer<SocketChannel>() {
 
				@Override
				protected void initChannel(SocketChannel ch)
						throws Exception {
					ch.pipeline().addLast(new ObjectEncoder(),
							new ObjectDecoder(),
							new NettyServerHandler());
					
				}
			});
		ChannelFuture sync = serverBootstrap.bind().sync();
		System.out.println("开始监听，地址端口为：" + sync.channel());
		sync.channel().closeFuture().sync();
		}finally{
		group.shutdownGracefully().sync();
		}
	}

}
