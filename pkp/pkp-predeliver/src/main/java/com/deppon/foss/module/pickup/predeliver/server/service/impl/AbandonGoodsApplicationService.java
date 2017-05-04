/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
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
 * PROJECT NAME	: pkp-predeliver
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/server/service/impl/AbandonGoodsApplicationService.java
 * 
 * FILE NAME        	: AbandonGoodsApplicationService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.esb.api.domain.AccessHeader;
import com.deppon.esb.inteface.domain.wfs.FileBean;
import com.deppon.esb.inteface.domain.wfs.ThrowfreightRequest;
import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.web.upload.AttachementEntity;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAttachementService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IAbandonGoodsApplicationDao;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IAbandonGoodsImportDao;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IAgActionHistoryDao;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IAbandonGoodsApplicationService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IExceptionProcessService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IWorkFlowService;
import com.deppon.foss.module.pickup.predeliver.api.shared.context.FossUserContextHelper;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.AbandGoodsApplicationConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.AbandonGoodsImportConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.ExceptionProcessConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.AbandonGoodsApplicationEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.AbandonGoodsImportEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.AgActionHistoryEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.ExceptionEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.AbandonGoodsResultDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.AbandonedGoodsDetailDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.AbandonedGoodsSearchDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ResultDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.exception.AbandonGoodsException;
import com.deppon.foss.module.pickup.predeliver.api.shared.exception.DeliverbillException;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * <p>
 * 弃货service实现<br />
 * </p>
 * 
 * @title AbandonGoodsApplicationService.java
 * @package com.deppon.foss.module.pickup.predeliver.server.service.impl
 * @author ibm-lizhiguo
 * @version 0.1 2013-3-19
 */
public class AbandonGoodsApplicationService implements IAbandonGoodsApplicationService {
	/**
	 * 日志对象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(AbandonGoodsApplicationService.class.getName());
	/**
	 * 0 金额
	 */
	private static final BigDecimal ZERO = BigDecimal.ZERO;

	/**
	 * 弃货service
	 */
	private IAbandonGoodsApplicationDao abandonGoodsApplicationDao;

	/**
	 * 异常处理serivce
	 */
	private IExceptionProcessService exceptionProcessService;

	/**
	 * 弃货中间导入表service
	 */
	private IAbandonGoodsImportDao abandonGoodsImportDao;

	/**
	 * 运单管理Service
	 */
	private IWaybillManagerService waybillManagerService;

	/**
	 * 部门组织复杂数据信息service
	 */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;

	/**
	 * 部门数据service
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;

	/**
	 * 系统常量service
	 */
	private IConfigurationParamsService configurationParamsService;
	/**
	 * 附件
	 */
	private IAttachementService attachementService;

	/**
	 * 为事务，实例化本身
	 */
	private IAbandonGoodsApplicationService abandonGoodsApplicationService;

	/**
	 * 弃货历史DAO
	 */
	private IAgActionHistoryDao agActionHistoryDao;

	/**
	 * 业务互斥锁服务
	 */
	private IBusinessLockService businessLockService;
	
	/**
	 * 工作流
	 */
	private IWorkFlowService workFlowService;

	/**
	 * set对象
	 */
	public void setConfigurationParamsService(IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}

	/**
	 * set对象
	 */
	public void setAbandonGoodsApplicationDao(IAbandonGoodsApplicationDao abandonGoodsApplicationDao) {
		this.abandonGoodsApplicationDao = abandonGoodsApplicationDao;
	}

	/**
	 * set对象
	 */
	public void setExceptionProcessService(IExceptionProcessService exceptionProcessService) {
		this.exceptionProcessService = exceptionProcessService;
	}

	/**
	 * @param attachementService : set the property attachementService.
	 */
	public void setAttachementService(IAttachementService attachementService) {
		this.attachementService = attachementService;
	}
	
	

	public void setWorkFlowService(IWorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}

	/**
	 * @param waybillNo; 运单号
	 * @param tSrvExceptionId; 异常_ID--页面
	 * @param abandonedgoodsType; 弃货类型--（页面提交==客户弃货类型）
	 * @param createUserName; 预弃货人--（记录的创建人姓名）
	 * @param createUserCode; 预弃货人编码--（记录的创建人员工编码）
	 * @param createOrgName; 预弃货部门
	 * @param createOrgCode; 预弃货部门编码
	 * @param preabandonedgoodsTime; 预弃货时间
	 * @param notes; 弃货事由
	 * @param status; 状态（新增--待处理状态）
	 * @param operator; 操作人
	 * @param operatorCode; 操作人编码
	 * @param operateOrgName; 操作部门
	 * @param operateOrgCode; 操作部门编码
	 * @param operateTime; 操作时间
	 * @param importStatus; 导入状态
	 * @param processId; 工作流id
	 * @param processReason; 工作流 审批具体事由细节 转弃货保存
	 */
	@Override
	public void insertAbandonGoodsApplication(AbandonGoodsApplicationEntity entity, List<AttachementEntity> attachementFlies) {
		//功能关闭
		if(true){
			throw new AbandonGoodsException("请从异常货系统转弃货！");
		}
		MutexElement mutexElement = new MutexElement(entity.getWaybillNo(), "弃货ID", MutexElementType.ABANDON_GOODS_ID);
		//互斥锁定
		boolean isLocked = businessLockService.lock(mutexElement, NumberConstants.ZERO);
		//若未上锁
		if (!isLocked) {
			//抛出派送单异常
			throw new AbandonGoodsException(DeliverbillException.WAYBILL_LOCKED);
		}
		AbandonedGoodsSearchDto dto = new AbandonedGoodsSearchDto();
		dto.setWaybillNo(entity.getWaybillNo());
		// 验证是否有审批、新增的弃货
		long total = this.getTotalCount(dto);
		//已经转弃货了 不用再转了 
		if (total > 0) {
			//解锁
			businessLockService.unlock(mutexElement);
			throw new AbandonGoodsException("运单已转弃货，无法再次提交，请至处理弃货页面查询。");
		}
		EmployeeEntity emp = FossUserContext.getCurrentUser().getEmployee();
		// 预弃货人--（记录的创建人姓名）
		entity.setCreateUserName(emp.getEmpName());
		// 预弃货人编码--（记录的创建人员工编码）
		entity.setCreateUserCode(emp.getEmpCode());
		// 预弃货部门
		OrgAdministrativeInfoEntity org = FossUserContext.getCurrentDept();

		if (org != null) {
			entity.setCreateOrgName(org.getName());
		} else {
			entity.setCreateOrgName("");
		}
		// 预弃货部门编码
		entity.setCreateOrgCode(FossUserContext.getCurrentDeptCode());
		// 预弃货时间
		entity.setPreabandonedgoodsTime(new Date());
		// 弃货事由
		// 状态（新增--待处理状态）
		entity.setStatus(AbandGoodsApplicationConstants.ABANDGOODS_STATUS_NEW);
		// 异常ID
		// 运单号
		// 弃货类型
		entity.setAbandonedgoodsType(AbandGoodsApplicationConstants.ABANDGOODS_TYPE_CUSTOMER);
		entity.setCustomerCooperateStatus(StringUtils.equals(FossConstants.YES, entity.getCustomerCooperateStatus()) ? FossConstants.YES : FossConstants.NO);

		// UUID
		entity.setId(UUIDUtils.getUUID());
		//id
		abandonGoodsApplicationDao.insertAbandonGoodsApplication(entity);
		// 设置操作内容
		entity.setOperateContent(ReflectionToStringBuilder.toString(entity));
		// 保存操作历史记录
		insertAgActionHistoryEntity(entity);
		//附件保存
		saveAttachment(attachementFlies, entity.getId());
		//保存
		ExceptionEntity exceptionEntity = new ExceptionEntity();
		//创建异常对象
		exceptionEntity.setId(entity.gettSrvExceptionId());
		//id
		exceptionEntity.setStatus(ExceptionProcessConstants.ALREADY_ABANDON_GOODS);
		//状态
		exceptionProcessService.updateExceptionProcessInfo(exceptionEntity);
		//更新
		//解锁
		businessLockService.unlock(mutexElement);
	}
	
	/**
	 * @param waybillNo; 运单号
	 * @param tSrvExceptionId; 异常_ID--页面
	 * @param abandonedgoodsType; 弃货类型--（页面提交==客户弃货类型）
	 * @param createUserName; 预弃货人--（记录的创建人姓名）
	 * @param createUserCode; 预弃货人编码--（记录的创建人员工编码）
	 * @param createOrgName; 预弃货部门
	 * @param createOrgCode; 预弃货部门编码
	 * @param preabandonedgoodsTime; 预弃货时间
	 * @param notes; 弃货事由
	 * @param status; 状态（新增--待处理状态）
	 * @param operator; 操作人
	 * @param operatorCode; 操作人编码
	 * @param operateOrgName; 操作部门
	 * @param operateOrgCode; 操作部门编码
	 * @param operateTime; 操作时间
	 * @param importStatus; 导入状态
	 * @param processId; 工作流id
	 * @param processReason; 工作流 审批具体事由细节 转弃货保存
	 */
	@Override
	public void editAbandonGoodsApplication(AbandonGoodsApplicationEntity entity){
		if(entity.getCustomerCooperateStatus()==null){
			entity.setCustomerCooperateStatus(FossConstants.NO);
		}
		abandonGoodsApplicationDao.editAbandonGoodsApplication(entity);
	}
	
	/**
	 * 
	 * <p>
	 * 保存附件<br />
	 * </p>
	 * 
	 * @author ibm-lizhiguo
	 * @version 0.1 2013-1-6
	 * @param fileList
	 * @param id void
	 */
	private void saveAttachment(List<AttachementEntity> fileList, String id) {
		//是否有附件
		if (CollectionUtils.isNotEmpty(fileList) && StringUtils.isNotEmpty(id)) {
			for (AttachementEntity attachementEntity : fileList) {
				if(null !=attachementEntity){
					//关联上传组件和业务
					attachementEntity.setRelatedKey(id);
					//更新上传组件数据
					attachementService.updateAttachementInfo(attachementEntity);
				}
			}
		} else {
			LOGGER.info("没有附件，不需要保存");
		}

	}

	/**
	 * 界面描述 外场经理或理货员可在该界面录入条件查询出待处理仓库异常货物运单， 并对选中的运单起草仓库异常货物处理工作流， 查看处理凭证，
	 * 查看工作流审批进度， 导入运单开单。 界面主要由两部分组成： [仓库异常货物处理]界面、[查看仓库异常货物]界面。 一、仓库异常货物处理 1. *
	 * 查询条件： 运单号、处理状态、 仓库异常货物类型、 发货部门、发货人、预处理人、 预处理时间、查询、重置。 a) * 运单号：
	 * 客户要求更改的运单号。 b) * 处理状态： 仓库异常货物处理的处理状态分为四种：待 处理、申请中、申请未通过、申请通过、已处理（内部带货）、
	 * 客户主动要求变更。 c) * 仓库异常货物类型： 派送异常货拉回转仓库异常货物的， 包含客户弃货、存储超时。 d) * 发货人姓名：
	 * 发货客户的名字。 e) * 预处理人： 新增预处理仓库异常货物的操作人姓名 f) * 预处理时间： 新增预处理仓库异常货物时间 g) * 查询：
	 * 根据录入条件查询仓库异常货物运单 h) * 重置： 清空查询条件至默认状态 2. 处理仓库异常货物： 工作流、仓库异常货物信息、 运单号、
	 * 处理状态、 预处理人、 收货人姓名、 收货人电话、 预处理时间、 总重量、 总体积、 总件数、 导入内部带货 二、查看仓库异常货物
	 * 该界面包含单号、货物名称、重量、体积、发货人、发货人电话、发货部门、所属区域、收货人、收货人电话、
	 * 到达部门、仓储部门、代收金额、保险金额、预付金额、到付金额、仓库异常货物类型、处理状态、预处理时间、预处理人、仓储时长。
	 * 需要提供的相关用例链接或提示信息： 链接： 1）工作流已处理后，可导入运单信息开内部带货，点击“导入”连接到“运单生成”界面。
	 * 2）若未起草弃货工作流，则外场经理或理货员可进入起草弃货处理工作流界面。 提示信息： 1）内部带货：“对不起，工作流未审批完，不能导入开单！”。
	 * 2）弃货处理时间：“查询时间不能超过30天！” 二、界面标题：查询弃货处理 1.
	 * 查询弃货信息：运单号、货物名称、重量、体积、发货人、发货人电话、发货部门、所属区域、收货人
	 * 、收货人电话、到达部门、仓储部门、代收金额、保险金额、预付金额、到付金额、弃货类型、 处理状态、弃货事由、预弃货人、预弃货时间、仓储时长。
	 * 查询弃货 货人名字在查询时可模糊匹配
	 * 
	 * @param id; iD
	 * @param waybillNo; 运单号
	 * @param status; 状态（新增--待处理状态）
	 * @param abandonedgoodsType; 弃货类型--（页面提交==客户弃货类型）
	 * @param receiveOrgCode; 始发部门CODE--(运单--收货部门)
	 * @param deliveryCustomerName; 发货人(运单--发货客户名称)
	 * @param createUserName; 预弃货人--（记录的创建人姓名）
	 * @param preabandonedgoodsTimeBegin; 预弃货时间--开始
	 * @param preabandonedgoodsTimeEnd; 预弃货时间--结束
	 * @param storageDay; 天数
	 * @param currentOrgCode; 货物库存状态在本部门库存
	 */
	public List<AbandonGoodsResultDto> searchAbandonGoodsList(AbandonedGoodsSearchDto dto) {
		//外场理货员根据查询条件只能查询到货物库存状态在本部门库存中的货物信息
		dto.setCurrentOrgCode(FossUserContextHelper.getOrgCode());
		if (StringUtils.isNotBlank(dto.getWaybillNo())) {
			dto.setPreabandonedgoodsTimeBegin(null);
			dto.setPreabandonedgoodsTimeEnd(null);
		}
		// 是否通过在线提醒跳转到处理弃货界面查询的  是 为Y   
		if(StringUtils.isNotBlank(dto.getIsByMsg()) && FossConstants.YES.equals(dto.getIsByMsg())){
			 List<String> abandgoodsStatus =new ArrayList<String>();
			 abandgoodsStatus.add(AbandGoodsApplicationConstants.ABANDGOODS_STATUS_NEW);
			 abandgoodsStatus.add(AbandGoodsApplicationConstants.ABANDGOODS_STATUS_APPROVAL);
			 abandgoodsStatus.add(AbandGoodsApplicationConstants.ABANDGOODS_STATUS_REFUSE);
			 dto.setAbandgoodsStatus(abandgoodsStatus);
			 dto.setPreabandonedgoodsTimeBegin(DateUtils.addDayToDate(new Date(),- NumberConstants.NUMBER_30));
			 dto.setPreabandonedgoodsTimeEnd(new Date());
		}
		//货人名字在查询时可模糊匹配。
		// 查询弃货，在sql中新增union无标签转弃货记录
		return abandonGoodsApplicationDao.searchAbandonGoodsListWithAmbuiousName(dto);
		
	}

	/**
	 * 得到弃货总数 * @param id; iD
	 * 
	 * @param waybillNo; 运单号
	 * @param status; 状态（新增--待处理状态）
	 * @param abandonedgoodsType; 弃货类型--（页面提交==客户弃货类型）
	 * @param receiveOrgCode; 始发部门CODE--(运单--收货部门)
	 * @param deliveryCustomerName; 发货人(运单--发货客户名称)
	 * @param createUserName; 预弃货人--（记录的创建人姓名）
	 * @param preabandonedgoodsTimeBegin; 预弃货时间--开始
	 * @param preabandonedgoodsTimeEnd; 预弃货时间--结束
	 * @param storageDay; 天数
	 * @param currentOrgCode;
	 */
	public Long getTotalCount(AbandonedGoodsSearchDto dto) {
		// 拒绝的弃货除外
		String[] statusArr = {AbandGoodsApplicationConstants.ABANDGOODS_STATUS_REFUSE };
		dto.setStatusArr(statusArr);
		return abandonGoodsApplicationDao.getTotalCount(dto);//得到弃货总数
	}

	/**
	 * 得到弃货总数 * @param id; iD
	 * 
	 * @param waybillNo; 运单号
	 * @param status; 状态（新增--待处理状态）
	 * @param abandonedgoodsType; 弃货类型--（页面提交==客户弃货类型）
	 * @param receiveOrgCode; 始发部门CODE--(运单--收货部门)
	 * @param deliveryCustomerName; 发货人(运单--发货客户名称)
	 * @param createUserName; 预弃货人--（记录的创建人姓名）
	 * @param preabandonedgoodsTimeBegin; 预弃货时间--开始
	 * @param preabandonedgoodsTimeEnd; 预弃货时间--结束
	 * @param storageDay; 天数
	 * @param currentOrgCode;
	 */
	@Override
	public AbandonGoodsResultDto queryAbandonedGoodsForWaybillNo(String waybillNo) {
		// 审批中
		String[] statusArr = {  AbandGoodsApplicationConstants.ABANDGOODS_STATUS_APPROVAL};
		AbandonedGoodsSearchDto dto = new AbandonedGoodsSearchDto();
		dto.setStatusArr(statusArr);
		dto.setWaybillNo(waybillNo);
		List<AbandonGoodsResultDto> dtos = abandonGoodsApplicationDao.queryAbandonedsForStatus(dto);//得到弃货总数
		return CollectionUtils.isEmpty(dtos) ? null : dtos.get(0);
	}
	
	/**
	 * 根据运单号和流水号查询无标签弃货记录
	 */
	@Override
	public AbandonGoodsResultDto queryNoTagAbandonedGoodsByWaybillNoAndSerialNo(
			String waybillNo, String serialNumber) {
		if(StringUtils.isEmpty(waybillNo) || StringUtils.isEmpty(serialNumber)){
			LOGGER.error("###waybillNo:" + waybillNo + ", serialNumber:" + serialNumber + "###不能为空");
			return null;
		}
		AbandonedGoodsSearchDto dto = new AbandonedGoodsSearchDto();
		dto.setWaybillNo(waybillNo);
		dto.setSerialNumber(serialNumber);
		List<AbandonGoodsResultDto> list = abandonGoodsApplicationDao.searchNoTagAbandonGoodsList(dto);
		if(list != null && list.size() == 1){
			return list.get(0);
		}else{
			LOGGER.error("###waybillNo:" + waybillNo + ", serialNumber:" + serialNumber +
					"查询到的记录数不等于1");
			return null;			
		}
	}

	/**
	 * 根据id查询得到弃货 * @param waybillNo; 运单号
	 * 
	 * @param goodsName; 货物名称--运单
	 * @param goodsWeightTotal; 重量--运单
	 * @param goodsVolumeTotal; 体积--运单
	 * @param deliveryCustomerContact; 发货人--(运单--发货客户联系人)
	 * @param deliveryCustomerPhone; 发货人电话
	 * @param deliveryCustomerMobilephone; 发货人手机
	 * @param receiveOrgCode; 发货部门
	 * @param receiveOrgName; 发货部门名称
	 * @param respectiveRegional; 所属区域(运单中的部门编码--调用李俊给的接口，获得所属区域)
	 * @param respectiveRegionalName; 所属区域名称
	 * @param deliveryCustomerCode; 发货人code
	 * @param deliveryCustomerName; 发货人name
	 * @param receiveCustomerContact; 收货人
	 * @param receiveCustomerPhone; 收货人电话
	 * @param lastLoadOrgCode; 到达部门(运单--最终配载部门--在营业部表中，把CODE改成NAME)
	 * @param lastLoadOrgName; 到达部门名称
	 * @param lastStorageCode; 最终配载部门CODE
	 * @param lastStorageName; 最终配载部门NAME
	 * @param codAmount; 代收金额
	 * @param insuranceAmount; 保险金额
	 * @param prePayAmount; 预付金额
	 * @param toPayAmount; 到付金额
	 * @param abandonedgoodsType; 弃货类型--(申请转弃货--弃货类型)
	 * @param status; 处理状态----(申请转弃货--状态)
	 * @param importStatus; 导入状态
	 * @param preabandonedgoodsTime; 预弃货处理时间--(申请转弃货--预弃货时间)
	 * @param createUserName; 预弃货人--(申请转弃货--预弃货人)
	 * @param storageDay; 仓储时长--(T_SRV_ACTUAL_FREIGHT--库存天数)
	 * @param notes; 弃货事由--(申请转弃货--弃货事由)
	 * @param goodsQtyTotal; 货物总件数
	 */
	public AbandonedGoodsDetailDto getAbandonGoodsDetailById(String id) {
		AbandonedGoodsDetailDto dto = null;
		// 获取弃货类型，如果是无标签转弃货，则只从弃货表查数据，否则沿用原来的查询方法
		String abandonGoodsType = abandonGoodsApplicationDao.getAbandonGoodsTypeById(id);
		if(abandonGoodsType != null &&
				abandonGoodsType.equals(AbandGoodsApplicationConstants.ABANDGOODS_TYPE_NOTAG)){
			dto = abandonGoodsApplicationDao.getNoTagAbandonGoodsDetailById(id);
		}else{
			dto = abandonGoodsApplicationDao.getAbandonGoodsDetailById(id);			
		}
		// 得到部门所属的大区
		// 无标签弃货有所属区域信息，其他情况按原有流程
		if(abandonGoodsType != null &&
				abandonGoodsType.equals(AbandGoodsApplicationConstants.ABANDGOODS_TYPE_NOTAG)){
			//设置区域名称
			dto.setRespectiveRegionalName(setUpOrgName(dto.getRespectiveRegional()));
		}else{
			searchBigRegion(dto);
		}
		// 到达部门名称
		String lastOrgName = setUpOrgName(dto.getLastLoadOrgCode());
		dto.setLastLoadOrgName(lastOrgName);
		// 仓储部门，无标签弃货有仓储部门信息，其他情况和到达部门一致
		if(abandonGoodsType != null &&
				abandonGoodsType.equals(AbandGoodsApplicationConstants.ABANDGOODS_TYPE_NOTAG)){
			dto.setLastStorageName(setUpOrgName(dto.getLastStorageCode()));
		}else{
			dto.setLastStorageName(lastOrgName);			
		}
		// 收货部门
		dto.setReceiveOrgName(setUpOrgName(dto.getReceiveOrgCode()));
		// 设置附件信息
		//获得文件附件列表
		dto.setAbandonedGoodsFiles(attachementService.getAttachementInfos(id, null));
		//返回数据
		return dto;
	}

	/**
	 * 得到部门名称
	 */
	/**
	 * 
	 * 得到部门名称 </p>
	 * 
	 * @author ibm-lizhiguo
	 * @version 0.1 2013-3-19
	 * @param orgCode
	 * @return String
	 */
	private String setUpOrgName(String orgCode) {
		//定义返回名字变量
		String orgName = null;
		//org code is not empty
		if (orgCode != null && !"".equals(orgCode)) {
			//调用组织服务，获得组织数据
			OrgAdministrativeInfoEntity org = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(orgCode);
			//得到部门对象
			if (org != null) {
				//得到部门名称
				orgName = org.getName();
				// 得到部门对象名称
			}
		}
		//返回部门名称
		return orgName;
	}

	/**
	 * 
	 * <p>
	 * 查询部门名称<br />
	 * </p>
	 * 
	 * @author ibm-lizhiguo
	 * @version 0.1 2013-3-19
	 * @param dto * @param waybillNo; 运单号
	 * @param goodsName; 货物名称--运单
	 * @param goodsWeightTotal; 重量--运单
	 * @param goodsVolumeTotal; 体积--运单
	 * @param deliveryCustomerContact; 发货人--(运单--发货客户联系人)
	 * @param deliveryCustomerPhone; 发货人电话
	 * @param deliveryCustomerMobilephone; 发货人手机
	 * @param receiveOrgCode; 发货部门
	 * @param receiveOrgName; 发货部门名称
	 * @param respectiveRegional; 所属区域(运单中的部门编码--调用李俊给的接口，获得所属区域)
	 * @param respectiveRegionalName; 所属区域名称
	 * @param deliveryCustomerCode; 发货人code
	 * @param deliveryCustomerName; 发货人name
	 * @param receiveCustomerContact; 收货人
	 * @param receiveCustomerPhone; 收货人电话
	 * @param lastLoadOrgCode; 到达部门(运单--最终配载部门--在营业部表中，把CODE改成NAME)
	 * @param lastLoadOrgName; 到达部门名称
	 * @param lastStorageCode; 最终配载部门CODE
	 * @param lastStorageName; 最终配载部门NAME
	 * @param codAmount; 代收金额
	 * @param insuranceAmount; 保险金额
	 * @param prePayAmount; 预付金额
	 * @param toPayAmount; 到付金额
	 * @param abandonedgoodsType; 弃货类型--(申请转弃货--弃货类型)
	 * @param status; 处理状态----(申请转弃货--状态)
	 * @param importStatus; 导入状态
	 * @param preabandonedgoodsTime; 预弃货处理时间--(申请转弃货--预弃货时间)
	 * @param createUserName; 预弃货人--(申请转弃货--预弃货人)
	 * @param storageDay; 仓储时长--(T_SRV_ACTUAL_FREIGHT--库存天数)
	 * @param notes; 弃货事由--(申请转弃货--弃货事由)
	 * @param goodsQtyTotal; 货物总件数 void
	 */
	private void searchBigRegion(AbandonedGoodsDetailDto dto) {
		//dto is not empty
		if (dto != null) {
			//实例集合
			List<String> bizTypes = new ArrayList<String>();
			//添加类型
			bizTypes.add(BizTypeConstants.ORG_BIG_REGION);
			//大区
			OrgAdministrativeInfoEntity org = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoByCode(dto.getReceiveOrgCode(), bizTypes);
			//获得区域
			if (org != null) {
				//设置区域CODE
				dto.setRespectiveRegional(org.getCode());
				//设置区域名称
				dto.setRespectiveRegionalName(org.getName());
			}
		}
	}

	/**
	 * 
	 * <p>
	 * 根据WaybillNo查询得到弃货<br />
	 * </p>
	 * 
	 * @author ibm-lizhiguo
	 * @version 0.1 2013-3-19
	 * @param waybillNo
	 * @return * @param waybillNo; 运单号
	 * @param goodsName; 货物名称--运单
	 * @param goodsWeightTotal; 重量--运单
	 * @param goodsVolumeTotal; 体积--运单
	 * @param deliveryCustomerContact; 发货人--(运单--发货客户联系人)
	 * @param deliveryCustomerPhone; 发货人电话
	 * @param deliveryCustomerMobilephone; 发货人手机
	 * @param receiveOrgCode; 发货部门
	 * @param receiveOrgName; 发货部门名称
	 * @param respectiveRegional; 所属区域(运单中的部门编码--调用李俊给的接口，获得所属区域)
	 * @param respectiveRegionalName; 所属区域名称
	 * @param deliveryCustomerCode; 发货人code
	 * @param deliveryCustomerName; 发货人name
	 * @param receiveCustomerContact; 收货人
	 * @param receiveCustomerPhone; 收货人电话
	 * @param lastLoadOrgCode; 到达部门(运单--最终配载部门--在营业部表中，把CODE改成NAME)
	 * @param lastLoadOrgName; 到达部门名称
	 * @param lastStorageCode; 最终配载部门CODE
	 * @param lastStorageName; 最终配载部门NAME
	 * @param codAmount; 代收金额
	 * @param insuranceAmount; 保险金额
	 * @param prePayAmount; 预付金额
	 * @param toPayAmount; 到付金额
	 * @param abandonedgoodsType; 弃货类型--(申请转弃货--弃货类型)
	 * @param status; 处理状态----(申请转弃货--状态)
	 * @param importStatus; 导入状态
	 * @param preabandonedgoodsTime; 预弃货处理时间--(申请转弃货--预弃货时间)
	 * @param createUserName; 预弃货人--(申请转弃货--预弃货人)
	 * @param storageDay; 仓储时长--(T_SRV_ACTUAL_FREIGHT--库存天数)
	 * @param notes; 弃货事由--(申请转弃货--弃货事由)
	 * @param goodsQtyTotal; 货物总件数 AbandonedGoodsDetailDto
	 */
	public AbandonedGoodsDetailDto getAbandonGoodsDetailByWaybillNo(String waybillNo) {
		//获得运单查询数据
		AbandonedGoodsDetailDto dto = getAbandoneGoodsDto(waybillNo);
		//查询大区
		searchBigRegion(dto);
		//得到部门所属的大区
		String lastOrgName = setUpOrgName(dto.getLastLoadOrgCode());
		dto.setLastLoadOrgName(lastOrgName);
		//到达部门和仓储部门用的同一个值
		dto.setLastStorageName(lastOrgName);
		//到达部门和仓储部门用的同一个值
		dto.setReceiveOrgName(setUpOrgName(dto.getReceiveOrgCode()));
		//返回数据
		return dto;
	}

	@Override
	public AbandonedGoodsDetailDto getAbandoneGoodsDto(String waybillNo) {
		return abandonGoodsApplicationDao.getAbandonGoodsDetailByWaybillNo(waybillNo);
	}

	/**
	 * 获得仓储时间
	 */
	public Date getInStockTimeByWaybillNoAndOrgCode(String waybillNo, String orgCode) {
		//定义查询map
		Map<String, String> map = new HashMap<String, String>();
		//放入运单号
		//waybill no
		map.put("waybillNo", waybillNo);
		// org code
		map.put("orgCode", orgCode);
		//获得仓储时间
		return abandonGoodsApplicationDao.getInStockTimeByWaybillNoAndOrgCode(map);
	}

	/**
	 * 保存导入内部带货 1、 导入开单时 ：开单提货方式为“内部带货”， 其它界面填充信息参见“DP-FOSS-接送货系统用例-运单生成 –运单开单”
	 * 2、 导入开单成功后： 1） 系统自动更新所有选中记录对应运单的“储运事项”， 内容为“该运单已弃货处理成功， 内部带货至大区办公室，
	 * 运单号为XXXXXXXX”， XXXXXXXX为内部带货开单运单号。 2） 系统自动更新所有选中记录的处理状态为“已处理（内部带货）”，
	 * 且记录复选框重置为未选中且为不可编辑状态。 3） 系统更新内部带货运单的“储运事项” ，内容为“申请弃货处理的运单号为、
	 * 其中“”为所有批量导入开单的运单号。
	 * 
	 * @date 2012-11-9 下午4:48:47
	 */
	@Transactional
	public void createAbandonGoodsImport(String ids) {
		//判断传入的ID是否为空
		if (StringUtils.isEmpty(ids)) {
			return;
		}
		//对传入得参数进行加工，得到数组
		String[] waybillArray = StringUtils.split(ids, AbandonGoodsImportConstants.COMMA);
		//遍历数组
		for (int i = 0; i < waybillArray.length; i++) {
			//获得id
			String id = waybillArray[i];
			//id为空 跳过
			if (StringUtils.isEmpty(id)) {
				continue;
			}
			//得到详细信息
			AbandonedGoodsDetailDto resultdto = this.getAbandonGoodsDetailById(id);
			//没有这条运单记录 跳过
			if (resultdto == null) {
				continue;
			}
			//弃货为已导入 不需要再次导入
			if (AbandGoodsApplicationConstants.IMPORTED_STATUS_DONE.equals(resultdto.getImportStatus())) {
				continue;
			}
			//保存AbandonGoodsImportEntity 中间表信息
			saveAbandonGoosImportEntity(resultdto);
			//更新 AbandonGoodsApplicationEntity 弃货为已导入
			updateAbandonGoodsApplicationEntity(resultdto);
		}
	}

	/**
	 * * @param waybillNo; 运单号
	 * 
	 * @param goodsName; 货物名称--运单
	 * @param goodsWeightTotal; 重量--运单
	 * @param goodsVolumeTotal; 体积--运单
	 * @param deliveryCustomerContact; 发货人--(运单--发货客户联系人)
	 * @param deliveryCustomerPhone; 发货人电话
	 * @param deliveryCustomerMobilephone; 发货人手机
	 * @param receiveOrgCode; 发货部门
	 * @param receiveOrgName; 发货部门名称
	 * @param respectiveRegional; 所属区域(运单中的部门编码--调用李俊给的接口，获得所属区域)
	 * @param respectiveRegionalName; 所属区域名称
	 * @param deliveryCustomerCode; 发货人code
	 * @param deliveryCustomerName; 发货人name
	 * @param receiveCustomerContact; 收货人
	 * @param receiveCustomerPhone; 收货人电话
	 * @param lastLoadOrgCode; 到达部门(运单--最终配载部门--在营业部表中，把CODE改成NAME)
	 * @param lastLoadOrgName; 到达部门名称
	 * @param lastStorageCode; 最终配载部门CODE
	 * @param lastStorageName; 最终配载部门NAME
	 * @param codAmount; 代收金额
	 * @param insuranceAmount; 保险金额
	 * @param prePayAmount; 预付金额
	 * @param toPayAmount; 到付金额
	 * @param abandonedgoodsType; 弃货类型--(申请转弃货--弃货类型)
	 * @param status; 处理状态----(申请转弃货--状态)
	 * @param importStatus; 导入状态
	 * @param preabandonedgoodsTime; 预弃货处理时间--(申请转弃货--预弃货时间)
	 * @param createUserName; 预弃货人--(申请转弃货--预弃货人)
	 * @param storageDay; 仓储时长--(T_SRV_ACTUAL_FREIGHT--库存天数)
	 * @param notes; 弃货事由--(申请转弃货--弃货事由)
	 * @param goodsQtyTotal; 货物总件数 更新 AbandonGoodsApplicationEntity 弃货为已导入
	 */
	private AbandonGoodsApplicationEntity updateAbandonGoodsApplicationEntity(AbandonedGoodsDetailDto resultdto) {
		//定义更新实体对象
		AbandonGoodsApplicationEntity entitySave = new AbandonGoodsApplicationEntity();
		entitySave.setId(resultdto.getId());
		entitySave.setWaybillNo(resultdto.getWaybillNo());//运单号
		//设置状态
		entitySave.setImportStatus(AbandGoodsApplicationConstants.IMPORTED_STATUS_DONE);
		//弃货为已导入
		entitySave.setStatus(AbandGoodsApplicationConstants.ABANDGOODS_STATUS_DEALED);
		//已处理（内部带货）
		entitySave.setNotes("该运单已弃货处理成功，内部带货至大区办公室，运单号为" + resultdto.getWaybillNo() + "，" + resultdto.getWaybillNo() + "为内部带货开单运单号。");
		abandonGoodsApplicationDao.updateByKey(entitySave);
		// 设置操作内容
		entitySave.setOperateContent(ReflectionToStringBuilder.toString(entitySave));
		// 保存操作历史记录
		insertAgActionHistoryEntity(entitySave);
		//更新数据库
		return entitySave;
	}

	/**
	 * * @param waybillNo; 运单号
	 * 
	 * @param goodsName; 货物名称--运单
	 * @param goodsWeightTotal; 重量--运单
	 * @param goodsVolumeTotal; 体积--运单
	 * @param deliveryCustomerContact; 发货人--(运单--发货客户联系人)
	 * @param deliveryCustomerPhone; 发货人电话
	 * @param deliveryCustomerMobilephone; 发货人手机
	 * @param receiveOrgCode; 发货部门
	 * @param receiveOrgName; 发货部门名称
	 * @param respectiveRegional; 所属区域(运单中的部门编码--调用李俊给的接口，获得所属区域)
	 * @param respectiveRegionalName; 所属区域名称
	 * @param deliveryCustomerCode; 发货人code
	 * @param deliveryCustomerName; 发货人name
	 * @param receiveCustomerContact; 收货人
	 * @param receiveCustomerPhone; 收货人电话
	 * @param lastLoadOrgCode; 到达部门(运单--最终配载部门--在营业部表中，把CODE改成NAME)
	 * @param lastLoadOrgName; 到达部门名称
	 * @param lastStorageCode; 最终配载部门CODE
	 * @param lastStorageName; 最终配载部门NAME
	 * @param codAmount; 代收金额
	 * @param insuranceAmount; 保险金额
	 * @param prePayAmount; 预付金额
	 * @param toPayAmount; 到付金额
	 * @param abandonedgoodsType; 弃货类型--(申请转弃货--弃货类型)
	 * @param status; 处理状态----(申请转弃货--状态)
	 * @param importStatus; 导入状态
	 * @param preabandonedgoodsTime; 预弃货处理时间--(申请转弃货--预弃货时间)
	 * @param createUserName; 预弃货人--(申请转弃货--预弃货人)
	 * @param storageDay; 仓储时长--(T_SRV_ACTUAL_FREIGHT--库存天数)
	 * @param notes; 弃货事由--(申请转弃货--弃货事由)
	 * @param goodsQtyTotal; 货物总件数 保存AbandonGoodsImportEntity 中间表信息
	 */
	private AbandonGoodsImportEntity saveAbandonGoosImportEntity(AbandonedGoodsDetailDto resultdto) {
		//实例化弃货导入实体
		AbandonGoodsImportEntity abandonGoodsImportEntity = new AbandonGoodsImportEntity();
		//设置ID
		abandonGoodsImportEntity.setId(UUIDUtils.getUUID());
		//id
		abandonGoodsImportEntity.setWaybillNo(resultdto.getWaybillNo());
		//运单号 
		abandonGoodsImportEntity.setStatus(AbandonGoodsImportConstants.NEW);
		//导入状态
		abandonGoodsImportEntity.setGoodsName(resultdto.getGoodsName());
		//货物名称
		abandonGoodsImportEntity.setAbandonedgoodsStatus(resultdto.getStatus());
		//弃货状态
		abandonGoodsImportEntity.setAbandonedgoodsType(resultdto.getAbandonedgoodsType());
		//弃货类型
		abandonGoodsImportEntity.setCodAmount(resultdto.getCodAmount());
		//总金额
		abandonGoodsImportEntity.setCreateUserName(resultdto.getCreateUserName());
		abandonGoodsImportEntity.setDeliveryCustomerContact(resultdto.getDeliveryCustomerContact());
		//收货人
		abandonGoodsImportEntity.setDeliveryCustomerMobilephone(resultdto.getDeliveryCustomerMobilephone());
		//收货人手机
		abandonGoodsImportEntity.setDeliveryCustomerPhone(resultdto.getDeliveryCustomerPhone());
		//收货人phone
		abandonGoodsImportEntity.setGoodsQtyTotal(resultdto.getGoodsQtyTotal());
		//件数
		abandonGoodsImportEntity.setGoodsVolumeTotal(resultdto.getGoodsVolumeTotal());
		//体积
		abandonGoodsImportEntity.setGoodsWeightTotal(resultdto.getGoodsWeightTotal());
		//总重量
		abandonGoodsImportEntity.setInsuranceAmount(resultdto.getInsuranceAmount());
		//设置最终配载部门
		abandonGoodsImportEntity.setLastLoadOrgCode(resultdto.getLastLoadOrgCode());
		//发送部门

		//here setStorageOrgCode = setLastLoadOrgCode discuss with zhiguo
		//根据最后的讨论结果 最后一个发送部门就是仓储部门 所以set值一样
		abandonGoodsImportEntity.setStorageOrgCode(resultdto.getLastLoadOrgCode());
		//仓储部门
		abandonGoodsImportEntity.setNotes(resultdto.getNotes());
		abandonGoodsImportEntity.setPreabandonedgoodsTime(resultdto.getPreabandonedgoodsTime());
		//预弃货时间
		abandonGoodsImportEntity.setPrePayAmount(resultdto.getPrePayAmount());
		//预付金额
		abandonGoodsImportEntity.setReceiveCustomerContact(resultdto.getReceiveCustomerContact());
		abandonGoodsImportEntity.setReceiveCustomerPhone(resultdto.getReceiveCustomerPhone());
		abandonGoodsImportEntity.setReceiveOrgCode(resultdto.getReceiveOrgCode());
		abandonGoodsImportEntity.setRespectiveRegional(resultdto.getRespectiveRegional());
		//所属大区

		abandonGoodsImportEntity.setStorageDay(resultdto.getStorageDay());
		//天数
		abandonGoodsImportEntity.setToPayAmount(resultdto.getToPayAmount());
		//到付金额

		//导入时间
		abandonGoodsImportEntity.setCreateTime(new Date(System.currentTimeMillis()));
		//创建人CODE
		abandonGoodsImportEntity.setCreateUserCode(FossUserContextHelper.getUserCode());
		//创建部门CODE
		abandonGoodsImportEntity.setCreateOrgCode(FossUserContextHelper.getOrgCode());
		//是否激活
		abandonGoodsImportEntity.setActive(FossConstants.ACTIVE);
		//保存数据
		abandonGoodsImportDao.insertSelective(abandonGoodsImportEntity);
		//插入中间表
		return abandonGoodsImportEntity;
	}

	/**
	 * 1、 系统默认查询起止时间为当天的00：00：00 到23：59：59，查询时间段不能超过30天。
	 * 若超过30天则提示“对不起，查询时间段不能超过30天！” 2、 处理状态： 1） 待处理： 未对预处理运单进行起草申请的处理，即初始状态 2）
	 * 申请中： 在OA中已成功提交仓库异常货物处理工作流，等待审批 3） 申请未通过：
	 * 因无凭证等原因起草的工作流被退回，此时可再点击[起草]重新起草工作流 4） 申请通过： 为OA工作流审批通过时的状态 5） 已处理（内部带货）：
	 * 点击“导入”后开内部带货单提交成功时反写后的状态，此时不可再起草（起草链接点击后无反应） 6） 客户主动要求变更：
	 * 呼叫中心审批工作流时联系发货人是否弃货，发货人主动要求变更，呼叫中心将工作流变更为拒绝的状态 3、
	 * 外场理货员根据查询条件只能查询到货物库存状态在本部门库存中的货物信息 不根据电话来查询，
	 * 但查询出的结果会将电话与手机拼接成字符串，中间用"/"隔开， 若只有一个联系方式则不需要拼接 1 系统每天晚上12点自动转换，
	 * 将派送异常运单对应的运单号、始发部门、发货人、 发货人手机、体积、入库时间、 仓储时长转换成预弃货信息， 同时操作人，操作人为系统管理员账户名。
	 * 
	 * 2 入库时间离当前时间如果超过“3”个月，则系统后台自动转成预弃货信息。 此处具体弃货时长可以进行人工配置。
	 */
	@Override
	public void preprocess() {
//		update 2016-03-16 需求RFOSS2016021807关闭FOSS转弃货
//		AbandonedGoodsSearchDto dto = new AbandonedGoodsSearchDto();
//		//默认天数90
//		//获得天数
//		String defaultStorageDay = getConfigurationDays();
//		//setup 默认天数
//		dto.setStorageDay(defaultStorageDay);
//		//获得明细对象
//		List<AbandonedGoodsDetailDto> list = abandonGoodsApplicationDao.searchAbandonGoodsByTimeList(dto);
//		//如果在指定天数内没有任何数据 就放弃不生成弃货
//		if (list == null || list.isEmpty()) {
//			return;
//		}
//		//对所有的数据生成弃货
//		for (Iterator<AbandonedGoodsDetailDto> iterator = list.iterator(); iterator.hasNext();) {
//			AbandonedGoodsDetailDto rtDto = iterator.next();
//			//对于在预弃货里面已经存在的单号 则不再做任何插入动作
//			AbandonedGoodsSearchDto innerDto = new AbandonedGoodsSearchDto();
//			innerDto.setWaybillNo(rtDto.getWaybillNo());//运单号 
//			long total = abandonGoodsApplicationService.getTotalCount(innerDto);//Total Count
//			//已经转弃货了 不用再转了 
//			if (total > 0) {
//				continue;
//			}
//			//自动导入 保存到弃货表
//			abandonGoodsApplicationService.saveAbandonGoodsApplicationEntity(rtDto);
//		}
	}

	/**
	 * 获得天数 组织配置参数
	 */
	private String getConfigurationDays() {
		String defaultStorageDay = AbandGoodsApplicationConstants.DEFAULT_DAYS;
		//获得配置对象
		ConfigurationParamsEntity entity = configurationParamsService.queryConfigurationParamsByOrgCode(DictionaryConstants.SYSTEM_CONFIG_PARM__PKP,
		//组织配置参数-接送货模块
				ConfigurationParamsConstants.PKP_PARM__ABANDOONGOODS_APP_DAYS,
				//异常转弃货JOB扫描天数
				FossConstants.ROOT_ORG_CODE);
		//集团
		if (entity != null) {
			defaultStorageDay = entity.getConfValue();
			//参数
			try {
				Integer.parseInt(defaultStorageDay);
				//天数如果弄错了不能解析为INTEGER
			} catch (Exception e) {
				LOGGER.error("Number format Exception", e);
				//default 90
			}
		}
		return defaultStorageDay;
	}

	/**
	 * 1、 若处理状态为[待处理]、 [申请未通过]时， [起草]链接可用， [导入内部带货]的按钮不可用。 2、 若处理状态为[申请中]、
	 * [申请通过]、[已处理（内部带货）]、 [客户主动要求变更]状态时， [起草]链接为不可用状态。 处理状态为[申请通过]时导入内部带货按钮才可用，
	 * 其它状态为不可用。 3、 仓库异常货物列的[查看]按钮在任何时候都可点击。 4、 发货人名字在查询时可模糊匹配。 5、
	 * 系统将起草弃货工作流的信息通过OA接口提交至OA系统中 ，成功生成弃货工作流后返回成功状态， FOSS系统自动更新状态为[申请中]；
	 * 若OA返回状态显示弃货工作生成失败时， FOSS系统自动中提示“处理仓库异常货物工作流失败”， 状态仍为[待申请] 6、
	 * 如果工作流处理结果是同意， 仓库异常货物状态改变为： 申请通过， FOSS系统调用签收出库接口(参见SUC-789
	 * 签收出库)对此票货自动签收出库， 并标示此货为仓库异常货物， 如果此货有到付款、 应收款等财务数据， 系统自动清零，
	 * 引用结算用例（参见SUC-159-异常出库） 1、 在弹出的[查看仓库异常货物信息]界面， 可点击[查看]链接在浏览器中查看客户上传的弃货凭证。
	 * 1、 当选中表头的全选框时， 系统自动选中所有可选择的记录（即[申请已通过]状态的记录）。 2、 系统自动计算所有已选中记录的总重量、 总体积、
	 * 总件数， 并显示在表格下方。 3、 点击[导入内部带货]时系统将选中运单信息保存到处理仓库异常货物临时表中。 1、
	 * 在运单开单界面查询出处理仓库异常货物临时表中数据， 用户选要开内部带货的记录导入开单。 2、 系统自动计算所有已选中记录的总重量、 总体积、
	 * 总件数， 并显示在表格下方， 可参见SR5
	 * <p>
	 * 保存到弃货表<br />
	 * </p>
	 * 
	 * @author ibm-lizhiguo
	 * @version 0.1 2013-3-19
	 * @param rtDto * @param waybillNo; 运单号
	 * @param goodsName; 货物名称--运单
	 * @param goodsWeightTotal; 重量--运单
	 * @param goodsVolumeTotal; 体积--运单
	 * @param deliveryCustomerContact; 发货人--(运单--发货客户联系人)
	 * @param deliveryCustomerPhone; 发货人电话
	 * @param deliveryCustomerMobilephone; 发货人手机
	 * @param receiveOrgCode; 发货部门
	 * @param receiveOrgName; 发货部门名称
	 * @param respectiveRegional; 所属区域(运单中的部门编码--调用李俊给的接口，获得所属区域)
	 * @param respectiveRegionalName; 所属区域名称
	 * @param deliveryCustomerCode; 发货人code
	 * @param deliveryCustomerName; 发货人name
	 * @param receiveCustomerContact; 收货人
	 * @param receiveCustomerPhone; 收货人电话
	 * @param lastLoadOrgCode; 到达部门(运单--最终配载部门--在营业部表中，把CODE改成NAME)
	 * @param lastLoadOrgName; 到达部门名称
	 * @param lastStorageCode; 最终配载部门CODE
	 * @param lastStorageName; 最终配载部门NAME
	 * @param codAmount; 代收金额
	 * @param insuranceAmount; 保险金额
	 * @param prePayAmount; 预付金额
	 * @param toPayAmount; 到付金额
	 * @param abandonedgoodsType; 弃货类型--(申请转弃货--弃货类型)
	 * @param status; 处理状态----(申请转弃货--状态)
	 * @param importStatus; 导入状态
	 * @param preabandonedgoodsTime; 预弃货处理时间--(申请转弃货--预弃货时间)
	 * @param createUserName; 预弃货人--(申请转弃货--预弃货人)
	 * @param storageDay; 仓储时长--(T_SRV_ACTUAL_FREIGHT--库存天数)
	 * @param notes; 弃货事由--(申请转弃货--弃货事由)
	 * @param goodsQtyTotal; 货物总件数 void
	 */
	@Transactional
	@Override
	public void saveAbandonGoodsApplicationEntity(AbandonedGoodsDetailDto rtDto) {
		AbandonGoodsApplicationEntity entity = new AbandonGoodsApplicationEntity();
		//当前没有session所以后面的操作人要根据oa返回的操作人来确定
		//弃货
		entity.setId(UUIDUtils.getUUID());
		//id
		entity.setCreateDate(new Date());
		//创建时间
		entity.setWaybillNo(rtDto.getWaybillNo());
		//运单号 
		entity.setAbandonedgoodsType(AbandGoodsApplicationConstants.ABANDGOODS_TYPE_AUTO);
		////类型状态为自动
		entity.setCreateOrgCode(rtDto.getReceiveOrgCode());
		//收货部门名称
		String receiveOrgName = null;
		//收货部门
		if (rtDto.getReceiveOrgCode() != null) {
			receiveOrgName = setUpOrgName(rtDto.getReceiveOrgCode());
			//收货部门名称
		}
		if (StringUtils.isNotEmpty(receiveOrgName)) {
			entity.setCreateOrgName(receiveOrgName);
			//收货部门名称
		} else {
			entity.setCreateOrgName("");
			//收货部门名称
		}

		entity.setNotes("库存超时系统自动弃货， 客户联系人 :" + rtDto.getDeliveryCustomerContact() + " 手机:" + rtDto.getDeliveryCustomerMobilephone());
		//注释 
		entity.setCreateUserCode(AbandGoodsApplicationConstants.DEFAULT_CREATE_USER_CODE);
		//需求上写的是管理员
		entity.setCreateUserName(AbandGoodsApplicationConstants.DEFAULT_CREATE_USER_NAME);
		//是管理员名称[
		//		if (rtDto.getPreabandonedgoodsTime() != null) {
		//			entity.setPreabandonedgoodsTime(rtDto.getPreabandonedgoodsTime());
		//		} else {
		entity.setPreabandonedgoodsTime(new Date());
		//		}
		//预弃货时间 
		entity.setStatus(AbandGoodsApplicationConstants.ABANDGOODS_STATUS_NEW);
		///状态为new
		//插入数据库弃货对象
		entity.setCustomerCooperateStatus(FossConstants.NO);
		abandonGoodsApplicationDao.insertAbandonGoodsApplication(entity);
		// 保存操作历史记录
		UserEntity user = new UserEntity();
		user.setUserName(AbandGoodsApplicationConstants.DEFAULT_CREATE_USER_NAME);
		EmployeeEntity employee = new EmployeeEntity();
		employee.setEmpCode(AbandGoodsApplicationConstants.DEFAULT_CREATE_USER_CODE);
		employee.setEmpName(AbandGoodsApplicationConstants.DEFAULT_CREATE_USER_NAME);
		user.setEmployee(employee);
		OrgAdministrativeInfoEntity dept = new OrgAdministrativeInfoEntity();
		dept.setCode(rtDto.getReceiveOrgCode());
		dept.setName(receiveOrgName);
		CurrentInfo currentInfo = new CurrentInfo(user, dept);
		entity.setOperateContent(ReflectionToStringBuilder.toString(entity));
		insertAgActionHistoryEntity(entity, currentInfo);
	}

	/**
	 * 费用弃货工作流
	 * 
	 * @date 2012-11-28 下午3:32:10
	 */
	@Transactional
	public ResultDto startOaDiscardWorkflow(String id) {
		//功能关闭
		if(true){
			throw new AbandonGoodsException("请从异常货系统转弃货！");
		}
		MutexElement mutexElement = new MutexElement(id, "弃货ID", MutexElementType.ABANDON_GOODS_ID);
		//互斥锁定
		boolean isLocked = businessLockService.lock(mutexElement, NumberConstants.ZERO);
		//若未上锁
		if (!isLocked) {
			//抛出派送单异常
			throw new AbandonGoodsException(DeliverbillException.WAYBILL_LOCKED);
		}
		//查询传递参数dto
		AbandonedGoodsSearchDto dto = new AbandonedGoodsSearchDto();
		AbandonGoodsResultDto resultDto = null;
		List<AbandonGoodsResultDto> abandonedGoodsList = null;
		//Id
		dto.setId(id);
		// 查询弃货
		String abandonGoodsType = abandonGoodsApplicationDao.getAbandonGoodsTypeById(id);		
		if(abandonGoodsType != null &&
				abandonGoodsType.equals(AbandGoodsApplicationConstants.ABANDGOODS_TYPE_NOTAG)){
			abandonedGoodsList = abandonGoodsApplicationDao.searchNoTagAbandonGoodsList(dto);
		}else{			
			abandonedGoodsList = abandonGoodsApplicationDao.searchAbandonGoodsList(dto);
			//查询是否上传附件
			if(CollectionUtils.isEmpty(attachementService.getAttachementInfos(id,AbandGoodsApplicationConstants.PKP_PREDELIVER))){
				//解锁
				businessLockService.unlock(mutexElement);
				return setUpResultDto(ResultDto.FAIL, "非无标签弃货必须上传附件才能起草工作流");
			}
		}		

		//没有弃货 放弃工作流启动
		if (abandonedGoodsList == null || abandonedGoodsList.isEmpty()) {
			//解锁
			businessLockService.unlock(mutexElement);
			return setUpResultDto(ResultDto.FAIL, "没有弃货");
		} else {
			//有弃货
			resultDto = (AbandonGoodsResultDto) abandonedGoodsList.get(0);
			if (resultDto == null) {
				//解锁
				businessLockService.unlock(mutexElement);
				return setUpResultDto(ResultDto.FAIL, "没有弃货");
			}
			//审批通过是不能够再做弃货工作流申请的 只有无状态或者审批失败才可以做弃货工作流申请
			if (AbandGoodsApplicationConstants.ABANDGOODS_STATUS_PASS.equals(resultDto.getStatus())) {
				//解锁
				businessLockService.unlock(mutexElement);
				return setUpResultDto(ResultDto.FAIL, "已经进入弃货流程（已通过）,不需要起草");
			}
			//审批中、工作流号为空、并且是一天前操作的可以继续起草工作流
			if (AbandGoodsApplicationConstants.ABANDGOODS_STATUS_APPROVAL.equals(resultDto.getStatus()) ) {
				Date date = new Date();
				long num = date.getTime() - (resultDto.getOperateTime() == null ? date.getTime() : resultDto.getOperateTime().getTime());
				if (StringUtils.isEmpty(resultDto.getProcessId()) && num >= 86400000) {
					//86400000 = DateUtils.convert("2013-09-23 00:00:00").getTime() - DateUtils.convert("2013-09-22 00:00:00").getTime();
				} else {
					//解锁
					businessLockService.unlock(mutexElement);
					return setUpResultDto(ResultDto.FAIL, "已经进入弃货流程,不需要起草");
				}
			}
		}
		
		//获得开单数据 
		WaybillEntity waybillEntity = null;
		if(abandonGoodsType != null &&
				abandonGoodsType.equals(AbandGoodsApplicationConstants.ABANDGOODS_TYPE_NOTAG)){
			// 无标签转弃货没有运单信息，需要手动拼装一个
			waybillEntity = buildNoTagWayBill(id);
		}else{
			waybillEntity = waybillManagerService.queryWaybillBasicByNo(resultDto.getWaybillNo());			
		}
		//该运单不存在,不能起草弃货工作流
		if (waybillEntity == null) {
			//解锁
			businessLockService.unlock(mutexElement);
			return setUpResultDto(ResultDto.FAIL, "该运单不存在,不能起草弃货工作流");
		}
		//准备消息头信息
		AccessHeader header = createAccessHeader(waybillEntity.getWaybillNo());
		//创建具体消息
		ThrowfreightRequest request = new ThrowfreightRequest();
		//具体消息值设置
		ResultDto validateResult = createThrowfreightRequest(resultDto, waybillEntity, request);
		//验证失败 有数据没有通过验证  直接跳出
		if (validateResult != null) {
			//解锁
			businessLockService.unlock(mutexElement);
			return validateResult;
		}
		AbandonGoodsApplicationEntity abandonentity = new AbandonGoodsApplicationEntity();
		abandonentity.setId(id);
		abandonentity.setStatus(AbandGoodsApplicationConstants.ABANDGOODS_STATUS_APPROVAL);
		// 设置操作内容
		abandonentity.setOperateContent(ReflectionToStringBuilder.toString(header) + ReflectionToStringBuilder.toString(request));
		long processId;
		//发送请求
		try {
//			ESBJMSAccessor.asynReqeust(header, request);
//			//发送请求
//			//发送请求的时候将弃货状态更新为 "审批中"  
//			//如果接口回调失败 或者审批不通过 则更新审批状态 可以重新发起审批 否则不允许 
//			updateAbandonGoodsStatus(resultDto);
			
			/**
			 * 起草工作流start
			 */
			processId=this.workFlowService.createAndStartProcess(resultDto.getWaybillNo(),resultDto.getSerialNumber());
			/**
			 * 起草工作流end 
			 */
			updateAbandonGoodsStatus(resultDto,processId);
		} catch (Exception e) {
			//解锁
			businessLockService.unlock(mutexElement);
			//申请弃货工作流失败
			LOGGER.error(AbandGoodsApplicationConstants.ESB_OA_WORKFLOW_SERVICE_CODE + "弃货工作流失败：", e);
			// 保存操作历史记录
			abandonentity.setOperateContent("申请弃货工作流失败--" + e.getMessage() + "--" + abandonentity.getOperateContent());
			insertAgActionHistoryEntity(abandonentity);
			return setUpResultDto(ResultDto.FAIL, "申请弃货工作流失败");
		}
		// 保存操作历史记录
		insertAgActionHistoryEntity(abandonentity);
		//解锁
		businessLockService.unlock(mutexElement);
		if(processId >0){
			//返回成功
			return setUpResultDto(ResultDto.SUCCESS, "",String.valueOf(processId));
		}else {
			//返回成功
			return setUpResultDto(ResultDto.SUCCESS, "");
		}
		
	}

	/**
	 * 
	 * <p>
	 * 更新发送结果<br />
	 * </p>
	 * 
	 * @author ibm-lizhiguo
	 * @version 0.1 2013-3-19
	 * @param waybillNo void
	 */
	private void updateAbandonGoodsStatus(AbandonGoodsResultDto resultDto,long processId) {
		//弃货对象
		AbandonGoodsApplicationEntity abandonentity = new AbandonGoodsApplicationEntity();
		abandonentity.setWaybillNo(resultDto.getWaybillNo());
		abandonentity.setId(resultDto.getId());
		//运单号
		abandonentity.setStatus(AbandGoodsApplicationConstants.ABANDGOODS_STATUS_APPROVAL);
		abandonentity.setOperateOrgCode(FossUserContextHelper.getOrgCode());
		abandonentity.setOperatorCode(FossUserContextHelper.getUserCode());
		abandonentity.setCustomerCooperateStatus(FossConstants.NO);//默认都是客户配合 需求DMANA-818
		abandonentity.setOperateTime(new Date());
		abandonentity.setProcessId(String.valueOf(processId));//起草工作流时将工作流ID置为空
		//状态 审批中
		abandonGoodsApplicationDao.updateByKey(abandonentity);
		//update data
		// 设置操作内容
		abandonentity.setOperateContent(ReflectionToStringBuilder.toString(abandonentity));
		// 保存操作历史记录
		insertAgActionHistoryEntity(abandonentity);
	}

	/**
	 * 
	 * <p>
	 * 具体消息值设置<br />
	 * </p>
	 * 
	 * @author ibm-lizhiguo
	 * @version 0.1 2013-3-19
	 * @param resultDto
	 * @param waybillEntity
	 * @param request * @param waybillNo; 运单号
	 * @param goodsName; 货物名称--运单
	 * @param goodsWeightTotal; 重量--运单
	 * @param goodsVolumeTotal; 体积--运单
	 * @param deliveryCustomerContact; 发货人--(运单--发货客户联系人)
	 * @param deliveryCustomerPhone; 发货人电话
	 * @param deliveryCustomerMobilephone; 发货人手机
	 * @param receiveOrgCode; 发货部门
	 * @param receiveOrgName; 发货部门名称
	 * @param respectiveRegional; 所属区域(运单中的部门编码--调用李俊给的接口，获得所属区域)
	 * @param respectiveRegionalName; 所属区域名称
	 * @param deliveryCustomerCode; 发货人code
	 * @param deliveryCustomerName; 发货人name
	 * @param receiveCustomerContact; 收货人
	 * @param receiveCustomerPhone; 收货人电话
	 * @param lastLoadOrgCode; 到达部门(运单--最终配载部门--在营业部表中，把CODE改成NAME)
	 * @param lastLoadOrgName; 到达部门名称
	 * @param lastStorageCode; 最终配载部门CODE
	 * @param lastStorageName; 最终配载部门NAME
	 * @param codAmount; 代收金额
	 * @param insuranceAmount; 保险金额
	 * @param prePayAmount; 预付金额
	 * @param toPayAmount; 到付金额
	 * @param abandonedgoodsType; 弃货类型--(申请转弃货--弃货类型)
	 * @param status; 处理状态----(申请转弃货--状态)
	 * @param importStatus; 导入状态
	 * @param preabandonedgoodsTime; 预弃货处理时间--(申请转弃货--预弃货时间)
	 * @param createUserName; 预弃货人--(申请转弃货--预弃货人)
	 * @param storageDay; 仓储时长--(T_SRV_ACTUAL_FREIGHT--库存天数)
	 * @param notes; 弃货事由--(申请转弃货--弃货事由)
	 * @param goodsQtyTotal; 货物总件数
	 * @return ResultDto
	 */
	private ResultDto createThrowfreightRequest(AbandonGoodsResultDto resultDto, WaybillEntity waybillEntity, ThrowfreightRequest request) {
		// 申请人
		request.setApplicantName(FossUserContextHelper.getUserName());
		// 申请人code
		request.setApplypersoncode(FossUserContextHelper.getUserCode());
		//get org code
		String orgCode = FossUserContextHelper.getOrgCode(); 
		// 查询大区类型
		List<String> bizTypes = new ArrayList<String>();		
		bizTypes.add(BizTypeConstants.ORG_BIG_REGION);
		//当前人部门找到当前的上级事业部 然后找事业部的标杆编码
		OrgAdministrativeInfoEntity org = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoByCode(orgCode, bizTypes);
		//查询大区类型找不到
		if (org == null) {
			bizTypes = new ArrayList<String>();
			//	查询部门类型列表
			bizTypes.add(BizTypeConstants.ORG_DIVISION);
			//事业部
			//当前人部门找到当前的上级事业部 然后找事业部的标杆编码
			org = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoByCode(orgCode, bizTypes);
			if (org == null) {
				//找不到事业部
				return setUpResultDto(ResultDto.FAIL, "申请弃货工作流失败,没有大区,没有事业部,请手动起草");
			}
		}
		//标杆编码
		String unifiedCode = org.getUnifiedCode();
		if (unifiedCode == null) {
			//没有标杆编码
			return setUpResultDto(ResultDto.FAIL, "申请弃货工作流失败,没有标杆编码,请手动起草");
		}
		request.setAreaCode(unifiedCode);
		// 运单号
		request.setWaybillNumber(waybillEntity.getWaybillNo());
		// 收货日期
		request.setReceivedDate(waybillEntity.getBillTime());
		// 货物名称
		request.setGoodsName(waybillEntity.getGoodsName());
		// 收货部门
		OrgAdministrativeInfoEntity receiveOrg = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeNoCache(waybillEntity.getReceiveOrgCode());
		if(receiveOrg != null){
			request.setReceiveDept(receiveOrg.getName());			
		}
		// 到达部门
		OrgAdministrativeInfoEntity arriveOrg = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeNoCache(waybillEntity.getLastLoadOrgCode());
		if(arriveOrg != null){
			request.setFetchDept(arriveOrg.getName());			
		}
		// 无标签弃货有仓储部门，其他按原来的逻辑，与到达部门一致
		if(AbandGoodsApplicationConstants.ABANDGOODS_TYPE_NOTAG.equals(resultDto.getAbandonedgoodsType())){
			String lastStorageOrgCode = resultDto.getLastStorageOrgCode();
			OrgAdministrativeInfoEntity storageOrg =
					orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeNoCache(lastStorageOrgCode);
			if(storageOrg != null){
				request.setStorageDept(storageOrg.getName());
			}
		}else{
			request.setStorageDept(arriveOrg.getName());
		}
		
		// 到付金额
		if (waybillEntity.getToPayAmount() != null) {
			request.setFetchAmt(waybillEntity.getToPayAmount());			
		} else {
			request.setFetchAmt(ZERO);
		}
		// 预付金额
		if (waybillEntity.getPrePayAmount() != null) {
			request.setPrepayAmt(waybillEntity.getPrePayAmount());			
		} else {
			request.setPrepayAmt(ZERO);
		}
		// 件数
		request.setAmount(waybillEntity.getGoodsQtyTotal());		
		// 保险价值
		request.setBless(waybillEntity.getInsuranceAmount());		
		// 重量
		request.setWeight(waybillEntity.getGoodsWeightTotal().doubleValue());
		// 代收货款金额
		if (waybillEntity.getCodAmount() == null) {
			request.setSupplyGoodAmt(ZERO);			
		} else {
			request.setSupplyGoodAmt(waybillEntity.getCodAmount());
		}
		// 体积
		request.setVolume(waybillEntity.getGoodsVolumeTotal().doubleValue());		
	/*	//自动 不配合   DMANA-818 库存超过90天自动转弃货提醒优化
		if (StringUtils.equals(FossConstants.YES, resultDto.getCustomerCooperateStatus())) {
			//处理类型  -- 客户不予配合处理 
			request.setDisposeType(AbandGoodsApplicationConstants.OA_DISCART_TYPE_UNWILLNESS);
		} else {*/
			//手动 配合 处理类型   -- 有弃货证明/赔偿协议/无标签货
		request.setDisposeType(AbandGoodsApplicationConstants.OA_DISCART_TYPE_WILLNESS);
	//	}
		// 申请事由
		request.setReason(resultDto.getNotes());
		
		// ISSUE-4377 添加两个新字段
		// 流水号
		request.setSerialNumber(resultDto.getSerialNumber());
		// OA差错编号
		request.setErrorNumber(resultDto.getErrorNumber());
		
		//申请事由
		//获得文件附件列表
		List<AttachementEntity> abandonedGoodsFiles = attachementService.getAttachementInfos(resultDto.getId(), null);
		if (CollectionUtils.isNotEmpty(abandonedGoodsFiles)) {
			List<FileBean> fileBeans = request.getFile();
			for (AttachementEntity att : abandonedGoodsFiles) {
				FileBean fileBean = new FileBean();
				fileBean.setFileName(att.getFileName());
				fileBean.setSavePath(att.getRelativePath());
				fileBeans.add(fileBean);
			}
		}
		return null;
	}
	
	/**
	 * 为中转提供的无标签转弃货接口 
	 * ISSUE-4377
	 * @param entity
	 * @author 153161-lufeifei
	 */
	public void insertAgEntityForNoTagTransfer(AbandonGoodsApplicationEntity entity){	
		//功能关闭，不让无标签转弃货
		if(true){
			throw new AbandonGoodsException("差错项目上线前，不让无标签转弃货！");
		}
		// 运单
		if(entity.getWaybillNo() == null){
			throw new AbandonGoodsException("waybillNo不能为空");
		}
		// 流水号
		if(entity.getSerialNumber() == null){
			throw new AbandonGoodsException("serialNumber不能为空");
		}
		// 验证是否有审批、新增的弃货
		AbandonedGoodsSearchDto dto = new AbandonedGoodsSearchDto();
		dto.setWaybillNo(entity.getWaybillNo());
		dto.setSerialNumber(entity.getSerialNumber());
		List<AbandonGoodsResultDto> list = abandonGoodsApplicationDao.searchNoTagAbandonGoodsList(dto);
		if (list != null && list.size() > 0) {
			for(AbandonGoodsResultDto temp : list){
				if(!AbandGoodsApplicationConstants.ABANDGOODS_STATUS_REFUSE.equals(temp.getAbandonedgoodsType())){
					throw new AbandonGoodsException("运单与流水号已转弃货，无法再次提交，请至处理弃货页面查询。");
				}
			}
		}
		// 申请人编码
		if(entity.getCreateUserCode() == null){
			throw new AbandonGoodsException("createUserCode不能为空");
		}
		// 申请人名称
		if(entity.getCreateUserName() == null){
			throw new AbandonGoodsException("createUserName不能为空");
		}
		// 申请事由
		if(entity.getNotes() == null){
			throw new AbandonGoodsException("notes不能为空");
		}
		// OA差错编号
		if(entity.getErrorNumber() == null){
			throw new AbandonGoodsException("errorNumber不能为空");
		}
		// 货物名称
		if(entity.getGoodsName() == null){
			throw new AbandonGoodsException("goodsName不能为空");
		}
		// 货物总重量
		if(entity.getGoodsWeightTotal() == null){
			throw new AbandonGoodsException("goodsWeightTotal不能为空");
		}
		// 货物总体积
		if(entity.getGoodsVolumeTotal() == null){
			throw new AbandonGoodsException("goodsVolumeTotal不能为空");
		}
		// 货物总件数
		if(entity.getGoodsQtyTotal() == null){
			throw new AbandonGoodsException("goodsQtyTotal不能为空");
		}
		// 库存时长
		if(entity.getStorageDay() == null){
			throw new AbandonGoodsException("storageDay不能为空");
		}
		// 仓储部门code
		if(entity.getLastStorageOrgCode() == null){
			throw new AbandonGoodsException("lastStorageOrgCode不能为空");
		}
		// 所属区域code
		if(entity.getBelongAreaCode() == null){
			throw new AbandonGoodsException("belongAreaCode不能为空");
		}
		
		// 当前操作时间
		Date currentDate = new Date();
		//id
		entity.setId(UUIDUtils.getUUID());
		// 处理类型为“无标签货”
		entity.setAbandonedgoodsType(AbandGoodsApplicationConstants.ABANDGOODS_TYPE_NOTAG);
		// 预弃货时间 
		entity.setPreabandonedgoodsTime(currentDate);
		// 状态
		entity.setStatus(AbandGoodsApplicationConstants.ABANDGOODS_STATUS_NEW);
		// 操作信息
		EmployeeEntity emp = FossUserContext.getCurrentUser().getEmployee();
		OrgAdministrativeInfoEntity org = FossUserContext.getCurrentDept();		
		// 操作人姓名
		entity.setOperator(emp.getEmpName());
		// 操作人编码
		entity.setOperatorCode(emp.getEmpCode());
		// 操作部门编码
		entity.setCreateOrgCode(org.getCode());
		entity.setOperateOrgCode(org.getCode());
		// 操作部门名称
		entity.setCreateOrgName(org.getName());
		entity.setOperateOrgName(org.getName());
		// 操作时间
		entity.setOperateTime(currentDate);
		// “有弃货证明/赔偿协议/无标签货” 为 “配合”
		entity.setCustomerCooperateStatus("N");
		
		abandonGoodsApplicationDao.insertAbandonGoodsApplication(entity);
	}

	/**
	 * 
	 * 保存弃货操作历史记录
	 * 
	 * @author ibm-wangfei
	 * @date Apr 24, 2013 5:56:55 PM
	 */
	@Override
	public void insertAgActionHistoryEntity(AbandonGoodsApplicationEntity abandonentity) {
		AgActionHistoryEntity record = new AgActionHistoryEntity();
		record.settSrvAbandonApplicationId(abandonentity.getId());
		record.setOperateOrgCode(FossUserContextHelper.getOrgCode());
		record.setOperateOrgName(FossUserContextHelper.getOrgName());
		record.setOperateTime(new Date());
		record.setOperator(FossUserContextHelper.getUserName());
		record.setOperatorCode(FossUserContextHelper.getUserCode());
		record.setStatus(abandonentity.getStatus());
		record.setOperateContent(abandonentity.getOperateContent());
		this.agActionHistoryDao.insertSelective(record);
	}

	/**
	 * 
	 * 保存弃货操作历史记录-- JOB运行时保存
	 * 
	 * @author ibm-wangfei
	 * @date Apr 24, 2013 5:56:55 PM
	 */
	@Override
	public void insertAgActionHistoryEntity(AbandonGoodsApplicationEntity abandonentity, CurrentInfo currentInfo) {
		AgActionHistoryEntity record = new AgActionHistoryEntity();
		record.settSrvAbandonApplicationId(abandonentity.getId());
		record.setOperateOrgCode(currentInfo.getCurrentDeptCode());
		record.setOperateOrgName(currentInfo.getCurrentDeptName());
		record.setOperateTime(new Date());
		record.setOperator(currentInfo.getEmpName());
		record.setOperatorCode(currentInfo.getEmpCode());
		record.setStatus(abandonentity.getStatus());
		record.setOperateContent(abandonentity.getOperateContent());
		this.agActionHistoryDao.insertSelective(record);
	}

	/**
	 * 
	 * <p>
	 * 准备头信息<br />
	 * </p>
	 * 
	 * @author ibm-lizhiguo
	 * @version 0.1 2013-3-19
	 * @param waybillNo
	 * @return AccessHeader
	 */
	private AccessHeader createAccessHeader(String waybillNo) {
		AccessHeader header = new AccessHeader();
		//business code 根据esb提供
		header.setEsbServiceCode(AbandGoodsApplicationConstants.ESB_OA_WORKFLOW_SERVICE_CODE);
		//版本随意
		header.setVersion(AbandGoodsApplicationConstants.ESB_OA_WORKFLOW_SERVICE_VERSION);
		//business id 随意
		header.setBusinessId(waybillNo);
		//运单号放在消息头中传给oa waybillNo
		header.setBusinessDesc1(waybillNo);
		return header;
	}

	/**
	 * 结果 setup workflow result dto
	 */
	private ResultDto setUpResultDto(String code, String msg) {
		ResultDto res = new ResultDto();
		//创建结果对象
		res.setCode(code);
		//错误编码
		res.setMsg(msg);
		//中文内容
		return res;
	}
	/**
	 * 结果 setup workflow result dto
	 */
	private ResultDto setUpResultDto(String code, String msg,String processId) {
		ResultDto res = new ResultDto();
		//创建结果对象
		res.setCode(code);
		//错误编码
		res.setMsg(msg);
		res.setProcessId(processId);//返回工作流ID
		//中文内容
		return res;
	}
	/**
	 * 拼装无标签弃货的运单信息
	 * @param abandonGoodsAppId
	 * @return
	 */
	private WaybillEntity buildNoTagWayBill(String abandonGoodsAppId){
		WaybillEntity entity = new WaybillEntity();
		AbandonedGoodsDetailDto detailDto =
				abandonGoodsApplicationDao.getNoTagAbandonGoodsDetailById(abandonGoodsAppId);
		// 运单号
		entity.setWaybillNo(detailDto.getWaybillNo());
		// 收货日期
		entity.setBillTime(null);
		// 货物名称
		entity.setGoodsName(detailDto.getGoodsName());
		// 收货部门
		entity.setReceiveOrgCode(null);
		// 到达部门
		entity.setLastLoadOrgCode(detailDto.getLastLoadOrgCode());
		// 到付金额
		entity.setToPayAmount(detailDto.getToPayAmount());
		// 预付金额
		entity.setPrePayAmount(detailDto.getPrePayAmount());
		// 件数
		entity.setGoodsQtyTotal(detailDto.getGoodsQtyTotal());
		// 保险价值
		entity.setInsuranceAmount(detailDto.getInsuranceAmount());
		// 重量
		entity.setGoodsWeightTotal(detailDto.getGoodsWeightTotal());
		// 代收货款金额
		entity.setCodAmount(detailDto.getCodAmount());
		// 体积
		entity.setGoodsVolumeTotal(detailDto.getGoodsVolumeTotal());
		
		return entity;
	}

	/**
	 * 
	 * <p>
	 * 更新导入数据状态和时间<br />
	 * </p>
	 * 
	 * @author ibm-lizhiguo
	 * @version 0.1 2013-3-21
	 * @param record
	 * @return int
	 */
	@Override
	public int updateByPrimaryKeySelective(AbandonGoodsImportEntity record) {
		return abandonGoodsImportDao.updateByPrimaryKeySelective(record);
	}

	/**
	 * 
	 * <p>
	 * 注入<br />
	 * </p>
	 * 
	 * @author ibm-lizhiguo
	 * @version 0.1 2013-3-19
	 * @param abandonGoodsImportDao void
	 */
	public void setAbandonGoodsImportDao(IAbandonGoodsImportDao abandonGoodsImportDao) {
		this.abandonGoodsImportDao = abandonGoodsImportDao;
	}

	/**
	 * 
	 * <p>
	 * 注入<br />
	 * </p>
	 * 
	 * @author ibm-lizhiguo
	 * @version 0.1 2013-3-19
	 * @param waybillManagerService void
	 */
	public void setWaybillManagerService(IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}

	/**
	 * 
	 * <p>
	 * 注入<br />
	 * </p>
	 * 
	 * @author ibm-lizhiguo
	 * @version 0.1 2013-3-19
	 * @param orgAdministrativeInfoComplexService void
	 */
	public void setOrgAdministrativeInfoComplexService(IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	/**
	 * 
	 * <p>
	 * 注入<br />
	 * </p>
	 * 
	 * @author ibm-lizhiguo
	 * @version 0.1 2013-3-19
	 * @param orgAdministrativeInfoService void
	 */
	public void setOrgAdministrativeInfoService(IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	public void setAbandonGoodsApplicationService(IAbandonGoodsApplicationService abandonGoodsApplicationService) {
		this.abandonGoodsApplicationService = abandonGoodsApplicationService;
	}

	public void setAgActionHistoryDao(IAgActionHistoryDao agActionHistoryDao) {
		this.agActionHistoryDao = agActionHistoryDao;
	}

	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}

	/**
	 * 在可编辑状态下修改转弃货申请数据，可上传附件功能
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-5-14 13:45:08
	 * @param abandonGoodsApplicationEntity
	 * @param attachementFiles
	 */
	@Override
	public void editAbandonGoodAttachFiles(
			AbandonGoodsApplicationEntity abandonGoodsApplicationEntity,
			List<AttachementEntity> attachementFiles) {
		if(abandonGoodsApplicationEntity != null){
			//修改转弃货申请数据
			abandonGoodsApplicationDao.editAbandonGoodsApplication(abandonGoodsApplicationEntity);
			
			//修改附件功能
			saveAttachment(attachementFiles, abandonGoodsApplicationEntity.getId());
		}		
	}
}