/**
 * 
 */
package com.lhk.rpc.zkregister;

import java.util.ArrayList;
import java.util.List;

import org.I0Itec.zkclient.ZkClient;
import org.jboss.netty.util.internal.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lhk.rpc.loadbalance.Url;

/**
* @author lhk:
* @version 创建时间：2020年2月10日 下午3:53:18
* 类说明
*/
/**
 * @author ASUS
 *
 */
public class ZkRegister extends RemoteRegister {
	public  static final Logger log = LoggerFactory.getLogger(ZkRegister.class);
	
	public RemoteRegister getRegistCenter(){
		return new ZkRegister();
	}
	/**
	 * 获取一个ZkClient，用于操作zookeeper
	 * @return
	 */
	public ZkClient getZKClient(){
		ZkClient zkClient = new ZkClient(ZKClientUtil.servers,ZKClientUtil.sessionTimeout,ZKClientUtil.connectionTimeout);
		log.info("获取zkClient成功");
		return zkClient;
	}
	/**
	 * 用于provider远程注册
	 * @param flag true:注册 false：删除
	 */

	public void register(Boolean flag,Url url){
		ZkClient zk = getZKClient();
		//配置的主机IP地址，可以换成其他形式获取
		/*String ip = System.getProperty("ip");
		ip = ZKClientUtil.isNullOrEmpty(ip)?"127.0.0.1:8080":ip;*/
		String ip = url.getHost()+":"+url.getPort();
		String registPath = ZKClientUtil.RPC_NAME_SPACE_PATH+"/"+ip;
		boolean exists1 = zk.exists(ZKClientUtil.RPC_NAME_SPACE_PATH);
		log.info("判断RPC_NAME_SPACE_PATH是否存在：{}",exists1?"存在":"不存在");
		if(!exists1){
			zk.createPersistent(ZKClientUtil.RPC_NAME_SPACE_PATH);
		}
		boolean exists = zk.exists(registPath);
		log.info("判断registPath是否存在：{}",exists1?"存在":"不存在");
		log.info("判断是注册地址还是删除地址：{}",flag?"注册地址":"删除地址");
		if(flag){
			if(!exists){
				String[] split = ip.split(":");
				zk.createPersistent(registPath);
			}
		}else{
			if(exists){
				zk.delete(registPath);
			}
		}
	}
	/**
	 * 获取所有的注册地址
	 * @return
	 */
	public List<String> getUrls(){
		ZkClient zk = getZKClient();
		//配置的主机IP地址，可以换成其他形式获取
		String ip = System.getProperty("ip");
		boolean exists1 = zk.exists(ZKClientUtil.RPC_NAME_SPACE_PATH);
		log.info("判断RPC_NAME_SPACE_PATH是否存在：{}",exists1?"存在":"不存在");
		if(!exists1){
			return new ArrayList<String>();
		}
		List<String> urls = zk.getChildren(ZKClientUtil.RPC_NAME_SPACE_PATH);
		return urls;
	}
}
