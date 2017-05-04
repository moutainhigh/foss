/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-departure-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/departure/api/utils/Constants.java
 *  
 *  FILE NAME          :Constants.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.departure.api.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;

/**
 * 
 * 读取properties文件定义常量
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:foss-sunrui,date:2012-10-22 上午11:23:31,content:TODO
 * </p>
 * 
 * @author foss-liubinbin
 * @date 2012-10-22 上午11:23:31
 * @since
 * @version
 */
public class Constants {
	private static final Logger logger = LogManager.getLogger(Constants.class);

	/***********车牌号编码转成车牌号名称的配置文件*************/
//	private static final String BASE_NAME = "com.deppon.foss.module.transfer.departure.api.shared.util.vehicle-no-config";

//	private static ResourceBundle resourceBundle;

	public final static String SUCCESS = "success";
	public final static String DEFAULT_CHARSET = "utf-8";

//	static {
//		if (resourceBundle == null) {
//			try {
//				resourceBundle = ResourceBundle.getBundle(BASE_NAME);
//			} catch (Exception e) {
//				e.printStackTrace();
//				logger.error("", e);
//			}
//		}
//	}

	/**
	 * 
	 * 将车牌号从编码格式转成普通格式
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2013-1-30 上午10:48:01
	 */
	public static String convertVehicleCode2Name(String vehicleCode) {
		String[] v = vehicleCode.split("-");
		if (v.length != 3) {
			throw new TfrBusinessException("车牌格式不正确", "");
		}
		String vehicle_CN = map.get(v[0]);
		if (vehicle_CN == null) {
			throw new TfrBusinessException("车牌格式不正确", "");
		}
		return vehicle_CN + v[1] + v[2];
	}
	
	/**
	 * ResourceBundle键值判断
	 * 
	 * @param resource
	 * @param key
	 * @return String
	 */
	public static String getNameValue(String key) {
		String retValue = "";
		try {
			retValue = map.get(key);
		} catch (MissingResourceException e) {
			logger.error("", e);
		} catch (NullPointerException e) {
			logger.error("", e);
		} catch (Exception e) {
			logger.error("", e);
		}
		return retValue;
	}
	/**
	 * 
	 * 把车牌号从普通模式转成编码格式
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2013-1-30 上午10:48:01
	 */
	public static String convertVehicleName2Code(String vehicleCode) {
		if (vehicleCode.length() <=1 ) {
			throw new TfrBusinessException("车牌格式不正确", "");
		}
		String vehicle_CN = getNameValue(vehicleCode.substring(0, 1));
		if (vehicle_CN == null) {
			throw new TfrBusinessException("车牌格式不正确", "");
		}
		try {
			return vehicle_CN +"-"+ vehicleCode.substring(1,2)+"-"+vehicleCode.substring(2);
		} catch (Exception e) {
			throw new TfrBusinessException("车牌格式不正确", "");
		}
	}

	public final static Map<String,String> map = new HashMap();  
	static {  
		map.put("JING","京");
		map.put("TJIN","津");
		map.put("HU","沪");
		map.put("CYU","渝");
		map.put("MENG","蒙");
		map.put("XIN","新");
		map.put("ZANG","藏");
		map.put("NING","宁");
		map.put("GUI","桂");
		map.put("GANG","港");
		map.put("AO","澳");
		map.put("HEI","黑");
		map.put("JIL","吉");
		map.put("LIAO","辽");
		map.put("JIN","晋");
		map.put("JI","冀");
		map.put("QING","青");
		map.put("LU","鲁");
		map.put("YU","豫");
		map.put("SU","苏");
		map.put("WAN","皖");
		map.put("ZHE","浙");
		map.put("MIN","闽");
		map.put("GAN","赣");
		map.put("XIANG","湘");
		map.put("E","鄂");
		map.put("YUE","粤");
		map.put("QIONG","琼");
		map.put("GANS","甘");
		map.put("SHAN","陕");
		map.put("QIAN","贵");
		map.put("DIAN","滇");
		map.put("CHUAN","川");
		map.put("YUN","云");
		map.put("京","JING");
		map.put("津","TJIN");
		map.put("沪","HU");
		map.put("渝","CYU");
		map.put("蒙","MENG");
		map.put("新","XIN");
		map.put("藏","ZANG");
		map.put("宁","NING");
		map.put("桂","GUI");
		map.put("港","GANG");
		map.put("澳","AO");
		map.put("黑","HEI");
		map.put("吉","JIL");
		map.put("辽","LIAO");
		map.put("晋","JIN");
		map.put("冀","JI");
		map.put("青","QING");
		map.put("鲁","LU");
		map.put("豫","YU");
		map.put("苏","SU");
		map.put("皖","WAN");
		map.put("浙","ZHE");
		map.put("闽","MIN");
		map.put("赣","GAN");
		map.put("湘","XIANG");
		map.put("鄂","E");
		map.put("粤","YUE");
		map.put("琼","QIONG");
		map.put("甘","GANS");
		map.put("陕","SHAN");
		map.put("贵","QIAN");
		map.put("滇","DIAN");
		map.put("川","CHUAN");
		map.put("云","YUN");
	}  
	public static void main(String args[]) {
		System.out.println(convertVehicleName2Code("沪A1234"));
//		System.out.println(convertVehicleCode2Name("HU-A1234"));
		
		// value = URLEncoder.encode(getKeyValue("HU"), DEFAULT_CHARSET);
	}
}