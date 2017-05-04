/**
 * Copyright 2016 STL TEAM
 */
/*******************************************************************************
 * Copyright 2016 STL TEAM
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
 * PROJECT NAME	: stl-consumer-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/settlement/consumer/server/service/impl/BillReceivablePtpService.java
 * 
 * FILE NAME        	: BillReceivablePtpService.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2016  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.consumer.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.ar.bamp.common.util.DateUtil;
import com.deppon.esb.inteface.domain.receivable.BillRecevivableChangedRequest;
import com.deppon.esb.inteface.domain.receivable.FeeDetails;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressPartSalesDeptService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressPartSalesDeptResultDto;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.sign.api.server.service.IWaybillSignResultService;
import com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillSignResultEntity;
import com.deppon.foss.module.settlement.common.api.server.service.IBillReceivableDService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillReceivableService;
import com.deppon.foss.module.settlement.common.api.server.service.ISettlementCommonService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementNoRuleEnum;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableDEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillReceivableConditionDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;
import com.deppon.foss.module.settlement.consumer.api.server.service.IBillReceivablePtpChangeService;
import com.deppon.foss.module.settlement.consumer.api.server.service.IBillReceivablePtpService;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.NewReceivablePtpEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.BillNewReceivablePtpDto;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 对接合伙人应收单service接口实现
 * @author foss-Jiang Xun
 * @date 2016-01-07 上午11:27:13
 */
public class BillReceivablePtpChangeService implements IBillReceivablePtpChangeService {
	
	private static final Logger LOGGER = LogManager
			.getLogger(BillReceivablePtpChangeService.class);

	/**
	 * 结算通用服务service
	 */
	private ISettlementCommonService settlementCommonService;
	/**
	 * 生成应付单service
	 */
	private IBillReceivablePtpService billReceivablePtpService;
	/**
	 * 应收单通用服务service
	 */
	private IBillReceivableService billReceivableService;
	/**
	 * 应收单明细Service
	 */
	private IBillReceivableDService billReceivableDService;
	/**
	 * 组织架构Service
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	/**
	 * 快递代理点部映射
	 */
	private IExpressPartSalesDeptService expressPartSalesDeptService;
	/**
	 * 运单签收结果信息
	 */
	private IWaybillSignResultService waybillSignResultService;
	/**
	 * 运单管理service
	 */
	//private IWaybillManagerService waybillManagerService;

	/**
	 * 根据运单号和应收类型红冲原合伙人应收单
	 * @author 黄乐为
	 * @date 2016-1-14 下午8:27:47
	 */
	@Override
	@Transactional
	public void writeBackBillReceivable(BillNewReceivablePtpDto dto){
		String waybillNo = dto.getNewReceivablePtpEntityList().get(0).getBillReceivableEntity().getWaybillNo();
		String billType = dto.getNewReceivablePtpEntityList().get(0).getBillReceivableEntity().getBillType();
		CurrentInfo currentInfo = getPtpCurrentInfo(dto.getNewReceivablePtpEntityList().get(0).getBillReceivableEntity());
		// 查询合伙人应收单
		BillReceivableConditionDto billReceivableConditionDto = new BillReceivableConditionDto(waybillNo);
		//应收单类型
		billReceivableConditionDto.setBillTypes(new String[]{billType});
		//查有效的应收单
		BillReceivableEntity receivableEntity = billReceivableService.queryReceivableByWaybillNoAndBillType(billReceivableConditionDto);
		if (receivableEntity != null) {
			// 1、先作废原有的有效应收单；2、生成一条红单；
			// 调用合伙人公共红冲接口:作废原应收单生成红单
			billReceivableService.writeBackPartnerBillReceivable(receivableEntity, currentInfo);
		}
		//如果没有有效的应收单则直接生成新应收单

		//foss内部service处理,生成新的应收单
		addReceivableBillController(dto);

	}

	/**
	 * 封装参数转成foss内部DTO
	 * @author foss-hemingyu
	 * @date 2016-01-14 下午8:16:00
	 */
	public BillNewReceivablePtpDto buildDto(BillRecevivableChangedRequest request) throws SettlementException {
		//应收单实体
		BillReceivableEntity entity = new BillReceivableEntity();
		List<NewReceivablePtpEntity> list = new ArrayList<NewReceivablePtpEntity>();
		//合伙人应收单生成Dto
		BillNewReceivablePtpDto dto = new BillNewReceivablePtpDto();
		NewReceivablePtpEntity newReceivablePtpEntity = new NewReceivablePtpEntity();
		//应收单明细实体
		BillReceivableDEntity dEntity;
		//应收单明细列表
		List<BillReceivableDEntity> recvDList = null;

		//日志记录接口参数信息
		LOGGER.info("\n运单号是:"+request.getWaybillNo()+"\n来源单据类型是："+request.getSourceBillType()+""+"\n来源单据号是："+request.getSourceBillNo()+""
				+"\n应收部门编码是："+request.getReceivableOrgCode()+""+"\n收入部门编码是："+request.getGeneratingOrgCode()+""
				+"\n催款部门编码是："+request.getDunningOrgCode()+""+"\n出发部门编码是："+request.getOrigOrgCode()+""
				+"\n到达部门编码是："+request.getDestOrgCode()+""+"\n业务日期是："+request.getBusinessDate()+""
				+"\n付款方式是："+request.getPaymentType()+""+"\n制单人编码是："+request.getCreateUserCode()+""
				+"\n制单人部门编码是："+request.getCreateOrgCode()+""+"\n扣款状态是："+request.getWithholdStatus()+""
				+"\n订单总金额是："+request.getAmount()+"\n运输性质是："+request.getProductCode()
				+"\n付款方式是："+request.getPaymentType()+"\n单据子类型是："+request.getBillType());
		BillRecevivableChangedRequest receivableBill = request;
		//验证参数
		validateParams(receivableBill);
		//应收单号
		String receivableNo = "";
		//ptp费用明细
		List<FeeDetails> feeDetails =receivableBill.getFeeDetails();

		//应收单子类型
		String billType = receivableBill.getBillType();
		if(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__ORIGINAL_RECEIVABLE.equals(billType)){//始发提成应收
			receivableNo = settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.YS82);
		}else if(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__DELIVERY_FEE_RECEIVABLE.equals(billType)){//委派费应收
			receivableNo = settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.YS83);
		}else if(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__FREIGHT_COLLECT_RECEIVABLE.equals(billType)){//合伙人到付
			receivableNo = settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.YS84);
		}else if(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE.equals(billType)){//合伙人代收货款应收(即普通代收货款应收)
			receivableNo = settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.YS85);
		}else if(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__PENALTY.equals(billType)){//合伙人罚款应收 于2016-02-19 10:40:42 foss-hemingyu添加
			receivableNo = settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.YS86);
		}else if(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__TRAIN_FEE.equals(billType)){//合伙人培训会务应收 于2016-02-19 10:40:42 foss-hemingyu添加
			receivableNo = settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.YS87);
		}else if(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER_ERROR_RECEIVABLE.equals(billType)){//合伙人差错应收 于2016-02-19 10:40:42 foss-hemingyu添加
			receivableNo = settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.YS88);
		}else if(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE.equals(billType)){//普通到付应收
        	//验证费用
			validateDR(receivableBill);
            receivableNo = settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.YS2);
        }else{
			throw new SettlementException("应收单据子类型不正确，无此类型定义");
		}

		LOGGER.info("\n应收单号是："+receivableNo);

		//明细费用之和
		BigDecimal sum = BigDecimal.ZERO;
		if(CollectionUtils.isNotEmpty(feeDetails)){
			recvDList = new ArrayList<BillReceivableDEntity>();
			for(int k=0;k<feeDetails.size();k++){
				dEntity = new BillReceivableDEntity();
				dEntity.setActive(FossConstants.YES);//有效状态
				dEntity.setAmount(feeDetails.get(k).getAmount()==null?BigDecimal.ZERO:feeDetails.get(k).getAmount());//明细费用
				dEntity.setCreateTime(new Date());//创建日期
				dEntity.setCreateUserCode(receivableBill.getCreateUserCode());//创建人编码
				dEntity.setCreateUserName(receivableBill.getCreateUserName());//创建人
				dEntity.setReceivableType(feeDetails.get(k).getReceivableType());//费用类型
				dEntity.setSourceBillNo(receivableBill.getSourceBillNo());//来源单据号
				dEntity.setReceivableNo(receivableNo);//应收单号

				sum = sum.add(dEntity.getAmount());
				recvDList.add(dEntity);
			}
			//判断应收单金额和明细金额是否相等
			if(sum.compareTo(receivableBill.getAmount()) != 0){
				throw new SettlementException("费用明细总和不等于总金额");
			}
			//判断应收单金额和为0，不生成此项应收单
			if(sum.compareTo(BigDecimal.ZERO)==0){
				return null;
			}
			LOGGER.info("\n费用明细数量是："+feeDetails.size());
		}

		//应收单
		entity.setReceivableNo(receivableNo);//应收单号
		entity.setWaybillNo(receivableBill.getWaybillNo());//运单号
		entity.setWaybillId(receivableBill.getWaybillId());//运单id
		entity.setBillType(receivableBill.getBillType());//应收单据子类型
		entity.setSourceBillType(receivableBill.getSourceBillType());//来源单据类型
		entity.setSourceBillNo(receivableBill.getSourceBillNo());//来源单据号
		entity.setCreateType(SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__AUTO);//生成方式
		entity.setReceivableOrgCode(receivableBill.getReceivableOrgCode());//应收部门编码
		entity.setGeneratingOrgCode(receivableBill.getGeneratingOrgCode());//收入部门编码
		entity.setDunningOrgCode(receivableBill.getDunningOrgCode());//催款部门编码
		entity.setOrigOrgCode(receivableBill.getOrigOrgCode());//出发部门编码
		entity.setDestOrgCode(receivableBill.getDestOrgCode());//到达部门编码
		entity.setCustomerCode(receivableBill.getCustomerCode());//客户编码/应收代理编码
		entity.setCustomerName(receivableBill.getCustomerName());//客户名称/应收代理名称
		entity.setDeliveryCustomerCode(receivableBill.getDeliveryCustomerCode());//发货客户编码
		entity.setDeliveryCustomerName(receivableBill.getDeliveryCustomerName());//发货客户名称
		entity.setReceiveCustomerCode(receivableBill.getReceiveCustomerCode());//收货客户编码
		entity.setReceiveCustomerName(receivableBill.getReceiveCustomerName());//收货客户名称
		entity.setCurrencyCode("RMB");//币种
		entity.setBusinessDate(receivableBill.getBusinessDate());//业务日期
		entity.setAccountDate(new Date());//记账日期
		LOGGER.info("...........记账日期是："+entity.getAccountDate());
		//合伙人到直营默认到付
		if(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__FREIGHT_COLLECT_RECEIVABLE.equals(receivableBill.getBillType())
				|| SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE.equals(receivableBill.getBillType())){
			entity.setPaymentType(SettlementDictionaryConstants.BILL_REPAYMENT__SOURCE_BILL_TYPE__FREIGHT_COLLECT);//付款方式
		}else{
			entity.setPaymentType(receivableBill.getPaymentType());//付款方式
		}
		entity.setProductCode(receivableBill.getProductCode());//运输性质
		entity.setProductId(receivableBill.getProductId());//产品ID
		entity.setTransportFee(receivableBill.getTransportFee());//公布价运费
		entity.setPickupFee(receivableBill.getPickupFee());//接货费
		entity.setDeliveryGoodsFee(receivableBill.getDeliveryGoodsFee());//送货费
		entity.setPackagingFee(receivableBill.getPackagingFee());//包装费
		entity.setCodFee(receivableBill.getCodFee());//代收货款手续费
		entity.setInsuranceFee(receivableBill.getInsuranceFee());//保价费
		entity.setOtherFee(receivableBill.getOtherFee());//其他费用
		entity.setValueAddFee(receivableBill.getValueAddFee());//增值费用
		entity.setPromotionsFee(receivableBill.getPromotionsFee());//优惠费用
		entity.setGoodsName(receivableBill.getGoodsName());//货物名称
		entity.setGoodsVolumeTotal(receivableBill.getGoodsVolumeTotal());//货物总体积
		entity.setBillWeight(receivableBill.getBillWeight());//计费重量
		entity.setReceiveMethod(receivableBill.getReceiveMethod());//提货方式
		entity.setCustomerPickupOrgCode(receivableBill.getCustomerPickupOrgCode());//提货网点
		entity.setGoodsQtyTotal(receivableBill.getGoodsQtyTotal());//货物总件数
		entity.setTargetOrgCode(receivableBill.getTargetOrgCode());//目的站
		entity.setVersionNo(FossConstants.INIT_VERSION_NO);//版本号
		entity.setActive(FossConstants.ACTIVE);//是否有效
		entity.setIsRedBack(FossConstants.NO);//是否红单
		entity.setIsInit(FossConstants.NO);//是否初始化
		entity.setApproveStatus(SettlementDictionaryConstants.BILL_PAYABLE__APPROVE_STATUS__AUDIT_AGREE);//审核状态
		entity.setCreateUserCode(receivableBill.getCreateUserCode());//制单人编码
		entity.setCreateUserName(receivableBill.getCreateUserName());//制单人名称
		entity.setCollectionType(receivableBill.getCollectionType());//收款类别
		entity.setCollectionName(receivableBill.getCollectionName());//收款名称
		entity.setUnitPrice(receivableBill.getUnitPrice());//运费计费费率
		entity.setInsuranceAmount(receivableBill.getInsuranceAmount());//保险声明价值
		entity.setDeliveryCustomerContact(receivableBill.getDeliveryCustomerContact());//发货联系人
		entity.setInvoiceMark(receivableBill.getInvoiceMark());//发票标记
		entity.setUnifiedSettlement(receivableBill.getUnifiedSettlement());//是否统一结算
		entity.setContractOrgCode(receivableBill.getContractOrgCode());//合同部门编码
		entity.setContractOrgName(receivableBill.getContractOrgName());//合同部门名称
		entity.setIsDiscount(receivableBill.getIsDiscount());//是否折扣
		entity.setWithholdStatus(receivableBill.getWithholdStatus());//扣款状态
		entity.setAmount(receivableBill.getAmount());//订单总金额
		entity.setVerifyAmount(BigDecimal.ZERO);//已核销金额
		entity.setUnverifyAmount(receivableBill.getAmount());//未核销金额
		entity.setNotes(new Date()+"更改合伙人应收  ");//备注
		//制单部门信息
		OrgAdministrativeInfoEntity createOrgEntity = orgAdministrativeInfoService
				.queryOrgAdministrativeInfoByUnifiedCode(receivableBill.getCreateOrgCode());
		if(null == createOrgEntity){
			LOGGER.error("生成应收单失败，制单部门信息为空！");
			throw new SettlementException("生成应收单失败，制单部门信息为空！");
		}
		entity.setCreateOrgCode(createOrgEntity.getCode());
		entity.setCreateOrgName(createOrgEntity.getName());
		
		//备注
		entity.setNotes("[" + receivableBill.getBillType() + "-"
				+ receivableBill.getOperateType() + "-"
				+ DateUtil.getCurrentDate() + "]");

		newReceivablePtpEntity.setBillReceivableEntity(entity);
		newReceivablePtpEntity.setBillReceivableDList(recvDList);
		list.add(newReceivablePtpEntity);
		//将应收单信息保存到Dto
		dto.setNewReceivablePtpEntityList(list);
		return dto;
	}

	/**
	 * 验证接口参数
	 * 非空验证:
	 * 运单号、来源单据号、来源单据类型、单据子类型、应收部门编码、收入部门编码、出发部门编码、到达部门编码、客户编码、总金额、业务日期、
	 * 制单部门编码
	 * @author foss-Jiang Xun
	 * @date 2016-01-20 下午3:16:00
	 */
	public void validateParams(BillRecevivableChangedRequest receivableBill) throws SettlementException {
		//合伙人罚款应收、合伙人培训会务应收、合伙人差错应收、合伙人其他应收。不验证运单号，来源单据类型
		if(!(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__PENALTY.equals(receivableBill.getBillType())
				||SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__TRAIN_FEE.equals(receivableBill.getBillType())
				||SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER_ERROR_RECEIVABLE.equals(receivableBill.getBillType())
				|| SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER_OTHER_FEE_RECEIVABLE.equals(receivableBill.getBillType()))){
			//运单号
			if(receivableBill.getWaybillNo()==null){
				LOGGER.error("运单号为空...");
				throw new SettlementException("运单号为空...");
			}
			//来源单据类型
			if(receivableBill.getSourceBillType()==null){
				LOGGER.error("来源单据类型为空...");
				throw new SettlementException("来源单据类型为空...");
			}
		}
		//来源单据号
		if(receivableBill.getSourceBillNo()==null){
			LOGGER.error("来源单据号为空...");
			throw new SettlementException("来源单据号为空...");
		}
		//单据子类型
		if(receivableBill.getBillType()==null){
			LOGGER.error("单据子类型为空...");
			throw new SettlementException("单据子类型为空...");
		}
		//应收部门编码
		if(receivableBill.getReceivableOrgCode()==null){
			LOGGER.error("应收部门编码为空...");
			throw new SettlementException("应收部门编码为空...");
		}
		//代收货款、到付运费应收，不验证收入部门
		if(!(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__FREIGHT_COLLECT_RECEIVABLE.equals(receivableBill.getBillType())||
				SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE.equals(receivableBill.getBillType()))){
			//收入部门编码
			if(receivableBill.getGeneratingOrgCode()==null){
				LOGGER.error("收入部门编码为空...");
				throw new SettlementException("收入部门编码为空...");
			}
		}
		//催款部门编码
		if(receivableBill.getDunningOrgCode()==null){
			LOGGER.error("催款部门编码为空...");
			throw new SettlementException("催款部门编码为空...");
		}
		//奖罚、培训、差错和其他应收不验证出发和到达部门
		if(!(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__PENALTY.equals(receivableBill.getBillType())||
				SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__TRAIN_FEE.equals(receivableBill.getBillType())||
				SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER_ERROR_RECEIVABLE.equals(receivableBill.getBillType())||
				SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER_OTHER_FEE_RECEIVABLE.equals(receivableBill.getBillType()))){
			//出发部门编码
			if(receivableBill.getOrigOrgCode()==null){
				LOGGER.error("出发部门编码为空...");
				throw new SettlementException("出发部门编码为空...");
			}
			//到达部门编码
			if(receivableBill.getDestOrgCode()==null){
				LOGGER.error("到达部门编码为空...");
				throw new SettlementException("到达部门编码为空...");
			}
		}
		//客户编码
		if(receivableBill.getCustomerCode()==null){
			LOGGER.error("客户编码为空...");
			throw new SettlementException("客户编码为空...");
		}
		//总金额
		if(receivableBill.getAmount()==null){
			LOGGER.error("应收单总金额为空，不生成应收单...");
			throw new SettlementException("应收单总金额为空，不生成应收单...");
		}
		//业务日期
		if(receivableBill.getBusinessDate()==null){
			LOGGER.error("业务日期为空...");
			throw new SettlementException("业务日期为空...");
		}
//		//付款方式
//		if(receivableBill.getPaymentType()==null){
//			LOGGER.error("付款方式为空...");
//			throw new SettlementException("付款方式为空...");
//		}
//		//运输性质
//		if(receivableBill.getProductCode()==null){
//			LOGGER.error("运输性质为空...");
//			throw new SettlementException("运输性质为空...");
//		}
//		//制单人编码
//		if(receivableBill.getCreateUserCode()==null){
//			LOGGER.error("制单人编码为空...");
//			throw new SettlementException("制单人编码为空...");
//		}
		//制单部门编码
		if(receivableBill.getCreateOrgCode()==null){
			LOGGER.error("制单部门编码为空...");
			throw new SettlementException("制单部门编码为空...");
		}
	}
	
	/**
	 * 验证到付运费应收单的总金额和其他费用(公布价运费 接货费 送货费 包装手续费 代收货款手续费 保价费 其他费用)之和
	 * @author foss-Jiang Xun
	 * @date 2016-04-05 上午10:23:10
	 */
	public void validateDR(BillRecevivableChangedRequest receivableBill) {
		//金额不为0（不是红冲）的，验证。
		if(receivableBill.getAmount()!=null&&BigDecimal.ZERO.compareTo(receivableBill.getAmount())==-1){
			BigDecimal sum = BigDecimal.ZERO;
			sum = BigDecimal.ZERO.add(receivableBill.getTransportFee()==null?BigDecimal.ZERO:receivableBill.getTransportFee())
			.add(receivableBill.getPickupFee()==null?BigDecimal.ZERO:receivableBill.getPickupFee())
			.add(receivableBill.getDeliveryGoodsFee()==null?BigDecimal.ZERO:receivableBill.getDeliveryGoodsFee())
			.add(receivableBill.getPackagingFee()==null?BigDecimal.ZERO:receivableBill.getPackagingFee())
			.add(receivableBill.getCodFee()==null?BigDecimal.ZERO:receivableBill.getCodFee())
			.add(receivableBill.getInsuranceFee()==null?BigDecimal.ZERO:receivableBill.getInsuranceFee())
			.add(receivableBill.getOtherFee()==null?BigDecimal.ZERO:receivableBill.getOtherFee());
			if(receivableBill.getAmount().compareTo(sum)!=0){
				LOGGER.error("到付运费应收总金额与费用项之和不相等...");
				throw new SettlementException("到付运费应收总金额与费用项之和不相等...");
			}
		}
	}

	/**
	 * 根据运单号和应收类型红冲原合伙人应收单
	 * @author 黄乐为
	 * @date 2016-1-14 下午8:27:47
	 */
	@Override
	public void addReceivableBillController(BillNewReceivablePtpDto dto) {
		LOGGER.info("变更应收单生成合伙人应收单开始....");
		//应收单信息列表
		List<NewReceivablePtpEntity> recvList = dto.getNewReceivablePtpEntityList();
		//应收单实体
		BillReceivableEntity entity;
		//应收单明细列表
		List<BillReceivableDEntity> dList;
		for(int i=0;i<recvList.size();i++){
			entity = recvList.get(i).getBillReceivableEntity();
			dList = recvList.get(i).getBillReceivableDList();
			//生成应收单
			if(entity.getAmount().compareTo(BigDecimal.valueOf(0)) > 0){
				//奖罚、培训、差错和其他应收，不查询出发和到达部门
				if(!(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__PENALTY.equals(entity.getBillType())||
						SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__TRAIN_FEE.equals(entity.getBillType())||
						SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER_ERROR_RECEIVABLE.equals(entity.getBillType())||
						SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER_OTHER_FEE_RECEIVABLE.equals(entity.getBillType()))){
					//根据出发部门标杆编码 查出发部门编码、出发部门名称
					OrgAdministrativeInfoEntity origEntity = orgAdministrativeInfoService
							.queryOrgAdministrativeInfoByUnifiedCode(entity.getOrigOrgCode());//出发部门信息
					if(null == origEntity){
						LOGGER.error("生成应收单失败，出发部门信息为空！");
						throw new SettlementException("生成应收单失败，出发部门信息为空！");
					}
					LOGGER.info(".......出发部门编码是："+origEntity.getCode());
					
					//出发部门编码
					entity.setOrigOrgCode(origEntity.getCode());
					//出发部门名称
					entity.setOrigOrgName(origEntity.getName());
					
					//到达部门信息
					OrgAdministrativeInfoEntity destEntity = orgAdministrativeInfoService
							.queryOrgAdministrativeInfoByUnifiedCode(entity.getDestOrgCode());
					if(null == destEntity){
						LOGGER.error("生成应收单失败，到达部门信息为空！");
						throw new SettlementException("生成应收单失败，到达部门信息为空！");
					}
					//到达部门编码
					entity.setDestOrgCode(destEntity.getCode());
					//到达部门名称
					entity.setDestOrgName(destEntity.getName());
					
					//运单为快递时，查询快递代理点部
					String prodectCode = entity.getProductCode();
					if(SettlementUtil.isPackageProductCode(prodectCode)){
						LOGGER.info("更改生成应收单，是快递！");
						//调用综合接口去查询出发部门快递代理点部
						ExpressPartSalesDeptResultDto expressOrigOrg = expressPartSalesDeptService.
								queryExpressPartSalesDeptBySalesCodeAndTime(entity.getOrigOrgCode(),null);
						//判断是否为空
						if(expressOrigOrg==null){
							throw new SettlementException("生成应收单失败，出发部门对应的快递代理点部为空！");
						}else if(StringUtils.isNotBlank(expressOrigOrg.getUnifiedCode())){
							//设置快递代理点部
							entity.setExpressOrigOrgCode(expressOrigOrg.getPartCode());
							entity.setExpressOrigOrgName(expressOrigOrg.getPartName());
						}else{
							
						}
						//虚拟网点，直接存快递点部(不查快递点部)
						OrgAdministrativeInfoEntity org = orgAdministrativeInfoService.
								queryOrgAdministrativeInfoByCode(entity.getDestOrgCode());
						if(org==null){//无部门信息
							entity.setExpressDestOrgCode(entity.getDestOrgCode());
							entity.setExpressDestOrgName(entity.getDestOrgName());
						}else if(org.checkExpressSalesDepartment()||org.checkTransferCenter()){//到达部门是虚拟营业部或外场，不校验快递点部
							entity.setExpressDestOrgCode(entity.getDestOrgCode());
							entity.setExpressDestOrgName(entity.getDestOrgName());
						}else{//有部门信息，且不是虚拟营业部
							//调用综合接口去查询到达部门快递代理点部
							ExpressPartSalesDeptResultDto expressDestOrg = expressPartSalesDeptService.
									queryExpressPartSalesDeptBySalesCodeAndTime(entity.getDestOrgCode(),null);
							if(expressDestOrg==null){
								throw new SettlementException("生成应收单失败，到达部门对应的快递代理点部为空！");
							}else if(StringUtils.isNotBlank(expressDestOrg.getUnifiedCode())){
								//设置快递代理点部
								entity.setExpressDestOrgCode(expressDestOrg.getPartCode());
								entity.setExpressDestOrgName(expressDestOrg.getPartName());
							}else{
								
							}
						}
					}else{
						LOGGER.info("更改生成应收单，不是快递！");
					}
				}
				
				
				//应收部门
				OrgAdministrativeInfoEntity receivableOrgEntity = orgAdministrativeInfoService
						.queryOrgAdministrativeInfoByUnifiedCode(entity.getReceivableOrgCode());
				if(null == receivableOrgEntity){
					LOGGER.error("生成应收单失败，应收部门信息为空！");
					throw new SettlementException("生成应收单失败，应收部门信息为空！");
				}
				//应收部门
				entity.setReceivableOrgCode(receivableOrgEntity.getCode());
				entity.setReceivableOrgName(receivableOrgEntity.getName());
				
				//收入部门信息
				//代收货款、到付运费应收，收入部门为空
				if(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__FREIGHT_COLLECT_RECEIVABLE.equals(entity.getBillType())||
						SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE.equals(entity.getBillType())){
					entity.setGeneratingOrgCode(null);
					entity.setGeneratingOrgName(null);	
				}else{
					OrgAdministrativeInfoEntity generatingOrgEntity = orgAdministrativeInfoService
							.queryOrgAdministrativeInfoByUnifiedCode(entity.getGeneratingOrgCode());
					if(null == generatingOrgEntity){
						LOGGER.error("生成应收单失败，收入部门信息为空！");
						throw new SettlementException("生成应收单失败，收入部门信息为空！");
					}
					entity.setGeneratingOrgCode(generatingOrgEntity.getCode());
					entity.setGeneratingOrgName(generatingOrgEntity.getName());	
					//收入子部门编码
					entity.setGeneratingComCode(generatingOrgEntity.getSubsidiaryCode());
					//收入子部门名称
					entity.setGeneratingComName(generatingOrgEntity.getSubsidiaryName());
				}
				
				//催款部门信息
				OrgAdministrativeInfoEntity dunningOrgEntity = orgAdministrativeInfoService
						.queryOrgAdministrativeInfoByUnifiedCode(entity.getDunningOrgCode());
				if(null == dunningOrgEntity){
					LOGGER.error("生成应收单失败，催款部门信息为空！");
					throw new SettlementException("生成应收单失败，催款部门信息为空！");
				}		
				entity.setDunningOrgCode(dunningOrgEntity.getCode());
				entity.setDunningOrgName(dunningOrgEntity.getName());
				
				//DN201609200015 若运单已签收，则将应收单的确认收入日期置为签收日期 by 243921
				WaybillSignResultEntity sign = new WaybillSignResultEntity();
				sign.setActive(FossConstants.YES);
				sign.setWaybillNo(entity.getWaybillNo());
				WaybillSignResultEntity result = waybillSignResultService.queryWaybillSignResultByWaybillNo(sign);
				if(null != result){
					//确认收入时间
					entity.setConrevenDate(result.getSignTime());
				}
				
				//设置ID
				entity.setId(UUIDUtils.getUUID());
				billReceivableService.addBillReceivable(entity, dto.getCurrentInfo());
				//生成应收单明细
				if(CollectionUtils.isNotEmpty(dList)){
					billReceivableDService.addList(dList);
				}
			}
		}
		LOGGER.info("变更应收单生成合伙人应收单结束....");
	}

	public CurrentInfo getPtpCurrentInfo(BillReceivableEntity entity) {
		UserEntity user = new UserEntity();
		EmployeeEntity employee = new EmployeeEntity();
		employee.setEmpCode(entity.getCreateUserCode());
		employee.setEmpName(entity.getCreateUserName());

		user.setEmployee(employee);
		user.setUserName(entity.getCreateUserName());
		OrgAdministrativeInfoEntity dept = new OrgAdministrativeInfoEntity();
		dept.setCode(entity.getCreateOrgCode());
		dept.setName(entity.getCreateOrgName());
		CurrentInfo currentInfo = new CurrentInfo(user, dept);
		return currentInfo;
	}

	/**
	 * 结算通用服务service set注入
	 * @param settlementCommonService
	 */
	public void setSettlementCommonService(
			ISettlementCommonService settlementCommonService) {
		this.settlementCommonService = settlementCommonService;
	}

	public ISettlementCommonService getSettlementCommonService() {
		return settlementCommonService;
	}

	public IBillReceivablePtpService getbillReceivablePtpService() {
		return billReceivablePtpService;
	}

	public void setbillReceivablePtpService(
			IBillReceivablePtpService billReceivablePtpService) {
		this.billReceivablePtpService = billReceivablePtpService;
	}
	public IBillReceivableService getBillReceivableService() {
		return billReceivableService;
	}

	public void setBillReceivableService(IBillReceivableService billReceivableService) {
		this.billReceivableService = billReceivableService;
	}

	public IBillReceivableDService getBillReceivableDService() {
		return billReceivableDService;
	}

	public void setBillReceivableDService(IBillReceivableDService billReceivableDService) {
		this.billReceivableDService = billReceivableDService;
	}

	public void setOrgAdministrativeInfoService(IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	public void setBillReceivablePtpService(IBillReceivablePtpService billReceivablePtpService) {
		this.billReceivablePtpService = billReceivablePtpService;
	}

	public void setExpressPartSalesDeptService(IExpressPartSalesDeptService expressPartSalesDeptService) {
		this.expressPartSalesDeptService = expressPartSalesDeptService;
	}

	public void setWaybillSignResultService(
			IWaybillSignResultService waybillSignResultService) {
		this.waybillSignResultService = waybillSignResultService;
	}

	/*public void setWaybillManagerService(IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}*/
	
}
