/**
 * 
 */
package com.lhk.rpc.api;
/**
* @author lhk:
* @version 创建时间：2020年2月9日 下午3:34:41
* 类说明
*/
/**
 * @author ASUS
 *
 */

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

import com.lhk.rpc.protocol.netty.NettyClient;

public class ProxFactory {

	public static <T> T getBean(final Class<T> interFace){
		return (T) Proxy.newProxyInstance(interFace.getClassLoader(), new Class<?>[] {interFace}, new InvocationHandler() {
			
			public Object invoke(Object var1, Method var2, Object[] var3) throws Throwable {
				// TODO Auto-generated method stub
				System.out.println("proxy");
				RpcInnvocation rpcInnvocation = new RpcInnvocation(interFace.getName(),var2.getName(),var3,var2.getParameterTypes(),new HashMap<String, Object>());
				
				NettyClient nettyClient = new NettyClient("127.0.0.1",8080,rpcInnvocation);
				RpcInnvocation resultInvocation = nettyClient.start();
				Map<String, Object>result = resultInvocation.getResult();
				if ("0".equals(result.get("code"))) {
					return result.get("message");
				}
				return null;
			}
		});
	}
}
