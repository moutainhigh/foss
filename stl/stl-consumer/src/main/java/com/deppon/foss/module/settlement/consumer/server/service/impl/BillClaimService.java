/**
 * Copyright 2013 STL TEAM
 */
/*******************************************************************************
 * Copyright 2013 STL TEAM
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: stl-consumer
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/settlement/consumer/server/service/impl/BillClaimService.java
 * 
 * FILE NAME        	: BillClaimService.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.consumer.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.cxf.common.util.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import com.deppon.crm.inteface.foss.domain.ReturnVoucherPaymentResultRequest;
import com.deppon.esb.api.ESBJMSAccessor;
import com.deppon.esb.api.domain.AccessHeader;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPayableService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillClaimEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.server.dao.IBillClaimDao;
import com.deppon.foss.module.settlement.consumer.api.server.service.IBillClaimService;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.BillClaimQueryDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.BillClaimResultDto;
import com.deppon.foss.util.define.FossConstants;

/**
 * 理赔、服务补救、退运费单据service
 * 
 * @author foss-qiaolifeng
 * @date 2013-1-25 上午10:15:23
 */
public class BillClaimService implements IBillClaimService {

	private static final Logger LOGGER = LogManager
			.getLogger(BillClaimService.class);

	/**
	 * 服务编码
	 */
	private static final String SERVICE_CODE = "ESB_FOSS2ESB_NOTIFY_CLAIMS_STATE";

	/**
	 * 版本号
	 */
	private static final String VERSION = "1.0";

	/**
	 * 业务描述
	 */
	private static final String BUSINESS_DESC = "退回理赔应付单";

	/**
	 * 理赔DAO
	 */
	private IBillClaimDao billClaimDao;

	/**
	 * 应付单服务
	 */
	private IBillPayableService billPayableService;

	/**
	 * 组织服务
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;

	/**
	 * 查询理赔、服务补救、退运费单据列表
	 * 
	 * @author foss-qiaolifeng
	 * @date 2013-1-29 下午3:11:21
	 * @see com.deppon.foss.module.settlement.consumer.api.server.service.IBillClaimService#queryBillClaimList(com.deppon.foss.module.settlement.consumer.api.shared.dto.BillClaimQueryDto)
	 */
	@Override
	public BillClaimResultDto queryBillClaimList(
			BillClaimQueryDto billClaimQueryDto, int start, int limit,
			CurrentInfo cInfo) {

		LOGGER.debug("分页查询理赔单开始...");

		if (billClaimQueryDto == null) {
			throw new SettlementException("输入参数为空,查询理赔信息失败!");
		}

		// 设置条件
		setBillClaimQueryDto(billClaimQueryDto);

		// 设置当前登录用户员工编码
		billClaimQueryDto.setEmpCode(cInfo.getEmpCode());

		// 初始化返回dto
		BillClaimResultDto rtnDto = new BillClaimResultDto();

		// 如果运单号为空，按参数查询
		if (CollectionUtils.isEmpty(billClaimQueryDto.getWayBillNos())) {

			// 按参数查询总条数
			Long billClaimTotalRows = billClaimDao
					.queryBillClaimCount(billClaimQueryDto);

			// 如果总条数>0，分页查询数据
			if (billClaimTotalRows > 0) {
				List<BillClaimEntity> billClaimEntityList = billClaimDao
						.queryBillClaimListByPage(billClaimQueryDto, start,
								limit);

				rtnDto.setBillClaimEntityList(billClaimEntityList);
				rtnDto.setBillClaimTotalRows(billClaimTotalRows);
			}

			// 否则，按运单号查询
		} else {
			
			/*
			 * 添加查询种类为空时的条件，条件为空时是由服务补救/退运费界面传来
			 * 只能查询服务补救加退运费
			 * 此处设计不合理，由于dto中type和payableType为String 无法多传值暂时先查询两次
			 */
			List<BillClaimEntity> billClaimEntityList = new ArrayList<BillClaimEntity>();
			if(null != billClaimQueryDto.getType()&& !"".equals(billClaimQueryDto.getType())){
				billClaimEntityList = billClaimDao
						.queryBillClaimListByWayBillNos(billClaimQueryDto);				
			} else {
				//查询服务补救 + 退运费
				billClaimQueryDto.setPayableBillType(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__COMPENSATION);
				billClaimEntityList.addAll(billClaimDao.queryBillClaimListByWayBillNos(billClaimQueryDto));
				
				billClaimQueryDto.setPayableBillType(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__REFUND);
				billClaimEntityList.addAll(billClaimDao.queryBillClaimListByWayBillNos(billClaimQueryDto));
			}

			rtnDto.setBillClaimEntityList(billClaimEntityList);
			rtnDto.setBillClaimTotalRows(Long.valueOf(billClaimEntityList.size()));
		}

		LOGGER.debug("分页查询理赔单结束...");
		return rtnDto;
	}

	/**
	 * 根据运单号退回理赔、服务补救、退运费单
	 * 
	 * @author foss-qiaolifeng
	 * @date 2013-1-29 下午3:11:32
	 * @see com.deppon.foss.module.settlement.consumer.api.server.service.IBillClaimService#returnBillClainEntity(java.lang.String)
	 */
	@Override
	@Transactional
	public void returnBillClainEntity(String waybillNo,
			CurrentInfo currentInfo, String rtnReason) {

		// 验证退回理赔应付运单号
		if (StringUtils.isEmpty(waybillNo)) {
			throw new SettlementException("退回理赔/服务补救/退运费应付时,所选运单号为空!");
		}

		// 1、 根据运单号查询理赔信息
		BillClaimEntity billClaimEntity = billClaimDao.queryBillClaimEntity(
				waybillNo, FossConstants.ACTIVE);

		// 运单号list
		List<String> waybillNoList = new ArrayList<String>();
		waybillNoList.add(waybillNo);

		// 应付单理赔类型list
		List<String> billTypeList = new ArrayList<String>();
		billTypeList
				.add(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__CLAIM);
		billTypeList
				.add(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__COMPENSATION);
		billTypeList
				.add(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__REFUND);

		// 2、 根据运单号查询理赔/服务补救应付单
		List<BillPayableEntity> list = billPayableService
				.queryByWaybillNosAndByBillTypes(waybillNoList, billTypeList);
		BillPayableEntity billPayableEntity = null;
		if (list.size() == 1) {
			billPayableEntity = list.get(0);
		} else if (list.size() < 1) {
			throw new SettlementException("理赔/服务补救/退运费应付单已不存在,请重新查询!");
		} else if (list.size() > 1) {
			throw new SettlementException("存在多个理赔应付单,数据异常,请确认数据正确性!");
		}

		// 3、验证理赔应付单和理赔信息，是否可以退回，不可以给出提示
		// 如果理赔或者理赔应付单其中一个为空，提示理赔应付已经发生变化，重新查询界面
		if (billClaimEntity == null) {
			throw new SettlementException("理赔/服务补救/退运费应付信息已发生变更,请重新查询!");

			// 如果应付单的支付状态为已支付（通过付款单号来判断）
		} else if (billPayableEntity!=null && !StringUtils.isEmpty(billPayableEntity.getPaymentNo())
				&& !SettlementConstants.DEFAULT_BILL_NO
						.equals(billPayableEntity.getPaymentNo())) {
			throw new SettlementException("应付单支付中或已支付，不能进行后续操作!");

			// 如果应付单的已核销金额>0，提示应付单已核销，不能进行后续操作
		} else if (billPayableEntity.getVerifyAmount().compareTo(
				BigDecimal.ZERO) == 1) {
			throw new SettlementException("应付单已核销，不能进行后续操作!");

			// 如果理赔信息的发送状态不是未发送，提示理赔信息退回中或已退回
		} else if (!SettlementDictionaryConstants.BILL_CLAIM__RETURN__STATUS__NOT_RETURN
				.equals(billClaimEntity.getStatus())) {
			throw new SettlementException("理赔/服务补救/退运费应付单退回中或者已退回，不能再次进行退回操作!");
		}

		// 4、验证通过，红冲理赔应付单
		billPayableService.writeBackBillPayable(billPayableEntity, currentInfo);

		// 5、验证通过，修改理赔信息,变更理赔信息为无效、发送中
		billClaimEntity.setModifyTime(new Date());// 修改时间
		billClaimEntity.setModifyUserCode(currentInfo.getEmpCode());// 修改人编码
		billClaimEntity.setModifyUserName(currentInfo.getEmpName());// 修改人名称
		billClaimEntity.setActive(FossConstants.INACTIVE);// 无效

		// 只有理赔才调用CRM接口，所以改变状态为发送中
		if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__CLAIM
				.equals(billClaimEntity.getType())) {
			billClaimEntity
					.setStatus(SettlementDictionaryConstants.BILL_CLAIM__RETURN__STATUS_RETURNING);// 发送中
		}

		// 修改理赔信息
		int i = billClaimDao.updateBillClaimEntity(billClaimEntity);
		if (i != 1) {
			throw new SettlementException("更新理赔/服务补救信息失败,该理赔/服务补救信息不存在或者不唯一!");
		}

		// 6、验证通过，若为理赔调用ESB接口，发送退回信息（服务补救暂不调用CRM接口）
		if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__CLAIM
				.equals(billClaimEntity.getType())) {
			returnClaimPayable(billClaimEntity, rtnReason);
		}
	}

	/**
	 * 新增理赔、服务补救、退运费单据
	 * 
	 * @author foss-qiaolifeng
	 * @date 2013-1-25 上午10:15:26
	 * @see com.deppon.foss.module.settlement.consumer.api.server.service.IBillClaimService#addBillClaimEntity(com.deppon.foss.module.settlement.common.api.shared.domain.BillClaimEntity)
	 */
	@Override
	public int addBillClaimEntity(BillClaimEntity entity,
			CurrentInfo currentInfo) {

		// 实体不能为空
		if (entity == null || StringUtils.isEmpty(entity.getId())) {
			throw new SettlementException("生成理赔、服务补救、退运费单据时参数异常!");

			// 运单号不能为空
		} else if (StringUtils.isEmpty(entity.getWaybillNo())) {
			throw new SettlementException("生成理赔、服务补救、退运费单据时运单号不能为空!");
			// 部门编码不能为空
		} else if (StringUtils.isEmpty(entity.getPayableOrgCode())) {
			throw new SettlementException("生成理赔、服务补救、退运费单据时理赔应付部门不能为空!");
			// 客户编码不能为空
		} else if (StringUtils.isEmpty(entity.getCustomerCode())) {
			throw new SettlementException("生成理赔、服务补救、退运费单据时客户编码不能为空!");
			// 理赔金额不能为空
		} else if (entity.getAmount() == null) {
			throw new SettlementException("生成理赔、服务补救、退运费单据时理赔金额不能为空!");
			// 理赔金额<=0时，理赔金额必须大于0
		} else if (entity.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
			throw new SettlementException("生成理赔、服务补救、退运费单据时理赔金额必须大于0!");
		}

		LOGGER.info("生成理赔、服务补救、退运费单据开始,运单号：" + entity.getWaybillNo());

		// 设置创建者名称、编码、修改者名称、编码
		entity.setCreateUserCode(currentInfo.getEmpCode());
		entity.setCreateUserName(currentInfo.getEmpName());
		entity.setModifyUserCode(currentInfo.getEmpCode());
		entity.setModifyUserName(currentInfo.getEmpName());

		// 调用新增
		int i = billClaimDao.addBillClaimEntity(entity);

		// 判断新增是否异常
		if (i != 1) {
			throw new SettlementException("新增理赔、服务补救、退运费单据失败!");
		}
		LOGGER.info("生成理赔、服务补救、退运费单据完成!");

		return i;
	}

	/**
	 * 查询理赔、服务补救、退运费单据根据根据运单号
	 * 
	 * @author foss-qiaolifeng
	 * @date 2013-1-25 上午10:15:28
	 * @see com.deppon.foss.module.settlement.consumer.api.server.service.IBillClaimService#queryBillClaimEntity(java.lang.String)
	 */
	@Override
	public BillClaimEntity queryBillClaimEntity(String waybillNo) {

		// 运单号不能为空
		if (StringUtils.isEmpty(waybillNo)) {
			throw new SettlementException("运单号不能为空!");
		}

		// 根据运单号查询理赔等单据并返回
		return billClaimDao.queryBillClaimEntity(waybillNo,
				FossConstants.ACTIVE);
	}

	/**
	 * 退回理赔应付信息
	 * 
	 * @author foss-qiaolifeng
	 * @date 2013-1-30 下午4:28:31
	 */
	private void returnClaimPayable(BillClaimEntity billClaimEntity,
			String rtnReason) {

		try {

			// 构建消息头

			AccessHeader header = new AccessHeader();
			header.setBusinessId(billClaimEntity.getWaybillNo());
			header.setEsbServiceCode(SERVICE_CODE);
			header.setVersion(VERSION);
			header.setBusinessDesc1(BUSINESS_DESC);

			// 构建发送消息实体
			ReturnVoucherPaymentResultRequest request = new ReturnVoucherPaymentResultRequest();

			// 运单号、部门编码、金额、客户编码、付款方式、结果、原因
			request.setWaybillNum(billClaimEntity.getWaybillNo());
			request.setAmount(billClaimEntity.getAmount());
			request.setCustCode(billClaimEntity.getCustomerCode());

			// 获取部门标杆编码
			OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoService
					.queryOrgAdministrativeInfoByCode(billClaimEntity
							.getPayableOrgCode());
			request.setDeptCode(orgEntity.getUnifiedCode());
			request.setPaymentType(billClaimEntity.getPaymentCategories());
			request.setPayResult(false);
			request.setReason(rtnReason);

			// 发送消息
			ESBJMSAccessor.asynReqeust(header, request);

		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
			throw new SettlementException("退回理赔应付单失败：" + ex.getMessage());
		}
	}

	/**
	 * 设置理赔查询的默认条件
	 * 
	 * @author foss-qiaolifeng
	 * @date 2013-1-29 下午4:33:39
	 */
	private void setBillClaimQueryDto(BillClaimQueryDto billClaimQueryDto) {

		// 有效
		billClaimQueryDto.setActive(FossConstants.ACTIVE);

		// 应付单支付状态为未支付
		billClaimQueryDto.setPayStatus(SettlementDictionaryConstants.BILL_PAYABLE__PAY_STATUS__NO);

		// 理赔
		if (SettlementDictionaryConstants.BILL_CLAIM__TYPE__CLAIM
				.equals(billClaimQueryDto.getType())) {

			// 理赔应付单单据类型为理赔
			billClaimQueryDto
					.setPayableBillType(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__CLAIM);

			// 服务补救
		} else if (SettlementDictionaryConstants.BILL_CLAIM__TYPE__COMPENSATION
				.equals(billClaimQueryDto.getType())) {

			// 理赔应付单单据类型为服务补救
			billClaimQueryDto
					.setPayableBillType(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__COMPENSATION);

			// 退运费
		} else if (SettlementDictionaryConstants.BILL_CLAIM__TYPE__REFUND
				.equals(billClaimQueryDto.getType())) {

			// 理赔应付单单据类型为退运费
			billClaimQueryDto
					.setPayableBillType(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__REFUND);

		}
	}

	public IBillClaimDao getBillClaimDao() {
		return billClaimDao;
	}

	public void setBillClaimDao(IBillClaimDao billClaimDao) {
		this.billClaimDao = billClaimDao;
	}

	public IBillPayableService getBillPayableService() {
		return billPayableService;
	}

	public void setBillPayableService(IBillPayableService billPayableService) {
		this.billPayableService = billPayableService;
	}

	public IOrgAdministrativeInfoService getOrgAdministrativeInfoService() {
		return orgAdministrativeInfoService;
	}

	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

}
