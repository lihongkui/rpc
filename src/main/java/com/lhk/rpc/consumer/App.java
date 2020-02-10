/**
 * 
 */
package com.lhk.rpc.consumer;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import com.lhk.rpc.api.HelloService;
import com.lhk.rpc.api.ProxFactory;

/**
* @author lhk:
* @version 创建时间：2020年2月9日 下午3:10:26
* 类说明
*/
/**
 * @author ASUS
 *
 */
public class App {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		HelloService helloService = ProxFactory.getBean(HelloService.class);
		String sayHello = helloService.sayHello("abc");
		System.out.println("sayHello"+sayHello);

	}

}

interface PersonDao {
	public void say();
}

class PersonDaoImpl implements PersonDao{

	public void say() {
		System.out.println("time to eat");
	}

}


class PersonHandler implements InvocationHandler {

	private Object obj;
	
	public PersonHandler(Object obj){
		this.obj=obj;
	}
	
	
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		
		System.out.println("before");
		Object result = method.invoke(obj, args);
		System.out.println("after");
		return result;
	}

}

