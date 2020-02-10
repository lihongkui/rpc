/**
 * 
 */
package com.lhk.rpc.api;

import java.util.Arrays;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
* @author lhk:
* @version 创建时间：2020年2月9日 下午2:33:06
* 类说明
*/
/**
 * @author ASUS
 *
 */
@Data
@AllArgsConstructor
@ToString
@Getter
@Setter
public class RpcInnvocation {
	
	
	
	/**
	 * 
	 */
	public RpcInnvocation() {
		super();
	}
	/**
	 * @param interFaceName
	 * @param methodName
	 * @param params
	 * @param paramsType
	 */
	
	private String interFaceName;
	/**
	 * @param interFaceName
	 * @param methodName
	 * @param params
	 * @param paramsType
	 * @param result
	 */
	public RpcInnvocation(String interFaceName, String methodName, Object[] params, Class<?>[] paramsType,
			Map<String, Object> result) {
		super();
		this.interFaceName = interFaceName;
		this.methodName = methodName;
		this.params = params;
		this.paramsType = paramsType;
		this.result = result;
	}
	private String methodName;
	private Object[] params;
	private Class<?>[] paramsType;
	private Map<String,Object> result;
	/**
	 * @return the interFaceName
	 */
	public String getInterFaceName() {
		return interFaceName;
	}
	/**
	 * @param interFaceName the interFaceName to set
	 */
	public void setInterFaceName(String interFaceName) {
		this.interFaceName = interFaceName;
	}
	/**
	 * @return the methodName
	 */
	public String getMethodName() {
		return methodName;
	}
	/**
	 * @param methodName the methodName to set
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	/**
	 * @return the params
	 */
	public Object[] getParams() {
		return params;
	}
	/**
	 * @param params the params to set
	 */
	public void setParams(Object[] params) {
		this.params = params;
	}
	
	/**
	 * @return the paramsType
	 */
	public Class<?>[] getParamsType() {
		return paramsType;
	}
	/**
	 * @param paramsType the paramsType to set
	 */
	public void setParamsType(Class<?>[] paramsType) {
		this.paramsType = paramsType;
	}
	/**
	 * @return the result
	 */
	public Map<String, Object> getResult() {
		return result;
	}
	/**
	 * @param result the result to set
	 */
	public void setResult(Map<String, Object> result) {
		this.result = result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RpcInnvocation [interFaceName=" + interFaceName + ", methodName=" + methodName + ", params="
				+ Arrays.toString(params) + ", paramsType=" + Arrays.toString(paramsType) + ", result=" + result + "]";
	}
	

	
}
