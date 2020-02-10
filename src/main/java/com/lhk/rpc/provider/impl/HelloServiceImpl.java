/**
 * 
 */
package com.lhk.rpc.provider.impl;

import com.lhk.rpc.api.HelloService;

/**
* @author lhk:
* @version 创建时间：2020年2月9日 下午1:39:58
* 类说明
*/
/**
 * @author ASUS
 *
 */
public class HelloServiceImpl implements HelloService {

	/* (non-Javadoc)
	 * @see com.lhk.rpc.provider.HelloService#sayHello(java.lang.String)
	 */
	public String sayHello(String text) {
		// TODO Auto-generated method stub
		return (new StringBuffer("hello").append(text)).toString();
	}

}
