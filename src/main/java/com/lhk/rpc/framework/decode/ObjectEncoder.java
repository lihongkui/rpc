/**
 * 
 */
package com.lhk.rpc.framework.decode;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoException;
import com.esotericsoftware.kryo.io.Output;
import com.lhk.rpc.api.RpcInnvocation;

/**
* @author lhk:
* @version 创建时间：2020年2月9日 下午6:03:21
* 类说明：编码器，实现复杂类型编码
*/
/**
 * @author ASUS
 *
 */
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class ObjectEncoder extends MessageToByteEncoder<RpcInnvocation> {

    private Kryo kryo = new Kryo();

    @Override
    protected void encode(ChannelHandlerContext ctx, RpcInnvocation msg, ByteBuf out) throws Exception {

        byte[] body = convertToBytes(msg);  //将对象转换为byte
        int dataLength = body.length;  //读取消息的长度
        out.writeInt(dataLength);  //先将消息长度写入，也就是消息头
        out.writeBytes(body);  //消息体中包含我们要发送的数据
    }

    private byte[] convertToBytes(RpcInnvocation car) {

        ByteArrayOutputStream bos = null;
        Output output = null;
        try {
            bos = new ByteArrayOutputStream();
            output = new Output(bos);
            kryo.writeObject(output, car);
            output.flush();

            return bos.toByteArray();
        } catch (KryoException e) {
            e.printStackTrace();
        }finally{
        	output.close();
        	try {
				bos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        return null;
    }

}
