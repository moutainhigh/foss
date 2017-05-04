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
 *  PROJECT NAME  : tfr-common-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/common/api/server/service/IFOSSToOAService.java
 *  
 *  FILE NAME          :IFOSSToOAService.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 ******************************************************************************/
package com.deppon.foss.module.transfer.common.api.server.service;

import java.io.IOException;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.common.api.shared.dto.AwbBeanUtils;

/**
 *  
 * @author 099197-foss-zhoudejun
 * @date 2013-3-11 下午3:04:36
 */
public interface IFOSSToTangYiService extends IService{
	
	/**
	 * 通过接入数据交换平台的账号和密码获取登录票据。
	 * @param userId 登录用户
	 * @param password 登录密码
	 * @return boolean(true:获取成功,false获取失败)
	 * @author 099197-foss-zhoudejun
	 * @throws IOException 
	 * @date 2013-3-11 下午3:06:05
	 */
	//boolean verifyTangYiToken() throws IOException;
	
	/**
	 * 查询唐翼制单
	 * @param userId 认证id
	 * @param token 唐翼校验码
	 * @param awbPrefix 运单号前缀
	 * @param awbNo 运单号 
	 * @param awbPostfix 运单号后缀(国内为00000000,国际)
	 * @author 099197-foss-zhoudejun
	 * @throws IOException 
	 * @date 2013-3-14 上午10:06:01
	 */
	int queryTangYiProtected (String awbNo) throws IOException;
	
	/**
	 * 制单服务接口
	 * @param userId 认证id
	 * @param token 唐翼校验码
	 * @param utils 运单基本信息和制单费用信息
	 * @return true 表示制单成功,false制单失败
	 * @author 099197-foss-zhoudejun
	 * @date 2013-3-14 下午3:28:34
	 */
	boolean createTangYiProtected(AwbBeanUtils awbBeanUtils) throws IOException; 
	
}