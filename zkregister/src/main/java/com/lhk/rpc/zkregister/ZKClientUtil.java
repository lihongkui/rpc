/**
 * 
 */
package com.lhk.rpc.zkregister;

/**
* @author lhk:
* @version 创建时间：2020年2月10日 下午1:57:49
* 类说明
*/
/**
 * @author ASUS
 *
 */
public class ZKClientUtil {
	
	public static String  servers = System.getProperty("zkIp");//可以是多个用“,”隔开
	public static int sessionTimeout=Integer.valueOf(System.getProperty("sessionTimeout"));
	public static int connectionTimeout=Integer.valueOf(System.getProperty("connectionTimeout"));
	
	public static String RPC_NAME_SPACE_PATH="/rpc_register";
	public static boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }
	
	
}
