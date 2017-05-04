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
 *  PROJECT NAME  : tfr-common
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/common/server/service/impl/FOSSToLMSService.java
 *  
 *  FILE NAME          :FOSSToLMSService.java
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
package com.deppon.foss.module.transfer.common.server.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.UUID;

import javax.xml.ws.Holder;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import com.csair.tang.tangwebservice.Awb;
import com.csair.tang.tangwebservice.AwbBasic;
import com.csair.tang.tangwebservice.AwbCharge;
import com.csair.tang.tangwebservice.AwbQueryServiceSoap;
import com.csair.tang.tangwebservice.IsAwbMade;
import com.csair.tang.tangwebservice.IsAwbMadeResponse;
import com.csair.tang.tangwebservice.SaveAwb;
import com.deppon.esb.header.ESBHeader;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.transfer.common.api.server.service.IFOSSToTangYiService;
import com.deppon.foss.module.transfer.common.api.shared.define.TransferConstants;
import com.deppon.foss.module.transfer.common.api.shared.dto.AwbBeanUtils;

/**
 * 唐翼制单serivce
 */
public class FOSSToTangYiService  implements IFOSSToTangYiService{

	 /**
 	 * logger 日志
 	 */
 	private static Logger logger = Logger.getLogger(FOSSToTangYiService.class);
 	
 	private AwbQueryServiceSoap awbQueryServiceSoap;

	 /**
 	 * 登录南航id  广州
 	 */
 	private String userIdGZ;
 	
	 /**
	 * 登录南航id  深圳
	 */
	private String userIdSZ;
	
	 /**
	 * 登录南航token  广州
	 */
	private String tokenGZ;
	
	 /**
	 * 登录南航token  深圳
	 */
	private String tokenSZ;
	 
	 /**
 	 * 运单号前缀
 	 */
 	private static final String AWB_PREFIX = "784";
	 
	 /**
 	 * 运单号后缀
 	 */
 	private static final String AWB_POSTFIX = "00000000";
 	
 	/**
 	 * 系统来源
 	 */
 	private static final String SOURCE_SYSTEM = "FOSS";
 	
 	/**
 	 * message转换
 	 */
 	private static final String MESSAGE_FORMAT = "SOAP";
 	
 	/**
 	 * 版本
 	 */
 	private static final String VERSION = "0.1";
 	

	/**
	 * 查询唐翼制单
	 * @param userId 认证id
	 * @param token 唐翼校验码
	 * @param awbPrefix 运单号前缀
	 * @param awbNo 运单号 
	 * @param awbPostfix 运单号后缀(国内为00000000,国际)
	 * @return true 表示已做过唐翼制单 false表示未做过唐翼制单
	 * @author 099197-foss-zhoudejun
	 * @throws IOException 
	 * @date 2013-3-14 上午10:06:01
	 */
	@Override
	public int queryTangYiProtected(String awbNo) throws IOException {
		String deptCode = FossUserContext.getCurrentDeptCode();
		String userId = "" ;
		String token = "";
		//非广州空运总调和深圳空运总调时返回
		if(!"W0114040401".equals(deptCode) && !"W011406020203".equals(deptCode)){
			throw new IOException("广州和深圳空运总调专用!");
		}
		if("W0114040401".equals(deptCode)){  //广州总调code:W0114040401
			userId = userIdGZ;
			token = tokenGZ;//正式环境
		}
		if("W011406020203".equals(deptCode)){  //深圳总调code:W011406020203
			userId = userIdSZ;
			token = tokenSZ;//正式环境
		}
		
		IsAwbMade isAwbMade = new IsAwbMade();
		//设置运单号
		isAwbMade.setAwbNo(awbNo);
		//设置运单号前缀
		isAwbMade.setAwbPrefix(AWB_PREFIX);
		//设置运单号后缀00000000为国内，00000001为国外
		isAwbMade.setAwbPostfix(AWB_POSTFIX);
		isAwbMade.setUserId(userId);
		isAwbMade.setToken(token);
		
		Holder<ESBHeader> esbHead = createAccessHeader(awbNo,"ESB_FOSS2ESB_QUERY_AWB");
		int isMake = 0;
		//设置响应对象
		IsAwbMadeResponse response = new IsAwbMadeResponse();
		try {
			response = awbQueryServiceSoap.isAwbMade(isAwbMade,esbHead);
			//如果返回 response.isIsAwbMadeResult()为true则表示已做过唐翼单,false未做过唐翼单
			isMake = response.isIsAwbMadeResult()==true?0:1;
			logger.info("唐翼单号为:{}返回成功!"+awbNo);
		} catch (Exception e) {
			isMake = 2;
			logger.info("socket网络连接超时!");
			throw new IOException("校验失败!socket网络连接失败");
		}
		return isMake;
	}

	/**
	 * 制单服务接口
	 * @param userId 认证id
	 * @param token 唐翼校验码
	 * @param utils 运单基本信息和制单费用信息
	 * @return true 表示制单成功,false制单失败
	 * @author 099197-foss-zhoudejun
	 * @throws IOException 
	 * @date 2013-3-14 下午3:28:34
	 */
	@Override
	public boolean createTangYiProtected(AwbBeanUtils utils) throws IOException {
		String deptCode = FossUserContext.getCurrentDeptCode();
		String userId = "" ;
		String token = "";
		//非广州空运总调和深圳空运总调时返回
		if(!"W0114040401".equals(deptCode) && !"W011406020203".equals(deptCode)){
			throw new IOException("广州和深圳空运总调专用!");
		}
		if("W0114040401".equals(deptCode)){  //广州总调code:W0114040401
			userId = userIdGZ;
			token = tokenGZ;//正式环境
		}
		if("W011406020203".equals(deptCode)){  //深圳总调code:W011406020203
			userId = userIdSZ;
			token = tokenSZ;//正式环境
		}
		
		SaveAwb saveAwb = new SaveAwb();
		Awb awb = new Awb();
		//运单基本信息
		AwbBasic awbBasic = new AwbBasic();
		//制单费用基本信息
		AwbCharge awbCharge = new AwbCharge();
		try {
			//属性拷贝
			BeanUtils.copyProperties(awbBasic, utils.getAwbBasicDto());
			//当类型为BigDecimal的属性值为null时做属性拷贝会出错，
			if(utils.getAwbChargeDto().getSpaceClassDiscount() == null){
				utils.getAwbChargeDto().setSpaceClassDiscount(new BigDecimal("0"));
			}
			BeanUtils.copyProperties(awbCharge, utils.getAwbChargeDto());
			awbBasic.setAwbPrefix(AWB_PREFIX);
			awbBasic.setAwbPostfix(AWB_POSTFIX);
			awbCharge.setAwbPrefix(AWB_PREFIX);
			awbCharge.setAwbPostfix(AWB_POSTFIX);
			awbBasic.setOpID(userId);
			//设值
			awb.setAwbBasic(awbBasic);
			awb.setAwbCharge(awbCharge);
			saveAwb.setAwb(awb);
			saveAwb.setUserId(userId);
			saveAwb.setToken(token);
			
			Holder<ESBHeader> esbHead = createAccessHeader(awbBasic.getAwbNo(),"ESB_FOSS2ESB_MADE_AWB");
			
			//调用保存
			awbQueryServiceSoap.saveAwb(saveAwb,esbHead);
			logger.info("唐翼制单制作成功!");
		} catch (Exception e) {
			//日志
			logger.info("socket网络连接超时!");
			throw new IOException("制单失败!" + e.getMessage());
		}
		return false;
	}
	
	/**
	 * 设置头信息
	 * @author 099197-foss-zhoudejun
	 * @date 2013-3-14 下午4:10:26
	 */
	private Holder<ESBHeader> createAccessHeader(String awbNo,String billType) {
		ESBHeader esbheader = new ESBHeader();
		//设置交易类型
		esbheader.setExchangePattern(1);
		//message转换
		esbheader.setMessageFormat(MESSAGE_FORMAT);
		//设置版本号
		esbheader.setVersion(VERSION);
		//设置系统来源
		esbheader.setSourceSystem(SOURCE_SYSTEM);
		//设置头信息
		esbheader.setVersion(TransferConstants.ESB_ACCESSHEADER_VERSION);
		//ESB服务编码
		esbheader.setEsbServiceCode(billType);
		//设置业务ID
		esbheader.setBusinessId(awbNo);
		//设置请求id
		esbheader.setRequestId(UUID.randomUUID().toString());
		return new Holder<ESBHeader>(esbheader);
	}	
	
	 public AwbQueryServiceSoap getAwbQueryServiceSoap() {
		return awbQueryServiceSoap;
	}

	public String getUserIdGZ() {
		return userIdGZ;
	}

	public void setUserIdGZ(String userIdGZ) {
		this.userIdGZ = userIdGZ;
	}

	public String getUserIdSZ() {
		return userIdSZ;
	}

	public void setUserIdSZ(String userIdSZ) {
		this.userIdSZ = userIdSZ;
	}

	public String getTokenGZ() {
		return tokenGZ;
	}

	public void setTokenGZ(String tokenGZ) {
		this.tokenGZ = tokenGZ;
	}

	public String getTokenSZ() {
		return tokenSZ;
	}

	public void setTokenSZ(String tokenSZ) {
		this.tokenSZ = tokenSZ;
	}

	public void setAwbQueryServiceSoap(AwbQueryServiceSoap awbQueryServiceSoap) {
		this.awbQueryServiceSoap = awbQueryServiceSoap;
	}
}