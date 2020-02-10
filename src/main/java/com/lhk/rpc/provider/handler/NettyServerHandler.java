/**
 * 
 */
package com.lhk.rpc.provider.handler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.lhk.rpc.api.RpcInnvocation;
import com.lhk.rpc.regist.LocalRegister;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
* @author lhk:
* @version 创建时间：2020年2月9日 下午2:30:29
* 类说明
*/
/**
 * @author ASUS
 *
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		//读取数据
		System.out.println("服务器读取数据。。"+msg);
		System.out.println("-----"+ctx.channel());
		RpcInnvocation rpcInnvocation = (RpcInnvocation)msg;
		Object invoke = invoke(rpcInnvocation);
		System.out.println("服务器向客户端发送数据..."+invoke);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", "0");
		map.put("message", invoke);
		RpcInnvocation result = new RpcInnvocation(null,null,null,null,map);
		ctx.writeAndFlush(result);
		/*ByteBuf buf = (ByteBuf)msg;
		byte[] bytes = new byte[buf.readableBytes()];
		buf.readBytes(bytes);
		String string = new String(bytes, "UTF-8");
		System.out.println("读取客户端的数据为："+ string);
		//向客户端发送数据
		System.out.println("服务器向客户端发送数据...");
		String currenttime = new Date(System.currentTimeMillis()).toString();
		ByteBuf copiedBuffer = Unpooled.copiedBuffer(currenttime.getBytes());
		ctx.writeAndFlush(copiedBuffer);*/
		
		
	}
	/*@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		System.out.println("服务器读写数据完毕。。。");
		ctx.flush();
	}*/
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		System.out.println(cause);
		System.out.println("服务器异常处理。。。");
		ctx.close();
	}
	/**
	 * 内部流程处理
	 * @param rpcInnvocation
	 * @return
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws InstantiationException
	 */
	private Object invoke(RpcInnvocation rpcInnvocation) throws ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, InstantiationException {
		String interFaceName = rpcInnvocation.getInterFaceName();
		Class<?> clazz = LocalRegister.getRegistUrl(interFaceName);
		Object newInstance = clazz.newInstance();
		Method method = clazz.getMethod(rpcInnvocation.getMethodName(), rpcInnvocation.getParamsType());
		Object invoke = method.invoke(newInstance, rpcInnvocation.getParams());
		return invoke;
		
	}

}
