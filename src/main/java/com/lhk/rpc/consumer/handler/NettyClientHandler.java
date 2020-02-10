/**
 * 
 */
package com.lhk.rpc.consumer.handler;

import com.lhk.rpc.api.RpcInnvocation;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
* @author lhk:
* @version 创建时间：2020年2月9日 下午3:13:59
* 类说明
*/
/**
 * @author ASUS
 *
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {
	
	private RpcInnvocation rpcInnvocation;
	public NettyClientHandler(RpcInnvocation rpcInnvocation){
		this.rpcInnvocation=rpcInnvocation;
	}
	
	// 客户端连接服务器后被调用，并向服务器发送数据
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // Send the message to Server
        super.channelActive(ctx);
        System.out.println("-----"+ctx.channel());
        System.out.println("client send message:"+rpcInnvocation);
		ctx.writeAndFlush(rpcInnvocation);//发送数据	
    }

    //有数据可读时
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        // you can use the Object from Server here
        System.out.println("client receive msg:"+msg);
        rpcInnvocation = (RpcInnvocation) msg;
        ctx.close();
    }

 // 当连接发生异常时被调用
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {

        cause.printStackTrace();
        ctx.close();
    }

	/**
	 * @return the rpcInnvocation
	 */
	public RpcInnvocation getRpcInnvocation() {
		return rpcInnvocation;
	}

	/**
	 * @param rpcInnvocation the rpcInnvocation to set
	 */
	public void setRpcInnvocation(RpcInnvocation rpcInnvocation) {
		this.rpcInnvocation = rpcInnvocation;
	}
    
    

}
