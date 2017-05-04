package com.deppon.foss.module.login.shared.util;

import org.apache.commons.codec.binary.Base64;

/**
 * 
 * 令牌序列化与反序列化
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:ztjie,date:2013-4-19 下午2:26:43,content:TODO </p>
 * @author ztjie
 * @date 2013-4-19 下午2:26:43
 * @since
 * @version
 */
final public class TokenMarshal {
	private TokenMarshal(){
	}

	/**
	 * 
	 * <p>序列化(Base64编码)</p> 
	 * @author ztjie
	 * @date 2013-4-19 下午2:27:10
	 * @param token
	 * @return
	 * @see
	 */
	public static String marshal(MacToken token) {
		return new String(Base64.encodeBase64String(token.toBytes()));
	}

	/**
	 * 
	 * <p>反序列化（Base64解码）</p> 
	 * @author ztjie
	 * @date 2013-4-19 下午2:27:19
	 * @param tokenStr
	 * @return
	 * @see
	 */
	public static MacToken unMarshal(String tokenStr) {
		return new MacToken(Base64.decodeBase64(tokenStr));
	}
}

