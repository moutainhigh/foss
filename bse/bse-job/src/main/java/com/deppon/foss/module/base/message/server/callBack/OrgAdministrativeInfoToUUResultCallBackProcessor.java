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
 * PROJECT NAME	: bse-baseinfo-web
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/callback/OrgAdministrativeInfoResultCallBackProcessor.java
 * 
 * FILE NAME        	: OrgAdministrativeInfoResultCallBackProcessor.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.message.server.callBack;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.core.exception.ESBException;
import com.deppon.esb.core.process.ErrorResponse;
import com.deppon.esb.core.process.ICallBackProcess;
import com.deppon.esb.pojo.transformer.json.SendOrgInfoToUUResponseTrans;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IOrgAdministrativeInfoToUUDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EsbCallBackLog;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoToUUEntity;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.uums.inteface.domain.SyncOrganizationToUUResponse;
import com.deppon.uums.inteface.domain.returninfo.ReturnInfo;
import com.eos.system.utility.StringUtil;

/**
 * 配合主数据项目用来存储组织信息推送给UUMS系统后的结果回调
 * @author 187862-dujunhui
 * @date 2015-4-13 下午1:54:15
 */
public class OrgAdministrativeInfoToUUResultCallBackProcessor implements
	ICallBackProcess {

	/**
	 * 加载日志文件
	 */
    private static final Logger LOGGER = LoggerFactory
	    .getLogger(OrgAdministrativeInfoToUUResultCallBackProcessor.class);
    /**
	 * 操作组织中间表的Dao
	 */
	private IOrgAdministrativeInfoToUUDao orgAdministrativeInfoToUUDao;

    public void setOrgAdministrativeInfoToUUDao(
			IOrgAdministrativeInfoToUUDao orgAdministrativeInfoToUUDao) {
		this.orgAdministrativeInfoToUUDao = orgAdministrativeInfoToUUDao;
	}

	@Override
    public void callback(Object response) throws ESBException {
	if (null != response) {
	    SyncOrganizationToUUResponse responseObj = (SyncOrganizationToUUResponse) response;
	    LOGGER.info("同步行政组织，收到回调信息：\n"
		    + new SendOrgInfoToUUResponseTrans()
			    .fromMessage(responseObj));
	    List<ReturnInfo> returnInfoList = responseObj.getReturnInfos();
	    for (ReturnInfo info : returnInfoList) {
			EsbCallBackLog callBackLog = new EsbCallBackLog();
			callBackLog.setId("返回ID："+info.getId());
			callBackLog.setReason("异常信息："+info.getReason());
			callBackLog.setRemark("同步状态："+info.getStatus());
			callBackLog.setCreateTime(new Date());
			//操作中间表
			OrgAdministrativeInfoToUUEntity updateEntity=new OrgAdministrativeInfoToUUEntity();
			updateEntity.setId(info.getId());
			//根据ID查询中间表本条信息
			List<OrgAdministrativeInfoToUUEntity> entityList=orgAdministrativeInfoToUUDao.
					queryOrgAdministrativeInfoByCondition(updateEntity);
			OrgAdministrativeInfoToUUEntity dataBaseEntity=null;
			if(entityList!=null){
				dataBaseEntity=entityList.get(0);
				if(StringUtil.equal(info.getStatus(), "0")){//同步失败
					//修改数据库中本ID对应数据的failCount和发送状态
					if(StringUtil.isEmpty(dataBaseEntity.getFailCount())){
						dataBaseEntity.setFailCount("0");
					}
					updateEntity.setFailCount((Integer.valueOf(dataBaseEntity.getFailCount())+1)+"");//异常次数加1
					if(Integer.valueOf(updateEntity.getFailCount())>NumberConstants.NUMBER_3){//异常次数大于3则标识为发送失败
						updateEntity.setSendStatus(DictionaryValueConstants.ORGINFO_TOUU_SENDSTATUS_SEND_FAIL);
					}else{//异常次数不大于3次则继续待发送
						updateEntity.setSendStatus(DictionaryValueConstants.ORGINFO_TOUU_SENDSTATUS_WAITING_TO_SEND);
					}
					orgAdministrativeInfoToUUDao.updateOrgAdministrativeInfoToUU(updateEntity);
				}else if(StringUtil.equal(info.getStatus(), "1")){//同步成功
					updateEntity.setSendStatus(DictionaryValueConstants.ORGINFO_TOUU_SENDSTATUS_SEND_SUCCESS);
					orgAdministrativeInfoToUUDao.updateOrgAdministrativeInfoToUU(updateEntity);
				}
			}
	    }
	}
    }

    @Override
    public void errorHandler(Object errorResponse) throws ESBException {
	ErrorResponse info = (ErrorResponse)errorResponse;
	StringBuilder sb =new StringBuilder();
	sb.append(info.getBusinessId()).append(",描述1：")
		.append(info.getBusinessDesc1()).append(",描述2：")
		.append(info.getBusinessDesc2()).append(",描述3：")
		.append(info.getBusinessDesc3());

	LOGGER.error("ESB处理错误", sb.toString());
    }
}
