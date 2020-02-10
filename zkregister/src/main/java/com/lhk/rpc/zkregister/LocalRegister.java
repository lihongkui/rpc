package com.lhk.rpc.zkregister;

import java.util.HashMap;
import java.util.Map;
/**
 * 本地注册
 * @author lhk
 *
 */
public class LocalRegister {
	private static Map<String,Class<?>> register = new HashMap();
	/**
	 * 注册服务
	 * @param serviceName 服务名称	
	 * @param clazz 服务对应的实现类
	 */
	public static void regist(String serviceName,Class<?> clazz){
		register.put(serviceName,clazz);
	}
	
	public static Class<?> getRegistUrl(String serviceName){
		return register.get(serviceName);
	}
	


}
