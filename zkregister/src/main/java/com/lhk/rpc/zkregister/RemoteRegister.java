/**
 * 
 */
package com.lhk.rpc.zkregister;
/**
* @author lhk:
* @version 创建时间：2020年2月9日 上午11:04:24
* 类说明 远程注册
*/

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.lhk.rpc.loadbalance.Url;

public abstract class RemoteRegister {
	/*private static Map<String, List<Url>> regists = new HashMap<String, List<Url>>();
	private static List<Url> registUrl = new ArrayList<Url>();*/
	
	public static void regist(String serviceName,Url url){
		
		new ZkRegister().register(true, url);
		}
		
		public static List<Url> getRegistUrl(String serviceName){
			List<String> urlsForRegister = new ZkRegister().getUrls();
			List<Url> urls = new ArrayList<Url>();
			for (Iterator<String> iterator = urlsForRegister.iterator(); iterator.hasNext();) {
				String url = (String) iterator.next();
				String[] split = url.split(":");
				urls.add(new Url(split[0], Integer.valueOf(split[1])));
				
			}
			return urls;
		}
	public abstract void register(Boolean flag,Url url);
	public abstract List<String> getUrls();
	
	public abstract RemoteRegister getRegistCenter();
	
	
}
