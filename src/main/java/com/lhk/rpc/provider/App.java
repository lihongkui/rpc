package com.lhk.rpc.provider;

import com.lhk.rpc.api.HelloService;
import com.lhk.rpc.loadbalance.Url;
import com.lhk.rpc.protocol.netty.NettyServer;
import com.lhk.rpc.provider.impl.HelloServiceImpl;
import com.lhk.rpc.regist.LocalRegister;
import com.lhk.rpc.regist.RemoteRegister;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception
    {
        //本地注册
    	LocalRegister.regist(HelloService.class.getName(), HelloServiceImpl.class);
    	//远程注册
    	Url url = new Url("127.0.0.1", 8888);
    	RemoteRegister.regist(HelloService.class.getName(), url);
    	//启动服务
    	NettyServer.startUp(8080);
    	
    }
}
