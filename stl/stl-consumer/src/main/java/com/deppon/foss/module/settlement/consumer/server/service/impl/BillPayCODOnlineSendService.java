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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/settlement/consumer/server/service/impl/BillPayCODOnlineSendService.java
 * 
 * FILE NAME        	: BillPayCODOnlineSendService.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.consumer.server.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.custom.yq.refund.RefundBillInfo;
import com.deppon.custom.yq.refund.SysRefundbillRequest;
import com.deppon.esb.api.ESBJMSAccessor;
import com.deppon.esb.api.domain.AccessHeader;
import com.deppon.esb.core.exception.ESBBusinessException;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementESBDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.server.service.IBillPayCODOnlineSendService;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CODMergeDto;
import com.deppon.foss.service.platformservice.CommonException;

/**
 * 
 * 将代收货款数据发送给银企
 * 
 * @author 046644-foss-zengbinwen
 * @date 2012-11-19 上午10:34:13
 */
public class BillPayCODOnlineSendService implements
		IBillPayCODOnlineSendService {

	/**
	 * 日志
	 */
	private static final Logger LOGGER = LogManager
			.getLogger(BillPayCODOnlineSendService.class);
	
	/** 组织信息 Service实现. */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	

	/**
	 * 
	 * 发送代收货款到银企
	 * 
	 * @author 046644-foss-zengbinwen
	 * @throws CommonException 
	 * @date 2012-11-19 上午10:38:33
	 */
	@Override
	public void billPayCODOnlineSend(String batchNo, List<CODMergeDto> mergeCodPayableDtoList) throws CommonException {

		// 代收货款批次号不能为空
		if (StringUtils.isEmpty(batchNo)) {
			throw new SettlementException("代收货款批次号不能为空.");
		}

		// 代收货款数据不能为空
		if (CollectionUtils.isEmpty(mergeCodPayableDtoList)) {
			throw new SettlementException("代收货款数据不能为空.");
		}

		try {
			// 构造发送对象和表头
			SysRefundbillRequest request = buildRequest(batchNo, mergeCodPayableDtoList);
			AccessHeader header = buildHeader(batchNo);

			ESBJMSAccessor.asynReqeust(header, request);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new CommonException("构造发送对象和表头异常：" + e.getMessage(), e);
		}

	}

	/**
	 * 
	 * 构建Request对象
	 * 
	 * @author 046644-foss-zengbinwen
	 * @throws CommonException 
	 * @date 2012-11-19 下午3:40:27
	 */
	private SysRefundbillRequest buildRequest(String batchNo, List<CODMergeDto> mergeCodPayableDtoList) throws CommonException {

		SysRefundbillRequest request = new SysRefundbillRequest();

		// 设置需要传送的代收货款数据
		List<RefundBillInfo> billList = request.getBankInfo();
		BigDecimal totalAmount = BigDecimal.ZERO;
		int count = 0;
		if (CollectionUtils.isNotEmpty(mergeCodPayableDtoList)) {
			RefundBillInfo bill = null;
			
			for (CODMergeDto o : mergeCodPayableDtoList) {
				
				bill = new RefundBillInfo();

				try {
					bill.setRefundSort(getRefundType(o.getCodType()));
				} catch (Exception e) {
					throw new CommonException("代收货款类型异常：" + e.getMessage(), e);
				}
				
				// 查询部门信息
				OrgAdministrativeInfoEntity orgEntity = null;
				try {
					orgEntity = orgAdministrativeInfoService
							.queryOrgAdministrativeInfoByCodeClean(o.getPayableOrgCode());
				} catch (Exception e) {
					throw new CommonException("调用综合接口异常：精确查询 通过 CODE通过组织编码在缓存中查找组织："+e.getMessage(), e);
				}
				
				if(orgEntity == null){
					throw new CommonException("调用综合接口异常："+o.getPayableOrgCode()+"部门编码对应标杆编码为空");
				}
				
				// 公司编码
				bill.setCompanyNo(o.getPayableComCode());
				// 始发部门、运单号、收款人、应退金额、账号
				bill.setDeptFrom(orgEntity.getUnifiedCode());
				bill.setWaybillNum(o.getWaybillNo());
				bill.setPayee(o.getPayeeName());
				bill.setAmount(o.getCodAmount());//应退金额--应付单
				bill.setAccount(o.getAccountNo());

				// 银行编码、省份编码、城市编码、支行编码、手机号码
				bill.setOpenBankNum(o.getBankHqCode());
				bill.setCusBankProvinceNum(o.getProvinceCode());
				bill.setCusBankCityNum(o.getCityCode());
				bill.setAccountNum(o.getBankBranchCode());
				bill.setCellphone(o.getPayeePhone());

				// 派送出库时间、对公对私标志
				bill.setDeliverTime(o.getSignDate());
				bill.setPublicPrivateFlag(getPublicPrivateFlag(o.getPublicPrivateFlag()));
				
				bill.setHbnumber(o.getMergeCode()); // 合并编号
				billList.add(bill);

				// 统计明细的的总金额，总条数
				// 如果运单号不为空，则是明细
				if(StringUtils.isNotBlank(o.getWaybillNo())){
					totalAmount = totalAmount.add(o.getCodAmount());
					count ++;	
				}
				
			}
		}

		// 代收货款信息、批次号、导出时间、代收货款类型、总金额、条数
		request.setBatchNum(batchNo);
		request.setExportTime(new Date());
		request.setAmount(totalAmount);
		request.setCount(count);
		return request;
	}

	/**
	 * 
	 * 返回对公对私标志
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-19 下午6:40:03
	 */
	private int getPublicPrivateFlag(String publicPrivateFlag) {
		if (SettlementDictionaryConstants.COD__PUBLIC_PRIVATE_FLAG__COMPANY
				.equals(publicPrivateFlag)) {
			return 0;
		} else {
			return 1;
		}
	}

	/**
	 * 
	 * 返回代收货款类型
	 * 
	 * @author 046644-foss-zengbinwen
	 * @throws ESBBusinessException 
	 * @date 2012-11-19 下午6:40:25
	 */
	private int getRefundType(String codType) throws ESBBusinessException {

		int refundType = 0;

		// 即日退
		if (SettlementDictionaryConstants.COD__COD_TYPE__RETURN_1_DAY
				.equals(codType)) {
			refundType = 1;
		}

		// 三日退
		else if (SettlementDictionaryConstants.COD__COD_TYPE__RETURN_3_DAY
				.equals(codType)) {
			refundType = 2;
		}

		// 审核退
		else if (SettlementDictionaryConstants.COD__COD_TYPE__RETURN_APPROVE
				.equals(codType)) {
			refundType = 3;
		}

		else {
			throw new ESBBusinessException("未知类型的代收货款类型");
		}

		return refundType;
	}

	/**
	 * 
	 * 返回ESB请求头消息
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-19 下午6:41:17
	 */
	private AccessHeader buildHeader(String batchNo) {

		AccessHeader header = new AccessHeader();
		header.setBusinessId(batchNo);
		header.setEsbServiceCode(SettlementESBDictionaryConstants.ESB_FOSS2ESB_BANK_PAY_REFUND);
		header.setBusinessDesc1(SettlementESBDictionaryConstants.ESB_FOSS2ESB_BANK_PAY_REFUND_DESC);
		header.setVersion(SettlementESBDictionaryConstants.ESB_HEADER__VERSION);

		return header;

	}

	/**
	 * @SET
	 * @param orgAdministrativeInfoService
	 */
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		/*
		 *@set
		 *@this.orgAdministrativeInfoService = orgAdministrativeInfoService
		 */
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	/**
	 * @GET
	 * @return orgAdministrativeInfoService
	 */
	public IOrgAdministrativeInfoService getOrgAdministrativeInfoService() {
		/*
		 *@get
		 *@ return orgAdministrativeInfoService
		 */
		return orgAdministrativeInfoService;
	}
	
	
}
