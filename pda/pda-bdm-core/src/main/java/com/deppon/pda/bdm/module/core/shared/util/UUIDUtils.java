package com.deppon.pda.bdm.module.core.shared.util;

import java.util.UUID;

/**
 * UUID工具类
 * @author wanghongling
 *
 */
public class UUIDUtils {
	/**
	 * 生成36位的UUID
	 * @return
	 */
	public static String getUUID(){
		return UUID.randomUUID().toString();
	}
}
