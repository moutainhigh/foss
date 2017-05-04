/**
 *  initial comments.
 */
/*
 * PROJECT NAME: pkp-pickup-web
 * PACKAGE NAME: com.deppon.foss.module.pickup.waybill.server.common
 * FILE    NAME: TestHelperUtils.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.pickup.waybill.server.common;

import java.util.Random;

/**
 * 该类是生成测试数据的工具类
 * 
 * @author 026123-foss-lifengteng
 * @date 2012-11-22 上午9:51:42
 */
public class TestHelperUtils {

	/**
	 * 随机生成汉字
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-22 上午9:55:24
	 */
	public static String randomChChar(int n) {

		String str = "";
		Random random = null;
		
		int hightPos, lowPos; // 定义高低位
		
		for (int i = 0; i < n; i++) {
			random = new Random();

			hightPos = (176 + Math.abs(random.nextInt(39)));// 获取高位值

			lowPos = (161 + Math.abs(random.nextInt(93)));// 获取低位值

			byte[] b = new byte[2];

			b[0] = (new Integer(hightPos).byteValue());

			b[1] = (new Integer(lowPos).byteValue());
			
			try{
				str = str.concat(new String(b, "GBk"));// 转成中文
			}catch(Exception e){
				e.printStackTrace();
			}
		}

		return str;
	}
	
	public static void main(String[] args) {
		System.out.println(TestHelperUtils.randomChChar(10));
	}
}