/*******************************************************************************
 * Copyright 2013 BSE TEAM
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: bse-common
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/common/server/service/impl/SMSSendLogService.java
 * 
 * FILE NAME        	: SMSSendLogService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.common.server.service.impl;

import java.io.IOException;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.FlagConstants;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.common.api.server.dao.ISMSSendLogDao;
import com.deppon.foss.module.base.common.api.server.service.ISMSSendLogService;
import com.deppon.foss.module.base.common.api.shared.domain.SMSSendLogEntity;
import com.deppon.foss.module.base.common.api.shared.domain.SmsAuthorityInfo;
import com.deppon.foss.module.base.common.api.shared.domain.SmsInfo;
import com.deppon.foss.module.base.common.api.shared.domain.SmsResultInfo;
import com.deppon.foss.module.base.common.api.shared.exception.SMSSendLogException;
import com.deppon.foss.module.base.common.api.shared.util.MD5Encrypt;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.util.define.FossConstants;

/**
 * 用来操作交互“短信信息”的数据库对应数据访问Service接口实现类：无SUC
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:100847-foss-GaoPeng,date:2012-11-19 下午8:47:29
 * </p>
 * 
 * @author 100847-foss-GaoPeng
 * @date 2012-11-19 下午8:47:29
 * @since
 * @version
 */
public class SMSSendLogService implements ISMSSendLogService {

	/**
	 * 日志.
	 */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(SMSSendLogService.class);
	
	/**
	 * 组织服务接口
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;

	// "短信信息"DAO
	private ISMSSendLogDao smsSendLogDao;

	// "短信验证信息"
	private SmsAuthorityInfo smsAuthority;
	
	/**
	 * 配置参数的service
	 */
	private IConfigurationParamsService configurationParamsService;

	/**
	 * <p>
	 * 新增一个“短信信息”实体入库（忽略实体中是否存在空值）
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-11-19 下午3:43:06
	 * @param smsFailLog
	 *            “短信信息”实体
	 * @return 1：成功；0：失败
	 * @throws SMSSendLogException
	 * @see com.deppon.foss.module.base.common.api.server.service.ISMSSendLogService#addSMSSendLog(com.deppon.foss.module.base.common.api.shared.domain.SMSSendLogEntity)
	 */
	@Override
	public int addSMSSendLog(SMSSendLogEntity smsSendLog)
			throws SMSSendLogException {
		if (null == smsSendLog) {
			throw new SMSSendLogException("", "短信息为空");
		}
		return smsSendLogDao.addSMSSendLog(smsSendLog);
	}

	/**
	 * <p>
	 * 新增一个“短信信息”实体入库 （只选择实体中非空值）
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-11-19 下午3:43:30
	 * @param smsFailLog
	 *            “短信信息”实体
	 * @return 1：成功；0：失败
	 * @throws SMSSendLogException
	 * @see com.deppon.foss.module.base.common.api.server.service.ISMSSendLogService#addSMSSendLogBySelective(com.deppon.foss.module.base.common.api.shared.domain.SMSSendLogEntity)
	 */
	@Override
	public int addSMSSendLogBySelective(SMSSendLogEntity smsSendLog)
			throws SMSSendLogException {
		if (null == smsSendLog) {
			throw new SMSSendLogException("", "短信息为空");
		}
		return smsSendLogDao.addSMSSendLogBySelective(smsSendLog);
	}

	/**
	 * <p>
	 * 根据“短信信息”记录唯一标识删除（物理删除）一条“短信信息”记录
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-11-19 下午3:43:34
	 * @param id
	 *            记录唯一标识
	 * @return 1：成功；0：失败
	 * @throws SMSSendLogException
	 * @see com.deppon.foss.module.base.common.api.server.service.ISMSSendLogService#deleteSMSSendLog(java.lang.String)
	 */
	@Override
	public int deleteSMSSendLog(String id) throws SMSSendLogException {
		if (StringUtils.isBlank(id)) {
			throw new SMSSendLogException("", "ID为空");
		}
		return smsSendLogDao.deleteSMSSendLog(id);
	}

	/**
	 * <p>
	 * 修改一个“短信信息”实体入库 （只选择实体中非空值）
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-11-19 下午3:43:41
	 * @param smsFailLog
	 *            “短信信息”实体
	 * @return 1：成功；0：失败
	 * @throws SMSSendLogException
	 * @see com.deppon.foss.module.base.common.api.server.service.ISMSSendLogService#updateSMSSendLogBySelective(com.deppon.foss.module.base.common.api.shared.domain.SMSSendLogEntity)
	 */
	@Override
	public int updateSMSSendLogBySelective(SMSSendLogEntity smsSendLog)
			throws SMSSendLogException {
		if (null == smsSendLog) {
			throw new SMSSendLogException("", "短信息为空");
		}
		if (StringUtils.isBlank(smsSendLog.getId())) {
			throw new SMSSendLogException("", "ID为空");
		}
		return smsSendLogDao.updateSMSSendLogBySelective(smsSendLog);
	}

	/**
	 * <p>
	 * 修改一个“短信信息”实体入库（忽略实体中是否存在空值）
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-11-19 下午3:43:44
	 * @param smsFailLog
	 *            “短信信息”实体
	 * @return 1：成功；0：失败
	 * @throws SMSSendLogException
	 * @see com.deppon.foss.module.base.common.api.server.service.ISMSSendLogService#updateSMSSendLog(com.deppon.foss.module.base.common.api.shared.domain.SMSSendLogEntity)
	 */
	@Override
	public int updateSMSSendLog(SMSSendLogEntity smsSendLog)
			throws SMSSendLogException {
		if (null == smsSendLog) {
			throw new SMSSendLogException("", "短信息为空");
		}
		if (StringUtils.isBlank(smsSendLog.getId())) {
			throw new SMSSendLogException("", "ID为空");
		}
		return smsSendLogDao.updateSMSSendLog(smsSendLog);
	}

	/**
	 * <p>
	 * 根据“短信信息”记录唯一标识查询出一条“短信信息”记录
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-11-19 下午3:43:38
	 * @param smsFailLog
	 *            “短信信息”参数实体
	 * @return “短信信息”实体
	 * @throws SMSSendLogException
	 * @see com.deppon.foss.module.base.common.api.server.service.ISMSSendLogService#querySMSSendLog(com.deppon.foss.module.base.common.api.shared.domain.SMSSendLogEntity)
	 */
	@Override
	public SMSSendLogEntity querySMSSendLog(SMSSendLogEntity smsSendLog)
			throws SMSSendLogException {
		if (null == smsSendLog) {
			return null;
		}
		return smsSendLogDao.querySMSSendLog(smsSendLog);
	}

	/**
	 * <p>
	 * 发送一条“短信信息”到短信平台
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-11-19 下午8:46:51
	 * @param smsSendLog
	 *            “短信信息”实体
	 * @throws SMSSendLogException
	 * @see com.deppon.foss.module.base.common.api.server.service.ISMSSendLogService#sendSMS(com.deppon.foss.module.base.common.api.shared.domain.SMSSendLogEntity)
	 */
	@Override
	public void sendSMS(SMSSendLogEntity smsSendLog) throws SMSSendLogException {
		if (smsSendLog == null) {
			throw new SMSSendLogException("", "发送对象不能为null");
		}

		if (StringUtils.isBlank(smsSendLog.getSenddeptCode())) {
			throw new SMSSendLogException("", "发送部门编码不能为空");
		}
		if (StringUtils.isBlank(smsSendLog.getSenderCode())) {
			throw new SMSSendLogException("", "发送人员编码不能为空");
		}

		SmsInfo smsInfo = new SmsInfo();
		smsInfo.setMobile(smsSendLog.getMobile());
		smsInfo.setMsgContent(smsSendLog.getContent());
		// SMS提供接口方指定说该参数为部门标杆编码 modify By 101911-zhouChunlai 2013-05-06
		// smsInfo.setSendDept(smsSendLog.getSenddept());
		OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoService
				.queryOrgAdministrativeInfoByCodeClean(smsSendLog
						.getSenddeptCode());
		if (null != orgEntity) {
			smsInfo.setSendDept(orgEntity.getUnifiedCode());
		} else {
			throw new SMSSendLogException("", "发送部门编码不存在");
		}
		// SMS提供接口方指定说该参数为员工编码 modify By 101911-zhouChunlai 2013-05-06
		// smsInfo.setSender(smsSendLog.getSender());
		smsInfo.setSender(smsSendLog.getSenderCode());
		smsInfo.setMsgType(smsSendLog.getMsgtype());
		smsInfo.setMsgSource(smsSendLog.getMsgsource());
		smsInfo.setUnionId(smsSendLog.getUnionId());
		smsInfo.setWaybillNo(smsSendLog.getWaybillNo());
		if (StringUtils.equals(smsSendLog.getServiceType(),
				NumberConstants.NUMERAL_S_ONE)
				|| StringUtils.equals(smsSendLog.getServiceType(),
						NumberConstants.NUMERAL_S_TWO)
				|| StringUtils.equals(smsSendLog.getServiceType(),
						NumberConstants.NUMERAL_S_THREE)) {
			smsInfo.setServiceType(smsSendLog.getServiceType());
		} else {
			throw new SMSSendLogException("", "指定的服务类型不存在");
		}
		if (null != smsSendLog.getLastsendTime()) {
			smsInfo.setLatestSendTime(new Timestamp(smsSendLog
					.getLastsendTime().getTime()));
		}
		if (null != smsSendLog.getSendTime()) {
			smsInfo.setSendTime(new Timestamp(smsSendLog.getSendTime().getTime()));
		}
		try {
			 
			 LOGGER.info("尝试通过WS发送短信,接受手机号：" + smsInfo.getMobile() + ", 内容："
			 + smsInfo.getMsgContent()); 
			 SmsResultInfo smsResultInfo = sendSMSByWs(smsInfo);
			LOGGER.info("通过WS发送短信未出现异常,接受手机号：" + smsInfo.getMobile() + ", 内容："
					+ smsInfo.getMsgContent());
			if(null == smsResultInfo){
				throw new SMSSendLogException("", "通过WS发送短信出错");
			}
			/*
			 * 发送到队列后存入本地数据库：设置初始化"验证成功"
			 */
			if(StringUtils.equals(FlagConstants.SMS_REASON_CODE, smsResultInfo.getResultCode())){
				smsSendLog.setIsValidated(FossConstants.ACTIVE);
			}else{
				smsSendLog.setIsValidated(FossConstants.INACTIVE);				
			}
			smsSendLog.setReason(smsResultInfo.getReason());			
			smsSendLog.setResultCode(smsResultInfo.getResultCode());
			addSMSSendLog(smsSendLog);
		} catch (Exception e) {
			LOGGER.error("调用短信平台接口发送短信出错", e);
			throw new SMSSendLogException("", e.getMessage());
		}
	}
	/**
	 * <p>
	 * 发送短信
	 * </p>
	 * 
	 * @author 094463-foss-xieyantao
	 * @date 2013-6-8 下午6:55:01
	 * @param smsList
	 *            短信列表
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 * @see com.deppon.foss.module.base.common.api.server.service.ISMSSendLogService#sendMsgs(java.util.List)
	 */
	private SmsResultInfo sendSMSByWs(SmsInfo smsInfo) throws JsonGenerationException, JsonMappingException, HttpException,IOException,SMSSendLogException{
		List<SmsInfo> smsList = new ArrayList<SmsInfo>();
		smsList.add(smsInfo);
		HttpClient httpClient = new HttpClient(); 
		PostMethod postMethod = new PostMethod(smsAuthority.getWsUrl());
		ObjectMapper mapper = new ObjectMapper();
		StringWriter stringWriter = new StringWriter();
		mapper.writeValue(stringWriter, smsList); 
		String digest = MD5Encrypt.encrypt(smsAuthority.getUsername() + smsAuthority.getPassword());
		postMethod.getParams().setContentCharset("UTF-8");
		postMethod.setRequestBody(new NameValuePair[] {
				new NameValuePair("data_digest", digest),
				new NameValuePair("smsInfo", stringWriter.getBuffer()
						.toString()) }); 
		//获取短信平台请求超时code以及参数配置时间
				String code ="BAS_SMS_TIMEOUT";
				ConfigurationParamsEntity entity =configurationParamsService.queryConfigurationParamsOneByCode(code);
				if(entity !=null){
					httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(Integer.parseInt(entity.getConfValue()));
					httpClient.getHttpConnectionManager().getParams().setSoTimeout(Integer.parseInt(entity.getConfValue()));
				}
				else{
					throw new SMSSendLogException("", "短信平台请求超时时间参数没有配置");
				}
		httpClient.executeMethod(postMethod);
		SmsResultInfo smsResultInfo = mapper.readValue(postMethod.getResponseBody(),SmsResultInfo.class);
		
		return smsResultInfo;
	}

	/**
	 * @param smsSendLogDao
	 *            the smsSendLogDao to set
	 */
	public void setSmsSendLogDao(ISMSSendLogDao smsSendLogDao) {
		this.smsSendLogDao = smsSendLogDao;
	}

	/**
	 * @param smsAuthority
	 *            the smsAuthority to set
	 */
	public void setSmsAuthority(SmsAuthorityInfo smsAuthority) {
		this.smsAuthority = smsAuthority;
	}

	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	public void setConfigurationParamsService(
			IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}
}
