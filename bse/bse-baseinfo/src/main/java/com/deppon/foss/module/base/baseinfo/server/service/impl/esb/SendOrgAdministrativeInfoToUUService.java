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
 * PROJECT NAME	: bse-baseinfo
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/esb/SendOrgAdministrativeInfoService.java
 * 
 * FILE NAME        	: SendOrgAdministrativeInfoService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */ 
package com.deppon.foss.module.base.baseinfo.server.service.impl.esb;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.api.ESBJMSAccessor;
import com.deppon.esb.api.domain.AccessHeader;
import com.deppon.esb.core.exception.ESBException;
import com.deppon.esb.pojo.transformer.json.SendOrgInfoToUURequestTrans;
import com.deppon.foss.base.util.DataTrans;
import com.deppon.foss.base.util.define.SymbolConstants;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IOrgAdministrativeInfoToUUDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISendOrgAdministrativeInfoToUUService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoToUUEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.SynchronousExternalSystemException;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.uums.inteface.domain.OrganizationInfo;
import com.deppon.uums.inteface.domain.SyncOrganizationToUURequest;
import com.eos.system.utility.StringUtil;

/**
 * 配合主数据项目推送FOSS组织信息给UUMS系统
 * @author 187862-dujunhui
 * @date 2015-4-10 上午10:45:42
 * @version 1.0
 */
public class SendOrgAdministrativeInfoToUUService implements
	ISendOrgAdministrativeInfoToUUService {

	/**
	 * 加载日志
	 */
    private static final Logger LOGGER = LoggerFactory.getLogger(SendOrgAdministrativeInfoToUUService.class);
    /**
     * 服务编码
     */
    private static final String SYNC_ORGANIZATION_SERVE_CODE = "ESB_FOSS2ESB_REVERSE_ADMINORG_FOSS_MDM";
    /**
     * 版本编号
     */
    private static final String version = "1.0";
    /**
	 * 操作组织中间表的Dao
	 */
	private IOrgAdministrativeInfoToUUDao orgAdministrativeInfoToUUDao;

    public void setOrgAdministrativeInfoToUUDao(
			IOrgAdministrativeInfoToUUDao orgAdministrativeInfoToUUDao) {
		this.orgAdministrativeInfoToUUDao = orgAdministrativeInfoToUUDao;
	}

	/**
     * 同步“行政组织信息”到UUMS系统
     * @author 187862-dujunhui
     * @date 2015-4-10 上午11:12:23
     */
    @Override
    public void sendOrgAdministrativeInfoToUU(){
	
		// 声明要传递的值
		SyncOrganizationToUURequest request = new SyncOrganizationToUURequest();
		
		StringBuilder versionNos=new StringBuilder();
		StringBuilder codes=new StringBuilder();
		
		List<OrganizationInfo> esbInfos = request.getOrganizationInfo();
		//查询组织中间表待发送信息
		OrgAdministrativeInfoToUUEntity queryEntity=new OrgAdministrativeInfoToUUEntity();
		List<String> statusList=new ArrayList<String>();
		statusList.add(DictionaryValueConstants.ORGINFO_TOUU_SENDSTATUS_WAITING_TO_SEND);
		queryEntity.setConditionSendStatusList(statusList);
		List<OrgAdministrativeInfoToUUEntity> entityList=orgAdministrativeInfoToUUDao.
				queryOrgAdministrativeInfoByCondition(queryEntity);
		//定义idList用于同步前将发送状态修改为发送中
		List<String> idList=new ArrayList<String>();
		if(entityList!=null){
			for(OrgAdministrativeInfoToUUEntity fossEntity : entityList){
			    if(fossEntity == null){
			    	continue;
			    }
	
			    idList.add(fossEntity.getId());
			    
			    versionNos.append(SymbolConstants.EN_COMMA);
			    if(fossEntity.getModifyDate()!=null){
			    	versionNos.append(fossEntity.getModifyDate().getTime());
			    }
			    codes.append(SymbolConstants.EN_COMMA);
			    codes.append(fossEntity.getCode());
			    
			    esbInfos.add(transFossToEsb(fossEntity));
			}
	
			AccessHeader header = new AccessHeader();
	
			header.setBusinessId(codes.toString());
			// 服务编码
			header.setEsbServiceCode(SYNC_ORGANIZATION_SERVE_CODE);
			header.setVersion(version);
			header.setBusinessDesc1("同步行政组织接口到UUMS");
			header.setBusinessDesc2(versionNos.toString());
	
			//同步前将发送状态修改为发送中
			LOGGER.error("==========MDM:开始=====FOSS修改中间表为发送中======");
	
			orgAdministrativeInfoToUUDao.updateOrgAdministrativeInfoToUUByIds(idList,null,DictionaryValueConstants.
					ORGINFO_TOUU_SENDSTATUS_SENDING);
			
			LOGGER.error("==========MDM:结束=====FOSS修改中间表为发送中======");
	
			LOGGER.error("==========MDM:new SendOrgInfoToUURequestTrans()====="+(new SendOrgInfoToUURequestTrans() == null)+"======");
			
				
				// 发送预付款申请到ESB
				try {
					LOGGER.info("FOSS开始发送同步行政组织到UUMS："+new SendOrgInfoToUURequestTrans().fromMessage(request));
					ESBJMSAccessor.asynReqeust(header, request);
					LOGGER.info("FOSS结束发送同步 行政组织到UUMS："+new SendOrgInfoToUURequestTrans().fromMessage(request));
				} catch (ESBException e) {
					 throw new SynchronousExternalSystemException(e.getMessage());
				}
				
			
			
		}
		//时间超过半个小时长锁的处理逻辑:扫描中间表，判断更新时间与当前时间之差超过半小时，且发送状态为sending
		List<OrgAdministrativeInfoToUUEntity>  sendingEntityList=orgAdministrativeInfoToUUDao.querySendingStatusLongTimeEntity();
		//修改状态为待发送
		if(CollectionUtils.isNotEmpty(sendingEntityList)){
			List<String> sendingIDList=new ArrayList<String>();
			for(OrgAdministrativeInfoToUUEntity sendingIDEntity:sendingEntityList){
				sendingIDList.add(sendingIDEntity.getId());
			}
			orgAdministrativeInfoToUUDao.updateOrgAdministrativeInfoToUUByIds(sendingIDList,null,DictionaryValueConstants.
					ORGINFO_TOUU_SENDSTATUS_WAITING_TO_SEND);
		}
    }
    
    /**
     * FOSS对象转ESB
     * @author 187862-dujunhui
     * @date 2015-4-10 下午4:35:21
     */
    private OrganizationInfo transFossToEsb(OrgAdministrativeInfoToUUEntity fossEntity){
		
	    if(fossEntity == null){
		    return null;
		}
		
		OrganizationInfo esbInfo = new OrganizationInfo();
		
		// ID
		esbInfo.setId(fossEntity.getId());
		// 组织编码
		esbInfo.setOrgCode(fossEntity.getCode());
		// 组织名称
		esbInfo.setOrgName(fossEntity.getName());
		// 是否空运总调
		esbInfo.setAirDispatch(fossEntity.getAirDispatch());
		// 理货业务类型
		esbInfo.setArrangeBizType(fossEntity.getArrangeBizType());
		// 理货部门服务外场组织编码
		esbInfo.setArrangeOutfield(fossEntity.getArrangeOutfield());
		// 是否集中开单组
		esbInfo.setBillingGroup(fossEntity.getBillingGroup());
		//补码简称
		esbInfo.setComplementSimpleName(fossEntity.getComplementSimpleName());
		// 国家地区
		esbInfo.setCountryRegion(fossEntity.getCountryRegion());
		// 派送排单服务外场组织编码
		esbInfo.setDeliverOutfield(fossEntity.getDeliverOutfield());
		// 部门服务区坐标编号
		esbInfo.setDepCoordinate(fossEntity.getDepCoordinate());
		// 部门面积
		if(fossEntity.getDeptArea()!=null){
			esbInfo.setDeptArea(DataTrans.bigDecimalToDouble(fossEntity.getDeptArea()));
		}
		//城市编码
		esbInfo.setCityCode(fossEntity.getCityCode());
		//区县编码
		esbInfo.setCountyCode(fossEntity.getCountyCode());
		// 省份编码
		esbInfo.setProvCode(fossEntity.getProvCode());
		// 是否车队调度组
		esbInfo.setDispatchTeam(fossEntity.getDispatchTeam());
		// 是否可空运配载
		esbInfo.setDoAirDispatch(fossEntity.getDoAirDispatch());
		//是否快递大区
		esbInfo.setIsExpressRegion(fossEntity.getExpressBigRegion());
		//是否快递分部
		esbInfo.setIsExpressBranch(fossEntity.getExpressBranch());
		//是否快递点部
		esbInfo.setIsExpressPart(fossEntity.getExpressPart());
		//是否快递虚拟营业部
		esbInfo.setIsExpressSalesDepartment(fossEntity.getExpressSalesDepartment());
		//是否快递分拣
		esbInfo.setIsExpressSorting(fossEntity.getExpressSorting());
		// 是否理货
		esbInfo.setIsArrangeGoods(fossEntity.getIsArrangeGoods());
		// 是否派送排单
		esbInfo.setIsDeliverSchedule(fossEntity.getIsDeliverSchedule());
		// 是否实体财务部
		esbInfo.setIsEntityFinance(fossEntity.getIsEntityFinance());
		// 是否大区
		esbInfo.setBigRegion(fossEntity.getBigRegion());
		// 是否事业部
		esbInfo.setDivision(fossEntity.getDivision());
		//是否保安组 
		esbInfo.setIsSecurity(fossEntity.getIsSecurity());
		// 是否营业小区
		esbInfo.setSmallRegion(fossEntity.getSmallRegion());
		// 组织拼音
		esbInfo.setPinYin(fossEntity.getPinyin());
		// 是否营业部派送部
		esbInfo.setSalesDepartment(fossEntity.getSalesDepartment());
		// 是否车队
		esbInfo.setTransDepartment(fossEntity.getTransDepartment());
		// 是否车队组
		esbInfo.setTransTeam(fossEntity.getTransTeam());
		// 是否外场
		esbInfo.setTransferCenter(fossEntity.getTransferCenter());
		// 事业部编码
		esbInfo.setDivisionCode(fossEntity.getDivisionCode());
		// 部门简称
		esbInfo.setOrgSimpleName(fossEntity.getOrgSimpleName());
		// 组织标杆编码
		esbInfo.setOrgBenchMarkCode(fossEntity.getUnifiedCode());
		// 联系电话
		esbInfo.setOrgPhone(fossEntity.getOrgPhone());
		// 传真
		esbInfo.setOrgFax(fossEntity.getOrgFax());
		// 邮编号码
		esbInfo.setOrgZipCode(fossEntity.getOrgZipCode());
		// 部门地址
		esbInfo.setOrgAddress(fossEntity.getOrgAddress());
		//是否经营本部
		esbInfo.setIsManageDepartment(fossEntity.getIsManageDepartment());
		// 创建人工号
		esbInfo.setCreateUser(fossEntity.getCreateUser());
		// 创建时间
		esbInfo.setCreateTime(fossEntity.getCreateDate());
		//修改人工号
		esbInfo.setModifyUser(fossEntity.getModifyUser());
		// 更新时间
		esbInfo.setModifyTime(fossEntity.getCreateDate());
		//异常次数
		esbInfo.setFailCount(StringUtil.isEmpty(fossEntity.getFailCount())? "0":fossEntity.getFailCount());
		//操作类型
		esbInfo.setOperateType(fossEntity.getOperateType());
		//操作时间
		esbInfo.setOperateTime(fossEntity.getModifyDateOfUU());
	
		return esbInfo;
    }
    

}
