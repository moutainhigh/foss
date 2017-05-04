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

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.deppon.esb.api.ESBJMSAccessor;
import com.deppon.esb.api.domain.AccessHeader;
import com.deppon.esb.core.exception.ESBException;
import com.deppon.esb.pojo.transformer.jaxb.SyncOrganizationRequestTrans;
import com.deppon.foss.base.util.DataTrans;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.base.util.define.SymbolConstants;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IEsbErrorLoggingDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISendOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EsbErrorLogging;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.SynchronousExternalSystemException;
import com.deppon.foss.util.define.FossConstants;
import com.deppon.ows.inteface.domain.OrganizationInfo;
import com.deppon.ows.inteface.domain.SyncOrganizationRequest;

/**
 * 
 * @date Mar 12, 2013 2:49:17 PM
 * @version 1.0
 */
public class SendOrgAdministrativeInfoService implements
	ISendOrgAdministrativeInfoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SendOrgAdministrativeInfoService.class);

    
    /**
     * 服务编码
     */
    private static final String SYNC_ORGANIZATION_SERVE_CODE = "ESB_FOSS2ESB_SYNC_ORGANIZATION";

    /**
     * 版本编号
     */
    private static final String version = "1.0";
    private IEsbErrorLoggingDao esbErrorLoggingDao;
	public void setEsbErrorLoggingDao(IEsbErrorLoggingDao esbErrorLoggingDao) {
		this.esbErrorLoggingDao = esbErrorLoggingDao;
	}
    /**
     * 同步“行政组织信息”到官网
     * 
     * @author 087584-foss-lijun
     * @date 2013-1-15 下午4:01:25
     */
    @Override
    public void sendOrgAdministrativeInfoToOfficialWebsite(
	    List<OrgAdministrativeInfoEntity> entitys)  {
	
	// 声明要传递的值
	SyncOrganizationRequest request = new SyncOrganizationRequest();
	
	StringBuilder versionNos=new StringBuilder();
	StringBuilder codes=new StringBuilder();
	
	List<OrganizationInfo> esbInfos = request.getOrganizationInfo();
	for(OrgAdministrativeInfoEntity fossEntity : entitys){
	    if(fossEntity == null){
		continue;
	    }

	    versionNos.append(SymbolConstants.EN_COMMA);
	    versionNos.append(fossEntity.getVersionNo());
	    codes.append(SymbolConstants.EN_COMMA);
	    codes.append(fossEntity.getCode());
	    
	    esbInfos.add(transFossToEsb(fossEntity));
	}

	AccessHeader header = new AccessHeader();

	header.setBusinessId(codes.toString());
	// 服务编码
	header.setEsbServiceCode(SYNC_ORGANIZATION_SERVE_CODE);
	// 处理工作流审批结果
	header.setVersion(version);
	header.setBusinessDesc1("同步行政组织接口 到其它平台");
	header.setBusinessDesc2(versionNos.toString());
	long startTime = System.currentTimeMillis();
	EsbErrorLogging erlog = new EsbErrorLogging();
	try {
		erlog.setParamenter(new SyncOrganizationRequestTrans().fromMessage(request));
		erlog.setRequestSystem("ESB");
		erlog.setRequestTime(new Date());
	    erlog.setMethodDesc("同步行政组织接口 到其它平台");
		erlog.setMethodName(this.getClass().getName()+".sendOrgAdministrativeInfoToOfficialWebsite");
		LOGGER.info("start send t_bas_org info to other platform.FOSS开始发送同步 行政组织 到其它平台 的报文："
		+new SyncOrganizationRequestTrans().fromMessage(request));
		// 发送预付款申请到ESB
		ESBJMSAccessor.asynReqeust(header, request);
		long responseTime = (System.currentTimeMillis()-startTime)/NumberConstants.NUMBER_1000; 
		if(responseTime>FossConstants.MAX_RESPONSE_TIME){
			erlog.setCreateTime(new Date());
			erlog.setErrorMessage("响应超时");
			erlog.setResponseTime(responseTime);
			esbErrorLoggingDao.addErrorMessage(erlog);
		}
		LOGGER.info("end send t_bas_org info to other platform.FOSS结束发送同步 行政组织 到其它平台 的报文："
		+new SyncOrganizationRequestTrans().fromMessage(request));
} catch (ESBException e) {
	LOGGER.error(e.getMessage(), e);
	erlog.setResponseTime((System.currentTimeMillis()-startTime));
	erlog.setCreateTime(new Date());
	StringWriter sw = new StringWriter();
	PrintWriter pw = new PrintWriter(sw);
	e.printStackTrace(pw);
	erlog.setErrorMessage(sw.toString());
	esbErrorLoggingDao.addErrorMessage(erlog);
	throw new SynchronousExternalSystemException(e.getMessage());
}
	
    }
    
    /**
     * foss对象转ESB
     * 
     * @author 087584-foss-lijun
     * @date 2013-1-21 下午4:44:19
     */
    private OrganizationInfo transFossToEsb(OrgAdministrativeInfoEntity fossEntity){
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
	// 拼音
	esbInfo.setPinYin(fossEntity.getPinyin());
	// 组织标杆编码
	esbInfo.setUnifiedCode(fossEntity.getUnifiedCode());
	// 组织负责人
	esbInfo.setLeader(fossEntity.getLeader());
	// 组织负责人工号
	esbInfo.setPrincipalNo(fossEntity.getPrincipalNo());
	// 上级组织名称
	esbInfo.setParentOrgName(fossEntity.getParentOrgName());
	// 上级组织标杆编码
	esbInfo.setParentOrgUnifiedCode(fossEntity.getParentOrgUnifiedCode());
	// 所属子公司编码
	esbInfo.setSubsidiaryCode(fossEntity.getSubsidiaryCode());
	// 所属成本中心编码
	esbInfo.setCostCenterCode(fossEntity.getCostCenterCode());
	// 组织状态
	esbInfo.setStatus(fossEntity.getStatus());
	// 作废日期
	esbInfo.setEndTime(fossEntity.getEndTime());
	// 启用日期
	esbInfo.setBeginTime(fossEntity.getBeginTime());
	// 是否事业部
	esbInfo.setDivision(fossEntity.getDivision());
	// 事业部编码
	esbInfo.setDivisionCode(fossEntity.getDivisionCode());
	// 是否大区
	esbInfo.setBigRegion(fossEntity.getBigRegion());
	// 是否小区
	esbInfo.setSmallRegion(fossEntity.getSmallRegion());
	//是否快递大区
	
	//测试使用
	//esbInfo.setIsExpressRegion("Y");
	esbInfo.setIsExpressRegion(fossEntity.getExpressBigRegion());
	//是否虚拟营业部 （根据 BUGKD-1696  FOSS组织信息同步接口问题导致官网查询显示虚拟营业部）
    esbInfo.setIsVirualLading(fossEntity.getExpressSalesDepartment());
	
	// 部门地址
	esbInfo.setAddress(fossEntity.getAddress());
	// 部门面积
	esbInfo.setDeptArea(DataTrans.bigDecimalToDouble(fossEntity.getDeptArea()));
	// 省份
	esbInfo.setProvCode(fossEntity.getProvCode());
	// 城市
	esbInfo.setCityCode(fossEntity.getCityCode());
	// 区县
	esbInfo.setCountyCode(fossEntity.getCountyCode());
	// 是否营业部派送部
	esbInfo.setSalesDepartment(fossEntity.getSalesDepartment());
	// 是否外场
	esbInfo.setTransferCenter(fossEntity.getTransferCenter());
	// 是否可空运配载
	esbInfo.setDoAirDispatch(fossEntity.getDoAirDispatch());
	// 是否车队
	esbInfo.setTransDepartment(fossEntity.getTransDepartment());
	// 是否车队调度组
	esbInfo.setDispatchTeam(fossEntity.getDispatchTeam());
	// 是否集中开单组
	esbInfo.setBillingGroup(fossEntity.getBillingGroup());
	// 是否车队组
	esbInfo.setTransTeam(fossEntity.getTransTeam());
	// 是否派送排单
	esbInfo.setIsDeliverSchedule(fossEntity.getIsDeliverSchedule());
	// 是否理货
	esbInfo.setIsArrangeGoods(fossEntity.getIsArrangeGoods());
	// 派送排单服务外场
	esbInfo.setDeliverOutfield(fossEntity.getDeliverOutfield());
	// 理货部门服务外场
	esbInfo.setArrangeOutfield(fossEntity.getArrangeOutfield());
	// 理货业务类型
	esbInfo.setArrangeBizType(fossEntity.getArrangeBizType());
	// 是否空运总调
	esbInfo.setAirDispatch(fossEntity.getAirDispatch());
	// 是否实体财务部
	esbInfo.setIsEntityFinance(fossEntity.getIsEntityFinance());
	// 所属实体财务部
	esbInfo.setEntityFinance(fossEntity.getEntityFinance());
	// 部门服务区坐标编号
	esbInfo.setDepCoordinate(fossEntity.getDepCoordinate());
	// 部门电话
	esbInfo.setDepTelephone(fossEntity.getDepTelephone());
	// 部门传真
	esbInfo.setDepFax(fossEntity.getDepFax());
	// 部门简称
	esbInfo.setOrgSimpleName(fossEntity.getOrgSimpleName());
	// 国家地区
	esbInfo.setCountryRegion(fossEntity.getCountryRegion());
	// 部门英文简称
	esbInfo.setOrgEnSimple(fossEntity.getOrgEnSimple());
	// 部门备注描述信息
	esbInfo.setDeptDesc(fossEntity.getDeptDesc());
	// 数据版本号
	esbInfo.setVersionNo(fossEntity.getVersionNo() == null ? 0 : (double) fossEntity.getVersionNo());
	// 上级组织编码
	esbInfo.setParentOrgCode(fossEntity.getParentOrgCode());
	// UUMS主键ID
	esbInfo.setUumsId(fossEntity.getUumsId());
	// UUMS上级主键ID
	esbInfo.setUumsParentId(fossEntity.getUumsParentId());
	// UUMS主键ID序列
	esbInfo.setUumsIds(fossEntity.getUumsIds());
	// 是否为叶子节点
	esbInfo.setIsLeaf(fossEntity.getIsLeaf());
	// 显示顺序
	esbInfo.setDisplayOrder(fossEntity.getDisplayOrder());
	// 部门层级
	esbInfo.setDeptLevel(fossEntity.getDeptLevel());
	// 地区编码默认拼音
	esbInfo.setAreaCode(fossEntity.getAreaCode());
	// 组织邮箱
	esbInfo.setDeptEmail(fossEntity.getDeptEmail());
	// 邮编号码
	esbInfo.setZipCode(fossEntity.getZipCode());
	// 组织性质
	esbInfo.setDeptAttribute(fossEntity.getDeptAttribute());
	// 已封存系统
	esbInfo.setCanceledSystems(fossEntity.getCanceledSystems());
	// EHR部门编码
	esbInfo.setEhrDeptCode(fossEntity.getEhrDeptCode());
	// 组织级别，取自数据字典，包括但不限于事业部，大区，小区，部门等，
	//esbInfo.setOrglevel(fossEntity.getOrgLevel());
	
	// 创建时间
	esbInfo.setCreateTime(fossEntity.getCreateDate());
	// 更新时间
	esbInfo.setModifyTime(fossEntity.getCreateDate());
	// 是否启用
	esbInfo.setActive(fossEntity.getActive());
	// 创建人
	esbInfo.setCreateUserCode(fossEntity.getCreateUser());
	// 更新人
	esbInfo.setModifyUserCode(fossEntity.getModifyUser());
	
	return esbInfo;
    }
    

}
